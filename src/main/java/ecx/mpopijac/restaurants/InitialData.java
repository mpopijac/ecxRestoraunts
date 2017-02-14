package ecx.mpopijac.restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ecx.mpopijac.restaurants.models.Role;
import ecx.mpopijac.restaurants.models.User;
import ecx.mpopijac.restaurants.service.RoleService;
import ecx.mpopijac.restaurants.service.UserService;

@Component
public class InitialData implements ApplicationRunner{

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		loadInitialData();
		
	}
	
	private void loadInitialData(){
		Role role = roleService.findById(1);
		if(role==null){
			role = new Role(1,"Administrator");
			roleService.save(role);
		}
		
		User user = userService.findByUsername("admin");
		if(user==null){
			user = new User("Admin", "Admin", "admin", "admin@email.com", bCryptPasswordEncoder.encode("admin"), role);
			userService.save(user);
		}
		
		role = roleService.findById(2);
		if(role==null){
			role = new Role(2,"Unregistered user");
			roleService.save(role);
		}
	}

}
