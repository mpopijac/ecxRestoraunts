package ecx.mpopijac.restaurants.service;

import java.util.List;

import ecx.mpopijac.restaurants.models.Restaurant;

public interface RestaurantService {
	
	Restaurant save(Restaurant restaurant);
	List<Restaurant> findAll();
	Restaurant findById(int id);
	List<Restaurant> findByName(String name);
	void delete(Restaurant restaurant);
	void update(Restaurant restaurant);

}
