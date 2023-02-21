package com.example.demo.product;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {

	@Id
	private String id;
	private String productname;
	@Override
	public String toString() {
		return "Product [id=" + id + ", productname=" + productname + "]";
	}
	
	
}
