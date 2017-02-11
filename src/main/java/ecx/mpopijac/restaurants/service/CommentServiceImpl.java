package ecx.mpopijac.restaurants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecx.mpopijac.restaurants.models.Article;
import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.User;
import ecx.mpopijac.restaurants.repository.CommentRepository;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Transactional
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Transactional
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Transactional
	public Comment findById(int id) {
		return commentRepository.findById(id);
	}

	@Transactional
	public List<Comment> findByAuthor(User user) {
		return commentRepository.findByAuthor(user);
	}

	@Override
	public List<Comment> findAllApprovedCommentsByArticle(Article article) {
		return commentRepository.findAllApprovedCommentsByArticle(article);
	}

}
