package com.example.demo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "REST_LOG_EXCEPTION")
public class RestExceptionModel {
	@Id
	@GeneratedValue
	private Integer id;
	private String method;
	private String clazz;
	@Lob
	private String request;
	@Lob
	private String exception;
	private Date createDt;

	public RestExceptionModel(String method, String clazz, String request, String exception) {
		super();
		this.method = method;
		this.clazz = clazz;
		this.request = request;
		this.exception = exception;
		this.createDt = new Date();
	}

	public RestExceptionModel() {
		super();
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
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

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

}
