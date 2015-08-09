package com.mylab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BAGHttpURLConnection {

	public static final String js_ligplaats_file = "/home/jan/git/BedAndBreakfast/json/BAGLigplaats.js";
	public static final String kml_ligplaats_file = "/home/jan/Downloads/BAGLigplaats/bagexample00000000.kml";
	public static final String js_verblijfsobject_file = "/home/jan/git/BedAndBreakfast/json/BAGVerblijfsplaats.js";
	public static final String kml_verblijfsobject_file = "/home/jan/Downloads/BAGVerblijfsobject/bagexample00000000.kml";
	public static final String js_standplaats_file = "/home/jan/git/BedAndBreakfast/json/BAGStandplaats.js";
	public static final String kml_standplaats_file = "/home/jan/Downloads/BAGStandplaats/bagexample00000000.kml";
	public static final String json_pand_file = "/home/jan/git/BedAndBreakfast/json/family.geo.json";
	public static final String js_pand_file = "/home/jan/git/BedAndBreakfast/json/BAGPand.js";
	public static final String kml_pand_file = "/home/jan/Downloads/BAGPand/bagexample00000000.kml";
	public static final String json_albertheijn_file = "/home/jan/git/BedAndBreakfast/json/albertheijn.geo.json";
	public static final String js_albertheijn_file = "/home/jan/git/BedAndBreakfast/json/BAGAlbertHeijn.js";
	public static final String kml_albertheijn_file = "/home/jan/Downloads/BAGAlbertHeijn/bagexample00000000.kml";

	private final String USER_AGENT = "Mozilla/5.0";
	public static final String kml_file = "/home/jan/Downloads/BAGPand/bagexample";
	static boolean result = true;

	public static void main(String[] args) throws Exception {

		BAGHttpURLConnection http = new BAGHttpURLConnection();
		String formatted_kml;
		String url;
		boolean found = true;
		PrintWriter out = null;
		String[] ahwinkels = new String[4];

		if (args.length > 0) {
			switch (args[0]) {
			case "BAGPand":
				/* 479100000044059 479100000013093 396100000067402 */
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						json_pand_file, false)));
				out.println("{\"type\":\"FeatureCollection\",\"features\":[");
				out.println("{\"type\":\"Feature\",\"id\":\"Family\",\"properties\":{\"name\":\"Family\"},\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":");
				out.println("[[[");
				out.close();
				String url_template = "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=pand&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:identificatie%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml";
				for (int j = 1; j < args.length; j++) {
					formatted_kml = "/home/jan/Downloads/BAGPand/bagexample"
							+ String.format("%08d", j - 1) + ".kml";
					url = "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=pand&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:identificatie%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml";
					url = url_template.replace("0123456789", args[j]);
					System.out.println(url);
					found = http.sendGet(url, formatted_kml);
					if (found) {
						BAGPandExtractJSON pand = new BAGPandExtractJSON();
						pand.process_pands(formatted_kml);
						out = new PrintWriter(new BufferedWriter(
								new FileWriter(json_pand_file, true)));
						if (j < args.length - 1) {
							out.println("]],\n[[");
						} else {
							out.println("]]");
						}
						out.close();
					}
				}
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						json_pand_file, true)));
				out.println("]}}]}");
				out.close();
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_pand_file, false)));
				out.println("var BAG_Pand_Placemarks = ");
				out.println("{\"type\":\"FeatureCollection\",\"features\":[");
				out.close();
				for (int j = 1; j < args.length; j++) {
					formatted_kml = "/home/jan/Downloads/BAGPand/bagexample"
							+ String.format("%08d", j - 1) + ".kml";
					url = "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=pand&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:identificatie%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml";
					url = url_template.replace("0123456789", args[j]);
					System.out.println(url);
					found = http.sendGet(url, formatted_kml);
					if (found) {
						BAGPandExtract pand = new BAGPandExtract();
						pand.process_pands(formatted_kml, j - 1);
						out = new PrintWriter(new BufferedWriter(
								new FileWriter(js_pand_file, true)));
						if (j < args.length - 1) {
							out.println(",");
						} else {
							out.println("");
						}
						out.close();
					}
				}
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_pand_file, true)));
				out.println("]}");
				out.close();
				break;
			case "BAGLigplaats":
				/* Assendelft */
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_ligplaats_file, false)));

				out.print("var BAG_Ligplaats_Placemarks = { 'type': 'FeatureCollection','features': [ ");
				out.close();
				url_template = "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=ligplaats&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:woonplaats%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml";
				formatted_kml = "/home/jan/Downloads/BAGLigplaats/bagexample"
						+ String.format("%08d", 0) + ".kml";
				url = url_template.replace("0123456789", args[1]);
				System.out.println(url);
				found = http.sendGet(url, formatted_kml);
				if (found) {
					BAGLigplaatsExtract ligplaats = new BAGLigplaatsExtract();
					ligplaats.process_ligplaatsen();
				}
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_ligplaats_file, true)));
				out.print("]}");
				out.close();
				break;
			case "BAGStandplaats":
				/* Assendelft */
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_standplaats_file, false)));
				out.print("var BAG_Standplaats_Placemarks = { 'type': 'FeatureCollection','features': [ ");
				out.close();
				url_template = "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=standplaats&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:woonplaats%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml";
				formatted_kml = "/home/jan/Downloads/BAGStandplaats/bagexample"
						+ String.format("%08d", 0) + ".kml";
				url = url_template.replace("0123456789", args[1]);
				System.out.println(url);
				found = http.sendGet(url, formatted_kml);
				if (found) {
					BAGStandplaatsExtract standplaats = new BAGStandplaatsExtract();
					standplaats.process_standplaatsen();
				}
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_standplaats_file, true)));
				out.print("]}");
				out.close();
				break;
			case "BAGVerblijfsobject":
				/* Assendelft 1566JG */
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_verblijfsobject_file, false)));
				out.print("var BAGVerblijfsobject = { 'type': 'FeatureCollection','features': [ ");
				out.close();
				url_template = "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=bag:verblijfsobject&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:woonplaats%3C/PropertyName%3E%3CLiteral%3Eabcdefghij%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:postcode%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml";
				formatted_kml = "/home/jan/Downloads/BAGVerblijfsobject/bagexample"
						+ String.format("%08d", 0) + ".kml";
				url = url_template.replace("abcdefghij", args[1]);
				url = url.replace("0123456789", args[2]);
				System.out.println(url);
				found = http.sendGet(url, formatted_kml);
				if (found) {
					BAGVerblijfsobjectExtract verblijfsplaats = new BAGVerblijfsobjectExtract();
					verblijfsplaats.process_verblijfplaatsen();
				}
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_verblijfsobject_file, true)));
				out.print("]}");
				out.close();
				break;
			case "BAGAlbertHeijn":
				/* 439100000006313 479100000079511 396100000082990 */
				/*
				 * out = new PrintWriter(new BufferedWriter(new FileWriter(
				 * BAGExtracts.json_albertheijn_file, false)));
				 * out.println("{\"type\":\"FeatureCollection\",\"features\":["
				 * ); out.println(
				 * "{\"type\":\"Feature\",\"id\":\"Albert_Heijn\",\"properties\":{\"name\":\"Albert_Heijn\"},\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":"
				 * ); out.println("[[["); out.close(); url_template =
				 * "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=pand&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:identificatie%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml"
				 * ; for (int j = 1; j < args.length; j++) { formatted_kml =
				 * "/home/jan/Downloads/BAGAlbertHeijn/bagexample" +
				 * String.format("%08d", j - 1) + ".kml"; url =
				 * "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=pand&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:identificatie%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml"
				 * ; url = url_template.replace("0123456789", args[j]);
				 * System.out.println(url); found = http.sendGet(url,
				 * formatted_kml); if (found) { BAGAlbertHeijnExtractJSON winkel
				 * = new BAGAlbertHeijnExtractJSON();
				 * winkel.process_pands(formatted_kml); out = new PrintWriter(
				 * new BufferedWriter( new FileWriter(
				 * BAGExtracts.json_albertheijn_file, true))); if (j <
				 * args.length - 1) { out.println("]],\n[["); } else {
				 * out.println("]]"); } out.close(); } } out = new
				 * PrintWriter(new BufferedWriter(new FileWriter(
				 * BAGExtracts.json_albertheijn_file, true)));
				 * out.println("]}}]}"); out.close();
				 */
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_albertheijn_file, false)));
				out.println("var BAG_Alberheijn_Placemarks = ");
				out.println("{\"type\":\"FeatureCollection\",\"features\":[");
				out.close();
				for (int j = 1; j < args.length; j++) {
					formatted_kml = "/home/jan/Downloads/BAGAlbertHeijn/bagexample"
							+ String.format("%08d", j - 1) + ".kml";
					url_template = "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=pand&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:identificatie%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml";
					url = url_template.replace("0123456789", args[j]);
					System.out.println(url);
					found = http.sendGet(url, formatted_kml);
					if (found) {
						BAGAlbertHeijnExtract albertheijn = new BAGAlbertHeijnExtract();
						albertheijn.process_pands(formatted_kml,
								Integer.toString(j - 1), "", "");
						out = new PrintWriter(new BufferedWriter(
								new FileWriter(js_albertheijn_file, true)));
						if (j < args.length - 1) {
							out.println(",");
						} else {
							out.println("");
						}
						out.close();
					}
				}
				out = new PrintWriter(new BufferedWriter(new FileWriter(
						js_albertheijn_file, true)));
				out.println("]}");
				out.close();
				break;
			case "BAGAlbertHeijnWinkels":
				BAGAlbertHeijnWinkels BAGWinkels = new BAGAlbertHeijnWinkels();
				found = true;
				while (found) {
					found = false;
					List<String> winkels = BAGWinkels.process_winkels();
					url_template = "http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=pand&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:identificatie%3C/PropertyName%3E%3CLiteral%3E0123456789%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml";
					System.out.println(winkels.size());
					out = new PrintWriter(new BufferedWriter(new FileWriter(
							js_albertheijn_file, false)));
					out.println("var BAG_Alberheijn_Placemarks = ");
					out.println("{\"type\":\"FeatureCollection\",\"features\":[");
					out.close();
					for (int i = 0; i < winkels.size(); i++) {
						String s = winkels.get(i);
						ahwinkels = s.split(",");
						if (ahwinkels[2] != null) {
							String t = kml_albertheijn_file;
							t = t.replace("00000000", ahwinkels[2]);
							File f = new File(t);
							if (f.exists() && !f.isDirectory()) {
								System.out.println("Exists: " + t);
								BAGAlbertHeijnExtract albertheijn = new BAGAlbertHeijnExtract();
								albertheijn.process_pands(t, ahwinkels[2],
										ahwinkels[1], ahwinkels[3]);
							} else {
								if (!t.equals("/home/jan/Downloads/BAGAlbertHeijn/bagexample000000000000000.kml")) {
									System.out.println("New: " + t);
									url = url_template.replace("0123456789",
											ahwinkels[2]);
									found = http.sendGet(url, t);
									if (found) System.exit(0);
								}
							}
						}
						out = new PrintWriter(new BufferedWriter(
								new FileWriter(js_albertheijn_file, true)));
						if (i < winkels.size() - 1) {
							out.println(",");
						} else {
							out.println("");
						}
						out.close();
					}
					out = new PrintWriter(new BufferedWriter(new FileWriter(
							js_albertheijn_file, true)));
					out.println("]};");
					out.println("function getColor(d) {");
//						return d > 1000 ? '#800026' :
//						       d > 500  ? '#BD0026' :
//						       d > 200  ? '#E31A1C' :
//						       d > 100  ? '#FC4E2A' :
//						       d > 50   ? '#FD8D3C' :
//						       d > 20   ? '#FEB24C' :
//						       d > 10   ? '#FED976' :
//						                  '#FFEDA0';
					out.println("return '#0000FF';");
					out.println("}");
					out.println("for(var i in BAG_Alberheijn_Placemarks.features)");
					out.println("BAG_Alberheijn_Placemarks.features[i].properties.color = getColor( BAG_Alberheijn_Placemarks.features[i].properties.oppervlakte );");
					out.close();
				}
				break;
			default:
			}
		}

	}

	private boolean sendGet(String url, String kml_file) throws Exception {

		boolean found = false;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		if (responseCode != 200) {
			found = false;
		} else {
			found = true;
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(kml_file, false)));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				out.println(inputLine);
			}
			in.close();
			out.close();
		}
		return found;
	}
}