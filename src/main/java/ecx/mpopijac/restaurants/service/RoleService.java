package ecx.mpopijac.restaurants.service;

import java.util.List;

import ecx.mpopijac.restaurants.models.Role;


public interface RoleService {
	Role save(Role article);
	List<Role> findAll();
	Role findById(int id);
	Role findByName(String name);
}
