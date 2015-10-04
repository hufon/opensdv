package fr.esdeve.dao;

import java.util.List;

import fr.esdeve.model.ArticleVente;
import fr.esdeve.model.Vente;

public interface IArticleVenteDAO extends IGenericDAO<ArticleVente>{
    public Integer getNextArticleOrder(ArticleVente articleVente, Vente vente);
    public void setOrder(ArticleVente article, Integer targetOrder);
    public List<ArticleVente> listArticleByVente(Vente vente);
}
