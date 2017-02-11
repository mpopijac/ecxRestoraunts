package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@Override
	public void delete(Article article) {
		em.createQuery("delete from Article a where a.id=:id").setParameter("id", article.getId()).executeUpdate();
	}

	@Override
	public void update(Article article) {
		Query query = em.createQuery(
				"update Article a set a.headline=:headline, a.imageLocation=:imageLocation, a.description=:description, a.restaurant=:restaurant, a.author=:author where a.id=:id");
		query.setParameter("headline", article.getHeadline());
		query.setParameter("imageLocation", article.getImageLocation());
		query.setParameter("description", article.getDescription());
		query.setParameter("restaurant", article.getRestaurant());
		query.setParameter("author", article.getAuthor());
		query.setParameter("id", article.getId());
		query.executeUpdate();
	}

}
