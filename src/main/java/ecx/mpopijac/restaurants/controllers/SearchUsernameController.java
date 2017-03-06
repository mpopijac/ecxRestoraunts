package ecx.mpopijac.restaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecx.mpopijac.restaurants.models.User;
import ecx.mpopijac.restaurants.service.UserService;

@RestController
public class SearchUsernameController {
	
	@Autowired
	UserService userService;

    @RequestMapping("/getUser")
    public User greeting(@RequestParam(value="username", defaultValue="null") String username) {
    	User user = userService.findByUsername(username);
        return user;
    }
}
