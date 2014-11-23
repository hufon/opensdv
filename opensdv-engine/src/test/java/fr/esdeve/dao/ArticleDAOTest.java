package fr.esdeve.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.esdeve.model.Vente;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-test.xml"
})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
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
		articleDAO.listArticleByVente(vente);
	}

}
