package com.mylab;

public class Placemark {

	String gml_id;
	String identificatie;
	String bouwjaar;
	String oppervlakte;
	String status;
	String openbare_ruimte;
	String huisnummer;
	String huisletter;
	String postcode;
	String woonplaats;
	String coordinates;
	String gebruiksdoel;

	public String getGml_id() {
		return gml_id;
	}

	public void setGml_id(String gml_id) {
		this.gml_id = gml_id;
	}

	public String getIdentificatie() {
		return identificatie;
	}

	public void setIdentificatie(String identificatie) {
		this.identificatie = identificatie;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getGebruiksdoel() {
		return gebruiksdoel;
	}

	public void setGebruiksdoel(String gebruiksdoel) {
		this.gebruiksdoel = gebruiksdoel;
	}
	
	public String getOppervlakte() {
		return oppervlakte;
	}

	public void setOppervlakte(String oppervlakte) {
		this.oppervlakte = oppervlakte;
	}
	
	public String getBouwjaar() {
		return bouwjaar;
	}

	public void setBouwjaar(String bouwjaar) {
		this.bouwjaar = bouwjaar;
	}

	@Override
	public String toString() {
		return this.postcode + ":" + this.woonplaats + ":" + this.huisnummer;
	}

	public String getOpenbare_ruimte() {
		return openbare_ruimte;
	}

	public void setOpenbare_ruimte(String openbare_ruimte) {
		this.openbare_ruimte = openbare_ruimte;
	}

	public String getHuisnummer() {
		return huisnummer;
	}

	public void setHuisnummer(String huisnummer) {
		this.huisnummer = huisnummer;
	}

	public String getHuisletter() {
		return huisletter;
	}

	public void setHuisletter(String huisletter) {
		this.huisletter = huisletter;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getWoonplaats() {
		return woonplaats;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}
	
	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String value) {
		this.coordinates = value;		
	}
}
