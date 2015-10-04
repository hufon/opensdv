package fr.esdeve.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@ManyToOne
	private Vente vente;
	
	@ManyToOne
	private Vendor vendor;

    @ManyToOne
    private Client client;
    
	@OneToMany(mappedBy="article", cascade=CascadeType.REMOVE)
	@JsonIgnore
	private List<ArticleVente> articleVentes;
	
	public List<ArticleVente> getArticleVentes() {
		return articleVentes;
	}
	public void setArticleVentes(List<ArticleVente> articleVentes) {
		this.articleVentes = articleVentes;
	}
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
	


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
