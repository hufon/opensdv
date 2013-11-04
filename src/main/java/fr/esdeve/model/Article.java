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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String designation;
	
	@ManyToOne
	private Vente vente;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	

}
