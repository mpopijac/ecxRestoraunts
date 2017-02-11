package ecx.mpopijac.restaurants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecx.mpopijac.restaurants.models.User;
import ecx.mpopijac.restaurants.repository.RoleRepository;
import ecx.mpopijac.restaurants.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Transactional
	public User save(User user) {
		if (user.getRole() == null) {
			user.setRole(roleRepository.findById(2));
		}
		return userRepository.save(user);
	}

	@Transactional
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Transactional
	public User findById(int id) {
		return userRepository.findById(id);
	}

	@Transactional
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Transactional
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Transactional
	public User findByUsernameAndPassword(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

	@Transactional
	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Transactional
	public void update(User user) {
		userRepository.update(user);
	}

	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}

}
