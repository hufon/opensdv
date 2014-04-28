package fr.esdeve.restresources;

import fr.esdeve.dao.ArticleDAO;
import fr.esdeve.dao.VendorDAO;
import fr.esdeve.model.Article;
import fr.esdeve.model.Vendor;
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
	private ArticleDAO articleDAO;

    @Autowired
    private VendorDAO vendorDAO;

    private String vendorId;


    @Override
    protected void doInit() throws ResourceException {
        this.vendorId = getAttribute("vendorId");
    }
	
	@Get("json")
    public Representation get() {
        List<Article> articles;
        if (vendorId == null) {
            articles = articleDAO.list();
            if (articles == null) {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
        } else

        {
            Vendor vendor = vendorDAO.get(this.vendorId);
            if (vendor == null)
            {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            articles = articleDAO.listArticleByVendor(vendor);
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
