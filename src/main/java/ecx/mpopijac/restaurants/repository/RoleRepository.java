package ecx.mpopijac.restaurants.repository;

import java.util.List;

import ecx.mpopijac.restaurants.models.Role;

public interface RoleRepository {
	Role save(Role article);
	List<Role> findAll();
	Role findById(int id);
	Role findByName(String name);
}
