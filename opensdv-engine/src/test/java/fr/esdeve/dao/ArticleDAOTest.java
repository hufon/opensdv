package fr.esdeve.dao;

import java.util.List;

import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.esdeve.model.Article;
import fr.esdeve.model.Vente;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-test.xml"
})
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })

@DatabaseSetup("classpath:dataSet.xml")	
public class ArticleDAOTest {
	
	@Autowired
	IArticleDAO articleDAO;
	
	
	@Test
	public void listArticleByVenteTest()
	{
		Vente vente = new Vente();
		vente.setId("1");
		List<Article> articles = articleDAO.listArticleByVente(vente);
		Assert.assertEquals(articles.size(), 1);
	}
	
	@Before
	public void init()
	{
		Persistence.createEntityManagerFactory("ventes");
	}
	

}
