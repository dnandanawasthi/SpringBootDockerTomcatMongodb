package com.mvpjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlRootElement(name = "incident")
@XmlType (propOrder={"id", "problemDescription", "severity", "status",
		"createdBy", "assignedTo", "component", "createDate", "closeDate",
		"lastUpdatedDate", "daysOpen", "solutionDescription",})
@Document
@JsonPropertyOrder({"id", "problemDescription", "severity", "status",
	"createdBy", "assignedTo", "component", "createDate", "closeDate",
	"lastUpdatedDate", "daysOpen", "solutionDescription"})
public class Incident {
	
	@Id
	private String id;
	private String problemDescription;
	private String severity;
	private String status;
	private String createdBy;
	private String assignedTo;
	private String component;
	private Date createDate;
	private Date closeDate;
	private Date lastUpdatedDate;
	private int daysOpen;
	private String solutionDescription;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getDaysOpen() {
		return daysOpen;
	}

	public void setDaysOpen(int daysOpen) {
		this.daysOpen = daysOpen;
	}

	public String getSolutionDescription() {
		return solutionDescription;
	}

	public void setSolutionDescription(String solutionDescription) {
		this.solutionDescription = solutionDescription;
	}

	@Override
	public String toString() {
		return "Incident{" +
				"id='" + id + '\'' +
				", problemDescription='" + problemDescription + '\'' +
				", severity='" + severity + '\'' +
				", status='" + status + '\'' +
				", createdBy='" + createdBy + '\'' +
				", assignedTo='" + assignedTo + '\'' +
				", component='" + component + '\'' +
				", createDate=" + createDate +
				", closeDate=" + closeDate +
				", lastUpdatedDate=" + lastUpdatedDate +
				", daysOpen=" + daysOpen +
				", solutionDescription='" + solutionDescription + '\'' +
				'}';
	}
}
