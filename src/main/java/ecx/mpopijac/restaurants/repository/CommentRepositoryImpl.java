package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

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
		return em.createQuery("select c from Comment c where c.author.id = :id").setParameter("id", user.getId())
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

	@Override
	public Integer approveCommentByHash(String hash) {
		try{
			Query query = em.createQuery("update Comment c set c.approved=:true where c.hash=:hash and c.approved <> :true");
			query.setParameter("hash", hash);
			query.setParameter("true", true);
		
			return query.executeUpdate();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
		
	}

	@Override
	public int delete(int id) {
		try{
			return em.createQuery("delete from Comment c where c.id=:id").setParameter("id", id).executeUpdate();
		}catch (IllegalStateException | PersistenceException e) {
			System.out.println(e.getMessage());
			System.out.println("Deleting error.");
		}
		return -1;
	}

	@Override
	public int unapproveCommentById(int id) {
		try{
			return em.createQuery("update Comment c set c.approved=:true where c.id=:id and c.approved <> :true").setParameter("true", false).setParameter("id", id).executeUpdate();
		}catch (IllegalStateException | PersistenceException e) {
			System.out.println(e.getMessage());
			System.out.println("Unapproving error.");
		}
		return -1;
	}

	@Override
	public int approveCommentById(int id) {
		try{
			Query query = em.createQuery("update Comment c set c.approved=:true where c.id=:id and c.approved <> :true");
			query.setParameter("id", id);
			query.setParameter("true", true);
			return query.executeUpdate();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

}
