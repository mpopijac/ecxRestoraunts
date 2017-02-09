package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ecx.mpopijac.restaurants.models.Role;

public class RoleRepositoryImpl implements RoleRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Role save(Role article) {
		em.persist(article);
		return article;
	}

	@Override
	public List<Role> findAll() {
		return em.createQuery("select r from Role r").getResultList();
	}

	@Override
	public Role findById(int id) {
		return em.find(Role.class, id);
	}

	@Override
	public Role findByName(String name) {
		return (Role) em.createQuery("select r from Role r where r.name = :name").setParameter("name", name).getSingleResult();
	}

}
