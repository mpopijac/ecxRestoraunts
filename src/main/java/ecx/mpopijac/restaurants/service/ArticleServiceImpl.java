package ecx.mpopijac.restaurants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ecx.mpopijac.restaurants.models.Article;
import ecx.mpopijac.restaurants.repository.ArticleRepository;

public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;
	
	@Transactional
	public Article save(Article article) {
		return articleRepository.save(article);
	}

	@Transactional
	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	@Transactional
	public Article findById(int id) {
		return articleRepository.findById(id);
	}

}
