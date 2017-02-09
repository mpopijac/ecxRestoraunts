package ecx.mpopijac.restaurants.service;

import java.util.List;

import ecx.mpopijac.restaurants.models.Article;


public interface ArticleService {
	Article save(Article article);
	List<Article> findAll();
	Article findById(int id);
}
