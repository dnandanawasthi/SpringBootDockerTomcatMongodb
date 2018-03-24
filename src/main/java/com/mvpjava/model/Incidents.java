package com.mvpjava.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "incidents")
public class Incidents {
	
	
	private List<Incident> incidents;

	public Incidents() {}
	
	public Incidents(List<Incident> incidents) {
		super();
		this.incidents = incidents;
	}

	@XmlElement(name = "incident")
	public List<Incident> getIncidents() {
		return incidents;
	}

	public void setIncidents(List<Incident> incidents) {
		this.incidents = incidents;
	}

	@Override
	public String toString() {
		return "Incidents []";
	}
}
