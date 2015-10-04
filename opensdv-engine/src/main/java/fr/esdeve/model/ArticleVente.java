package fr.esdeve.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ArticleVente {
    @ManyToOne
	private Vente vente;

	@Id
	private String id;

	private Integer initialPrice = 0;
	private Integer minimumPrice = 0;
	private Integer sellingPrice = 0;
	private Integer venteOrder = 0;
	private Boolean retired = false;
	
	@ManyToOne
	private Article article;

	public Vente getVente() {
		return vente;
	}
	public void setVente(Vente vente) {
		this.vente = vente;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	@ManyToOne
    private Client client;
	
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
	public Integer getVenteOrder() {
		return venteOrder;
	}
	public void setVenteOrder(Integer venteOrder) {
		this.venteOrder = venteOrder;
	}

}
