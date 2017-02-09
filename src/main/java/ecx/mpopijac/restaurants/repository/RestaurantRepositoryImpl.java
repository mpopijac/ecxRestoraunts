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

}
