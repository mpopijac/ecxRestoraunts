package ecx.mpopijac.restaurants.service;

import java.util.List;

import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.User;

public interface CommentService {
	Comment save(Comment article);
	List<Comment> findAll();
	Comment findById(int id);
	List<Comment> findByAuthor(User user);
}
