package ecx.mpopijac.restaurants.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ecx.mpopijac.restaurants.models.Article;
import ecx.mpopijac.restaurants.models.Restaurant;
import ecx.mpopijac.restaurants.service.ArticleService;
import ecx.mpopijac.restaurants.service.RestaurantService;
import ecx.mpopijac.restaurants.service.UserService;

@Controller
public class ArticleController {

	@Autowired
	Environment env;

	@Autowired
	ArticleService articleService;

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/crud-article", method = RequestMethod.GET)
	public String crudArticlePage(HttpServletRequest request, Model model) {
		String operation = request.getParameter("operation");
		if (operation != null && operation.equals("DELETE")) {
			Article article = new Article();
			article.setId(Integer.parseInt(request.getParameter("id")));
			articleService.delete(article);
		}
		List<Article> articles = articleService.findAll();
		model.addAttribute("articles", articles);
		return "crud-article";
	}

	@RequestMapping(value = "/c-article", method = RequestMethod.GET)
	public String createArticlePage(Model model) {
		model.addAttribute("heading", "Add new article");
		model.addAttribute("buttonAction", "Add new article");
		model.addAttribute("article", new Article());
		model.addAttribute("operation", "CREATE");
		List<Restaurant> restaurants = restaurantService.findAll();
		model.addAttribute("restaurants", restaurants);
		return "cu-article";
	}

	@RequestMapping(value = "/u-article", method = RequestMethod.GET)
	public String updateArticlePage(HttpServletRequest request, Model model) {
		model.addAttribute("heading", "Update article");
		model.addAttribute("buttonAction", "Update article");
		Article article = articleService.findById(Integer.parseInt(request.getParameter("id")));
		model.addAttribute("article", article);
		model.addAttribute("operation", "UPDATE");
		List<Restaurant> restaurants = restaurantService.findAll();
		model.addAttribute("restaurants", restaurants);
		return "cu-article";
	}

	// Fetch data and create/update article
	@RequestMapping(value = "/crud-article", method = RequestMethod.POST)
	public String addCreateArticlePage(HttpServletRequest request, Model model,
			@RequestParam("imageLocation") MultipartFile image) {
		String operation = request.getParameter("operation");
		switch (operation) {
		case "CREATE": {
			Article article = new Article();
			article.setHeadline(request.getParameter("headline"));
			article.setDescription(request.getParameter("description"));

			String filename = image.getOriginalFilename();
			String directory = env.getProperty("upload.file.path");
			String filePath = Paths.get("." + File.separator + directory, filename).toString();
			try {
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(image.getBytes());
				stream.close();

			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

			article.setImageLocation(filePath);
			article.setAuthor(userService.findById(1));
			article.setRestaurant(restaurantService.findById(Integer.parseInt(request.getParameter("restaurant"))));
			articleService.save(article);
			break;
		}
		case "UPDATE":
			Article article = new Article();
			article.setHeadline(request.getParameter("headline"));
			article.setDescription(request.getParameter("description"));
			article.setId(Integer.parseInt(request.getParameter("id")));
			
			String filename = image.getOriginalFilename();
			if (filename != null && !filename.equals("")) {
				String directory = env.getProperty("upload.file.path");
				String filePath = Paths.get("." + File.separator + directory, filename).toString();
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(image.getBytes());
					stream.close();

				} catch (IOException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				article.setImageLocation(filePath);
			}else{
				article.setImageLocation(articleService.findById(article.getId()).getImageLocation());
			}
			
			article.setAuthor(userService.findById(1));
			article.setRestaurant(restaurantService.findById(Integer.parseInt(request.getParameter("restaurant"))));
			
			articleService.update(article);
			break;
		}
		List<Article> articles = articleService.findAll();
		model.addAttribute("articles", articles);
		return "crud-article";
	}
}
