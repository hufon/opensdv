package fr.esdeve.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
		this.year = java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD);
	}
	

    @Id
	private String id;
    
    @Size(min=1)
    @NotNull
	private String name;
	private String location;
	
	private Integer year;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@OneToMany(mappedBy="vente", cascade=CascadeType.REMOVE)
	@JsonIgnore
	private List<ArticleVente> articles;
	
	public List<ArticleVente> getArticles() {
		return articles;
	}
	public void setArticles(List<ArticleVente> articles) {
		this.articles = articles;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return this.id.toString()+" - "+this.name;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
}
