package ecx.mpopijac.restaurants.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ecx.mpopijac.restaurants.models.Operation;
import ecx.mpopijac.restaurants.models.Role;
import ecx.mpopijac.restaurants.models.User;
import ecx.mpopijac.restaurants.service.RoleService;
import ecx.mpopijac.restaurants.service.UserService;

@Controller
public class SignUpController {
	
	public static final Pattern VALID_EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_USERNAME = Pattern.compile("^[a-z0-9_.]{5,15}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_NAME = Pattern.compile("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð _-]{2,30}$", Pattern.CASE_INSENSITIVE);

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUpUser(Model model) {
		model.addAttribute("heading", "Sign Up");
		model.addAttribute("buttonAction", "Create an account");
		model.addAttribute("user", new User());
		model.addAttribute("operation", Operation.CREATE);
		List<Role> roles = roleService.findAll();
		model.addAttribute("roles", roles);
		return "signup";
	}

	@RequestMapping(value = "/signup-error", method = RequestMethod.GET)
	public String signupError(Model model) {
		model.addAttribute("signupError", true);
		return "signup";
	}

	@RequestMapping(value = "/signup-user", method = RequestMethod.POST)
	public String SignUpUser(HttpServletRequest request, Model model) {
		
		boolean error = false;
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");
		String email = request.getParameter("email");
				
		Matcher matcher = VALID_NAME.matcher(firstName);
		if(!matcher.find()){
			model.addAttribute("FirstNameError", "First name must be more than 1 and less than 30 characters long!");
			error = true;
		}	
		
		matcher = VALID_NAME.matcher(lastName);
		if(!matcher.find()){
			model.addAttribute("LastNameError", "Last name must be more than 1 and less than 30 characters long!");
			error = true;
		}
		
		matcher = VALID_USERNAME.matcher(username);
		if(!matcher.find()){
			model.addAttribute("UsernameError1", "The username can only consist of alphabetical, number, dot and underscore!");
			model.addAttribute("UsernameError2", "The username must be more than 4 and less than 15 characters long!");
			error = true;
		}
		
		if(password.length()<6){
			model.addAttribute("PassError1", "Passwords must have at least 6 characters!");
			error = true;
		}
		
		if(!password.equals(cpassword)){
			model.addAttribute("PassError2", "Passwords don't match!");	
			error = true;
		}
		
		matcher = VALID_EMAIL.matcher(email);
		if(!matcher.find()){
			model.addAttribute("EmailError", "The input is not a valid email address!");	
			error = true;
		}	
		
    	User kor = userService.findByUsername(username);
    	if(kor!=null){
			model.addAttribute("UsernameError1", "This username is already taken!");
			error = true;
		}		
		
		if(error){
			model.addAttribute("signUpError", error);
			return signUpUser(model);			
		}
		
		User user = new User();
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setUsername(request.getParameter("username"));
		user.setPassword(bCryptPasswordEncoder.encode(request.getParameter("password")));
		user.setEmail(request.getParameter("email"));
		user.setRole(roleService.findByName("User"));
		userService.save(user);
		
		return "redirect:login";
	}

}
