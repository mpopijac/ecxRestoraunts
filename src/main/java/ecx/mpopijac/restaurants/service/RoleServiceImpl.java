package ecx.mpopijac.restaurants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ecx.mpopijac.restaurants.models.Role;
import ecx.mpopijac.restaurants.repository.RoleRepository;

public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Transactional
	public Role save(Role article) {
		return roleRepository.save(article);
	}

	@Transactional
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Transactional
	public Role findById(int id) {
		return roleRepository.findById(id);
	}

	@Transactional
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

}
