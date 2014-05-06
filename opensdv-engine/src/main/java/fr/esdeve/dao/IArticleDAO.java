package fr.esdeve.dao;

import fr.esdeve.dao.impl.GenericDAO;
import fr.esdeve.model.Article;
import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vente;

import java.util.List;

/**
 * Created by hubert on 06/05/14.
 */
public interface IArticleDAO extends IGenericDAO<Article> {
    public Integer getNextArticleOrder(Article article, Vente vente);
    public void setOrder(Article article, Integer targetOrder);
    public List<Article> listArticleByVente(Vente vente);
    public List<Article> listArticleByVendor(Vendor vendor);


}
