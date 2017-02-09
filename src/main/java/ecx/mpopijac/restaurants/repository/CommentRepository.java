package ecx.mpopijac.restaurants.repository;

import java.util.List;

import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.User;

public interface CommentRepository {
	Comment save(Comment article);
	List<Comment> findAll();
	Comment findById(int id);
	List<Comment> findByAuthor(User user);
}
