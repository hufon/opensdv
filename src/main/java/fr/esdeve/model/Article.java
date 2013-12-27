package fr.esdeve.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Article implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Article()
	{
	}
	

    @Id
	private String id;
	private String designation;
	
	private Integer initialPrice = 0;
	private Integer minimumPrice = 0;
	
	private Integer sellingPrice = 0;
	
	private Boolean retired = false;
	
	@ManyToOne
	private Vente vente;
	
	@ManyToOne
	private Vendor vendor;

	
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public Vente getVente() {
		return vente;
	}
	public void setVente(Vente vente) {
		this.vente = vente;
	}
	public Integer getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(Integer initialPrice) {
		this.initialPrice = initialPrice;
	}
	public Integer getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(Integer sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public Integer getMinimumPrice() {
		return minimumPrice;
	}
	public void setMinimumPrice(Integer minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	public Boolean getRetired() {
		return retired;
	}
	public void setRetired(Boolean retired) {
		this.retired = retired;
	}
	

}
