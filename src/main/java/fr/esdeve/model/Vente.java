package fr.esdeve.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Vente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vente()
	{
		this.name = "VAE_";
		this.date = new Date();
		
		this.name += DateFormat.getInstance().getCalendar().get(DateFormat.YEAR_FIELD);
		//this.name += this.date.getMonth();
	}
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@OneToMany
	private List<Article> articles;
	
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
