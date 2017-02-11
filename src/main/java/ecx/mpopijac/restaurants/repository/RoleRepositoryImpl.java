package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ecx.mpopijac.restaurants.models.Role;

@Repository("roleRepository")
public class RoleRepositoryImpl implements RoleRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Role save(Role role) {
		em.persist(role);
		return role;
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
		return (Role) em.createQuery("select r from Role r where r.name = :name").setParameter("name", name)
				.getSingleResult();
	}

	@Override
	public void update(Role role) {
		em.createQuery("update Role r set r.name=:name where r.id=:id").setParameter("name", role.getName())
				.setParameter("id", role.getId()).executeUpdate();
	}

	@Override
	public void delete(Role role) {
		em.createQuery("delete from Role r where r.id=:id").setParameter("id", role.getId()).executeUpdate();
	}

}
