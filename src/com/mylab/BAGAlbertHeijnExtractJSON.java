package com.mylab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BAGAlbertHeijnExtractJSON {

	public void process_pands(String formatted_kml) {

		final List<Placemark> placemarks = new ArrayList<Placemark>();

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					BAGHttpURLConnection.json_albertheijn_file, true)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				// As we read any XML element we will push that in this stack
				private Stack<String> elementStack = new Stack<String>();
				private Stack<Placemark> objectStack = new Stack<Placemark>();
				private String current_SimpleData;

				boolean bSimpleData = false;
				boolean bcoordinates = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					this.elementStack.push(qName);
					if ("Placemark".equals(qName)) {
						Placemark placeMark = new Placemark();
						this.objectStack.push(placeMark);
					} else if ("SimpleData".equals(qName)) {
						setCurrent_SimpleData(attributes.getValue(0));
						bSimpleData = true;
					} else if ("coordinates".equals(qName)) {
						bcoordinates = true;
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					this.elementStack.pop();
					if ("Placemark".equals(qName)) {
						Placemark placeMark = (Placemark) this.objectStack
								.pop();
						placemarks.add(placeMark);
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					String value = new String(ch, start, length).trim();
					if (value.length() == 0)
						return;
					if (bSimpleData) {
						Placemark placeMark = (Placemark) this.objectStack
								.peek();
						switch (getCurrent_SimpleData()) {
						case "bouwjaar":
							placeMark.setBouwjaar(value);
							break;
						case "status":
							placeMark.setStatus(value);
							break;
						case "gebruiksdoel":
							placeMark.setGebruiksdoel(value);
							break;
						default:
						}
						bSimpleData = false;
					} else if (bcoordinates) {
						Placemark placeMark = (Placemark) this.objectStack
								.peek();
						placeMark.setCoordinates(value);
						bcoordinates = false;
					}

				}

				public String getCurrent_SimpleData() {
					return current_SimpleData;
				}

				public void setCurrent_SimpleData(String current_SimpleData) {
					this.current_SimpleData = current_SimpleData;
				}

			};

			saxParser.parse(formatted_kml, handler);
			Number numlat = 0, numlng = 0, numlatpr = 0, numlngpr = 0;
			
			System.out.println("Placemarks size: " + placemarks.size());
			String s = null;
			for (int i = 0; i < placemarks.size(); i++) {
				Placemark placeMark = placemarks.get(i);
				String gebruiksdoel = placeMark.getGebruiksdoel();
				if (!gebruiksdoel.equalsIgnoreCase("winkelfunctie")) continue;
				String[] coordinates = placeMark.getCoordinates().split(" ");
				for (int j = 0; j < coordinates.length - 1; j++) {
					String[] latlng = coordinates[j].split(",");
					numlat = NumberFormat.getInstance().parse(latlng[0].substring(0, 6));
					numlng = NumberFormat.getInstance().parse(latlng[1].substring(0, 7));
					if ((numlat.intValue() == numlatpr.intValue()) && ( numlng.intValue() == numlngpr.intValue())) continue;
					numlatpr = numlat;
					numlngpr = numlng;
					s = "[" + coordinates[j] + "],\n";
					out.write(s);
				}
				s = "[" + coordinates[coordinates.length - 1] + "]\n";
				out.write(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (out != null) {
			out.close();
		}
	}

}
