package ecx.mpopijac.restaurants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecx.mpopijac.restaurants.models.Article;
import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.ServiceStatus;
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

	@Transactional
	public List<Comment> findAllApprovedCommentsByArticle(Article article) {
		return commentRepository.findAllApprovedCommentsByArticle(article);
	}

	@Transactional
	public ServiceStatus approveCommentByHash(String hash) {
		int numberOfChangedRows = commentRepository.approveCommentByHash(hash);
		switch(numberOfChangedRows){
			case 0:
				return ServiceStatus.ERROR;
			case 1:
				return ServiceStatus.SUCCESS;
			default:
				return ServiceStatus.UNKNOWN_ERROR;
		}
	}

	@Transactional
	public ServiceStatus delete(int id) {
		switch(commentRepository.deleteById(id)){
		case 0:
			return ServiceStatus.ERROR;
		case 1:
			return ServiceStatus.SUCCESS;
		default:
			return ServiceStatus.UNKNOWN_ERROR;
		}
	}

	@Transactional
	public ServiceStatus unapproveCommentById(int id) {
		switch(commentRepository.unapproveCommentById(id)){
		case 0:
			return ServiceStatus.ERROR;
		case 1:
			return ServiceStatus.SUCCESS;
		default:
			return ServiceStatus.UNKNOWN_ERROR;
		}
	}

	@Transactional
	public ServiceStatus approveCommentById(int id) {
		switch(commentRepository.approveCommentById(id)){
		case 0:
			return ServiceStatus.ERROR;
		case 1:
			return ServiceStatus.SUCCESS;
		default:
			return ServiceStatus.UNKNOWN_ERROR;
		}
	}

}
