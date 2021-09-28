package org.jomaveger.bookexamples.chapter6;

import org.jomaveger.functional.control.Option;

public class Address {

	private String street;
	private String city;
	private String country;
	private String countryCode;
	
	public Address(String street, String city, String country) {
		this.street = street;
		this.city = city;
		this.country = country;
	}
	
	public Address(String street, String city, String country,
					String countryCode) {
		this.street = street;
		this.city = city;
		this.country = country;
		this.countryCode = countryCode;
	}
	
	public Option<String> getCountryCode() {
		return Option.instance(countryCode);
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}
}
