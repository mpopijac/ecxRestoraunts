package ecx.mpopijac.restaurants.repository;

import java.util.List;

import ecx.mpopijac.restaurants.models.Article;

public interface ArticleRepository {
	Article save(Article article);
	List<Article> findAll();
	Article findById(int id);
}
