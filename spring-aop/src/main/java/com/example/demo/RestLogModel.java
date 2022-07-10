package com.example.demo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "REST_LOG")
public class RestLogModel {
	@Id
	@GeneratedValue
	private Integer id;
	private String method;
	private String clazz;
	@Lob
	private String request;
	@Lob
	private String response;
	private Date createDt;

	public RestLogModel(String method, String clazz, String request, String response) {
		super();
		this.method = method;
		this.clazz = clazz;
		this.request = request;
		this.response = response;
		this.createDt = new Date();
	}

	public RestLogModel() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
