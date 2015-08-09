package com.mylab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BAGVerblijfsobjectExtract {

	public void process_verblijfplaatsen() {

		final List<Placemark> placemarks = new ArrayList<Placemark>();

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					BAGHttpURLConnection.js_verblijfsobject_file, true)));
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
						case "woonplaats":
							placeMark.setWoonplaats(value);
							break;
						case "huisnummer":
							placeMark.setHuisnummer(value);
							break;
						case "huisletter":
							placeMark.setHuisletter(value);
							break;
						case "oppervlakte":
							placeMark.setOppervlakte(value);
							break;
						case "postcode":
							placeMark.setPostcode(value);
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

			saxParser.parse(BAGHttpURLConnection.kml_verblijfsobject_file, handler);

			System.out.println("Placemarks size: " + placemarks.size());
			String s = null;
			for (int i = 0; i < placemarks.size(); i++) {
				Placemark placeMark = placemarks.get(i);
				String woonplaats = placeMark.getWoonplaats(); if (woonplaats == null) woonplaats = "";
				if (woonplaats.contains("'")) {
					woonplaats = woonplaats.replaceAll("'", "&#39;");
				}
				String huisnummer = placeMark.getHuisnummer(); if (huisnummer == null) huisnummer = "";
				String huisletter = placeMark.getHuisletter(); if (huisletter == null) huisletter = "";
				String postcode = placeMark.getPostcode(); if (postcode == null) postcode = "";
				String oppervlakte = placeMark.getOppervlakte(); if (oppervlakte == null) oppervlakte = "";
				String address = huisnummer + huisletter + " " + postcode + " " + woonplaats + " Area: " + oppervlakte;
				out.write("{ 'geometry': { 'type': 'Point', 'coordinates': ");
				s = "[" + placeMark.getCoordinates() + "]";
				out.write(s);
				out.write("}, 'type': 'Feature', 'properties': { 'popupContent': 'This is address ");
				out.write(address);
				out.write("'},'id': " + i);
				if (i < placemarks.size() - 1) {
					s = "},\n";
				}
				else {
					s = "}\n";
				}
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
