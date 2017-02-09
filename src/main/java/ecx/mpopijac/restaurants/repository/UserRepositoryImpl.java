package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
