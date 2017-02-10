package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ecx.mpopijac.restaurants.models.User;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public User save(User user) {
		em.persist(user);
		return user;
	}

	@Override
	public List<User> findAll() {
		return em.createQuery("select u from User u").getResultList();
	}

	@Override
	public User findById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public User findByUsername(String username) {
		return (User) em.createQuery("select u from User u where u.username = :username").setParameter("username", username).getSingleResult();
	}

	@Override
	public User findByEmail(String email) {
		return (User) em.createQuery("select u from User u where u.email=:email").setParameter("email", email).getSingleResult();
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return (User) em.createQuery("select u from User u where u.username=:username and u.password=:password").setParameter("username", username).setParameter("password", password).getSingleResult();
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		return (User) em.createQuery("select u from User u where u.email=:email and u.password=:password").setParameter("email", email).setParameter("password", password).getSingleResult();
	}

	@Override
	public void update(User user) {
		Query query = em.createQuery("update User u set u.firstName=:firstName, u.lastName=:lastName, u.username=:username, u.password=:password, u.email=:email, u.role=:role where u.id=:id");
		query.setParameter("firstName",user.getFirstName());
		query.setParameter("lastName", user.getLastName());
		query.setParameter("username", user.getUsername());
		query.setParameter("password", user.getPassword());
		query.setParameter("email", user.getEmail());
		query.setParameter("role", user.getRole());
		query.setParameter("id", user.getId());
		query.executeUpdate();		
	}

	@Override
	public void delete(User user) {
		em.createQuery("delete from User u where u.id=:id").setParameter("id", user.getId()).executeUpdate();		
	}

}
