package ecx.mpopijac.restaurants.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ecx.mpopijac.restaurants.models.Role;
import ecx.mpopijac.restaurants.models.User;
import ecx.mpopijac.restaurants.service.RoleService;
import ecx.mpopijac.restaurants.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping(value="/crud-user", method=RequestMethod.GET)
	public String crudUserPage(Model model){
		List<User> users = userService.findAll();
		model.addAttribute("users",users);		
		return "crud-user";
	}
	
	@RequestMapping(value="/c-user", method=RequestMethod.GET)
	public String createUserPage(Model model){
		model.addAttribute("heading","Add new user");
		model.addAttribute("buttonAction","Add new user");
		model.addAttribute("user",new User());
		model.addAttribute("operation","CREATE");
		List<Role> roles = roleService.findAll();
		model.addAttribute("roles",roles);
		return "cu-user";
	}
	
	@RequestMapping(value="/u-user", method=RequestMethod.GET)
	public String updateUserPage(HttpServletRequest request, Model model){
		model.addAttribute("heading","Update user");
		model.addAttribute("buttonAction","Update user");
		User user = userService.findById(Integer.parseInt(request.getParameter("id")));
		model.addAttribute("user",user);
		model.addAttribute("operation","UPDATE");
		List<Role> roles = roleService.findAll();
		model.addAttribute("roles",roles);
		return "cu-user";
	}
	
	//Fetch data and delete/create/update user
	@RequestMapping(value="/crud-user", method=RequestMethod.POST)
	public String addCreateDeleteUserPage(HttpServletRequest request, Model model){
		String operation = request.getParameter("operation");
		switch(operation){
		case "DELETE": {
			User user = new User();
			user.setId(Integer.parseInt(request.getParameter("id")));
			userService.delete(user);
			break;
		}
		case "CREATE": {
			User user = new User();
			user.setFirstName(request.getParameter("firstName"));
			user.setLastName(request.getParameter("lastName"));
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user.setEmail(request.getParameter("email"));
			user.setRole(roleService.findById(Integer.parseInt(request.getParameter("role"))));
			userService.save(user);
			break;
		}
		case "UPDATE":
			User user = new User();
			user.setFirstName(request.getParameter("firstName"));
			user.setLastName(request.getParameter("lastName"));
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user.setEmail(request.getParameter("email"));
			user.setId(Integer.parseInt(request.getParameter("id")));
			user.setRole(roleService.findById(Integer.parseInt(request.getParameter("role"))));
			userService.update(user);
			break;
		}
		List<User> users = userService.findAll();
		model.addAttribute("users",users);
		return "crud-user";
	}
}
