package fr.esdeve.restresources;

import fr.esdeve.dao.IArticleDAO;
import fr.esdeve.dao.IArticleVenteDAO;
import fr.esdeve.dao.IVendorDAO;
import fr.esdeve.dao.IVenteDAO;
import fr.esdeve.dao.impl.ArticleDAO;
import fr.esdeve.dao.impl.VendorDAO;
import fr.esdeve.dao.impl.VenteDAO;
import fr.esdeve.model.Article;
import fr.esdeve.model.ArticleVente;
import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vente;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@Scope("prototype")
public class ArticleVenteListResource extends ServerResource {
	

	@Autowired
	private IArticleDAO articleDAO;

	
	@Autowired
	private IArticleVenteDAO articleVenteDAO;
	
	
    @Autowired
    private IVendorDAO vendorDAO;

    @Autowired
    private IVenteDAO venteDAO;

    private String articleId;

    private String venteId;


    @Override
    protected void doInit() throws ResourceException {
        this.articleId = getQueryValue("articleId");
        this.venteId = getQueryValue("venteId");
    }
	
	@Get("json")
    public Representation get() {
        List<ArticleVente> articlesventes = null;
        if (articleId != null)
        {
            Article article = articleDAO.get(this.articleId);
            if (article == null)
            {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            articlesventes = article.getArticleVentes();
        }
        return new JacksonRepresentation<List<ArticleVente>>(articlesventes);
    }
	
    @Post("json")
    public void create(Representation representation) throws IOException {
        JacksonRepresentation<ArticleVente> jsonRepresentation = new JacksonRepresentation<ArticleVente>(representation, ArticleVente.class);
        ArticleVente newArticle = jsonRepresentation.getObject();
        articleVenteDAO.addBean(newArticle);
    }

}
