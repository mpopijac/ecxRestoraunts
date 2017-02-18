package ecx.mpopijac.restaurants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecx.mpopijac.restaurants.models.Article;
import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.ServiceStatus;
import ecx.mpopijac.restaurants.models.User;
import ecx.mpopijac.restaurants.repository.ArticleRepository;
import ecx.mpopijac.restaurants.repository.CommentRepository;
import ecx.mpopijac.restaurants.repository.RoleRepository;
import ecx.mpopijac.restaurants.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Transactional
	public User save(User user) {
		if (user.getRole() == null) {
			user.setRole(roleRepository.findById(2));
		}
		// user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
	public ServiceStatus delete(User user) {
		user = userRepository.findById(user.getId());
		List<Article> articles = articleRepository.findByAuthor(user);
		List<Comment> comments = commentRepository.findByAuthor(user);
		if (articles.isEmpty() && comments.isEmpty()) {
			userRepository.deleteById(user.getId());
			return ServiceStatus.SUCCESS;
		}
		return ServiceStatus.ERROR;

	}

	@Transactional
	public User findByUsernameOrEmail(String usernameOrEmail) {
		return userRepository.findByUsernameOrEmail(usernameOrEmail);
	}

	@Transactional
	public User findAdminByUsernameOrEmail(String usernameOrEmail) {
		return userRepository.findAdminByUsernameOrEmail(usernameOrEmail);
	}

}
