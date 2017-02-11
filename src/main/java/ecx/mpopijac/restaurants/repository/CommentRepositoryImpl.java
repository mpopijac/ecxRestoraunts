package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ecx.mpopijac.restaurants.models.Article;
import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.User;

@Repository("commentRepository")
public class CommentRepositoryImpl implements CommentRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Comment save(Comment comment) {
		em.persist(comment);
		em.flush();
		return comment;
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
		return em.createQuery("select c from Comment c where c.id = :id").setParameter("id", user.getId())
				.getResultList();
	}

	@Override
	public List<Comment> findAllApprovedCommentsByArticle(Article article) {
		try {
			return em.createQuery("select c from Comment c where c.approved = :approved and c.article=:article")
					.setParameter("approved", true).setParameter("article", article).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
