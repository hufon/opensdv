package fr.esdeve.restresources;

import fr.esdeve.dao.IArticleDAO;
import fr.esdeve.dao.IVendorDAO;
import fr.esdeve.dao.IVenteDAO;
import fr.esdeve.dao.impl.ArticleDAO;
import fr.esdeve.dao.impl.VendorDAO;
import fr.esdeve.dao.impl.VenteDAO;
import fr.esdeve.model.Article;
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
import java.util.List;


@Component
@Scope("prototype")
public class ArticleListResource extends ServerResource {
	

	@Autowired
	private IArticleDAO articleDAO;

    @Autowired
    private IVendorDAO vendorDAO;

    @Autowired
    private IVenteDAO venteDAO;

    private String vendorId;

    private String venteId;


    @Override
    protected void doInit() throws ResourceException {
        this.vendorId = getQueryValue("vendorId");
        this.venteId = getQueryValue("venteId");
    }
	
	@Get("json")
    public Representation get() {
        List<Article> articles = null;
        if (vendorId != null)
        {
            Vendor vendor = vendorDAO.get(this.vendorId);
            if (vendor == null)
            {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            articles = articleDAO.listArticleByVendor(vendor);
        }
        if (venteId != null)
        {
            Vente vente = venteDAO.get(this.venteId);
            if (vente == null)
            {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            articles = articleDAO.listArticleByVente(vente);
        }
        if (vendorId == null && venteId==null) {
            articles = articleDAO.list();
            if (articles == null) {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
        }
        return new JacksonRepresentation<List<Article>>(articles);
    }
	
    @Post("json")
    public void create(Representation representation) throws IOException {
        JacksonRepresentation<Article> jsonRepresentation = new JacksonRepresentation<Article>(representation, Article.class);
        Article newArticle = jsonRepresentation.getObject();
        articleDAO.addBean(newArticle);
    }

}
