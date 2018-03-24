package com.mvpjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "statusmessage")
@JsonPropertyOrder({"status", "message"})
@XmlType (propOrder={"status", "message"})
public class StatusMessage {

	private Integer status;
	private String message;
	
	public StatusMessage() {
	}
	
	public StatusMessage(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	@XmlElement(name = "status")
	@JsonProperty(value = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@XmlElement(name = "message")
	@JsonProperty(value = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "StatusMessage [status=" + status + ", message=" + message + "]";
	}
}
