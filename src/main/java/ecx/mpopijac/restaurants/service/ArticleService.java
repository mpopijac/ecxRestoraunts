package ecx.mpopijac.restaurants.service;

import java.util.List;

import ecx.mpopijac.restaurants.models.Article;
import ecx.mpopijac.restaurants.models.User;

public interface ArticleService {
	Article save(Article article);

	List<Article> findAll();

	Article findById(int id);

	void delete(Article article);

	void update(Article article);
	
	List<Article> findByAuthor(User author);
}
