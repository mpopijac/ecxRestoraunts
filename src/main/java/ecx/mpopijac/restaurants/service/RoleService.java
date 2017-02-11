package ecx.mpopijac.restaurants.service;

import java.util.List;

import ecx.mpopijac.restaurants.models.Role;

public interface RoleService {
	Role save(Role role);

	List<Role> findAll();

	Role findById(int id);

	Role findByName(String name);

	void update(Role role);

	void delete(Role role);
}
