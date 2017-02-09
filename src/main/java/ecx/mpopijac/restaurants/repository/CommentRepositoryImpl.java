package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.User;

@Repository("commentRepository")
public class CommentRepositoryImpl implements CommentRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Comment save(Comment article) {
		em.persist(article);
		em.flush();
		return article;
	}

	@Override
	public List<Comment> findAll() {
		return em.createQuery("select c from Comment c").getResultList();
	}

	@Override
	public Comment findById(int id) {
		return em.find(Comment.class, id);
	}

	@Override
	public List<Comment> findByAuthor(User user) {
		return em.createQuery("select c from Comment c where c.id = :id").setParameter("id", user.getId()).getResultList();
	}

}
