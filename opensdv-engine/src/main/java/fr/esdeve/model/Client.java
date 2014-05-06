package fr.esdeve.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false)
    private String name;
        
    @OneToMany(mappedBy="client", cascade=CascadeType.REMOVE )
	private List<Article> articles;

    @Column(nullable = false)
    private Integer rate = 12;

    @Column(nullable = false)
    private Integer number = 0;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Vente vente;

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

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }
}
