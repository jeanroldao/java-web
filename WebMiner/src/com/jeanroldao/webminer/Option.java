package com.jeanroldao.webminer;

public class Option {
	private String text;
	private String value;
	
	public Option() {
		this("", "");
	}
	
	public Option(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return text;
	}
}
