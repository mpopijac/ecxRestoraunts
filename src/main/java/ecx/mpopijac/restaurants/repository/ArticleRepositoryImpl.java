package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ecx.mpopijac.restaurants.models.Article;

@Repository("articleRepository")
public class ArticleRepositoryImpl implements ArticleRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Article save(Article article) {
		em.persist(article);
		em.flush();
		return article;
	}

	@Override
	public List<Article> findAll() {
		return em.createQuery("select a from Article a").getResultList();
	}

	@Override
	public Article findById(int id) {
		return em.find(Article.class, id);
	}

}
