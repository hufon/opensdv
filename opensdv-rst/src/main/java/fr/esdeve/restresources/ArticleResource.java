package fr.esdeve.restresources;


import fr.esdeve.dao.ArticleDAO;
import fr.esdeve.model.Article;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ArticleResource extends ServerResource {
	

	@Autowired
	private ArticleDAO articleDAO;
	
	private String articleId;

	@Override
	protected void doInit() throws ResourceException {
		this.articleId = getAttribute("articleId");
	}
	
	@Get("json")
    public Representation get() {
        Article article = articleDAO.get(articleId);
        if (article == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new JacksonRepresentation<Article>(article);
    }

    @Put("json")
    public void update(Representation representation) throws IOException {
        JacksonRepresentation<Article> jsonRepresentation = new JacksonRepresentation<Article>(representation, Article.class);
        Article article = jsonRepresentation.getObject();
        articleDAO.save(article);
    }
	
	@Delete
    public void remove() {
		articleDAO.remove(articleId);
    }

}
