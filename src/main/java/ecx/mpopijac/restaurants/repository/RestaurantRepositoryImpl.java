package ecx.mpopijac.restaurants.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ecx.mpopijac.restaurants.models.Restaurant;

@Repository("restaurantRepository")
public class RestaurantRepositoryImpl implements RestaurantRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Restaurant save(Restaurant restaurant) {
		em.persist(restaurant);
		em.flush();
		return restaurant;
	}

	@Override
	public List<Restaurant> findByName(String name) {
		Query query = em.createQuery("select r from Restaurant r where r.name LIKE :name").setParameter("name", name);
		List<Restaurant> restaurants = query.getResultList();
		return restaurants;
	}

	@Override
	public Restaurant findId(int id) {
		return em.find(Restaurant.class, id);
	}

	@Override
	public List<Restaurant> findAll() {
		return em.createQuery("select r from Restaurant r").getResultList();
	}

	@Override
	public void delete(Restaurant restaurant) {
		em.createQuery("delete from Restaurant r where r.id=:id").setParameter("id", restaurant.getId()).executeUpdate();
	}

	@Override
	public void update(Restaurant restaurant) {
		Query query = em.createQuery("update Restaurant r set r.name=:name, r.address=:address, r.description=:description where r.id=:id");
		query.setParameter("name",restaurant.getName());
		query.setParameter("address", restaurant.getAddress());
		query.setParameter("description", restaurant.getDescription());
		query.setParameter("id", restaurant.getId());
		query.executeUpdate();		
	}

}
