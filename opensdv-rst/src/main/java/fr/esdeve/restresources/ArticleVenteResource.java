package fr.esdeve.restresources;

import fr.esdeve.dao.IArticleVenteDAO;
import fr.esdeve.dao.IVendorDAO;
import fr.esdeve.dao.IVenteDAO;
import fr.esdeve.model.ArticleVente;
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



@Component
@Scope("prototype")
public class ArticleVenteResource extends ServerResource {

	
	@Autowired
	private IArticleVenteDAO articleVenteDAO;
	
	
    @Autowired
    private IVendorDAO vendorDAO;

    @Autowired
    private IVenteDAO venteDAO;

    private Long articleVenteId;

    private String venteId;


    @Override
    protected void doInit() throws ResourceException {
        this.articleVenteId = Long.parseLong(getAttribute("articleVenteId"));
    }
	
	@Get("json")
    public Representation get() {
        ArticleVente articleVente = articleVenteDAO.get(articleVenteId);
        if (articleVente == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new JacksonRepresentation<ArticleVente>(articleVente);
    }
	
    @Post("json")
    public void create(Representation representation) throws IOException {
        JacksonRepresentation<ArticleVente> jsonRepresentation = new JacksonRepresentation<ArticleVente>(representation, ArticleVente.class);
        ArticleVente newArticle = jsonRepresentation.getObject();
        articleVenteDAO.addBean(newArticle);
    }

}
