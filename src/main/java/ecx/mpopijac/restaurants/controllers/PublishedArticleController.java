package ecx.mpopijac.restaurants.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ecx.mpopijac.restaurants.models.Article;
import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.User;
import ecx.mpopijac.restaurants.service.ArticleService;
import ecx.mpopijac.restaurants.service.CommentService;
import ecx.mpopijac.restaurants.service.MailService;
import ecx.mpopijac.restaurants.service.UserService;

@Controller
public class PublishedArticleController {

	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService;

	@Autowired
	CommentService commentService;
	
	@Autowired
	MailService mailService;

	@RequestMapping(value = "/published-article", method = RequestMethod.GET)
	public String publishedArticlePage(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		if (id == null || id.equals("")) {
			return "index";
		}
		Article article = articleService.findById(Integer.parseInt(id));
		model.addAttribute("article", article);
		model.addAttribute("comments", commentService.findAllApprovedCommentsByArticle(article));
		return "published-article";
	}

	@RequestMapping(value = "/published-article", method = RequestMethod.POST)
	public String saveCommentPublishedArticlePage(HttpServletRequest request, Model model) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String commentText = request.getParameter("comment");
		int articleId = Integer.parseInt(request.getParameter("id"));
		commentText = commentText.replace("\n", "<br/>");
		User user = userService.findByEmail(email);
		if (user == null) {
			user = new User(firstName, lastName, email);
			userService.save(user);
			user = userService.findByEmail(email);
		}

		Article article = articleService.findById(articleId);
		Comment comment = new Comment(commentText, user, article);
		commentService.save(comment);
		try{
			mailService.sendEmail(user, comment);
		}catch (MailException | InterruptedException e) {
			System.out.println("Nije htjelo poslati poruku"+e.getMessage());
		}
		model.addAttribute("article", article);
		List<Comment> comments = commentService.findAllApprovedCommentsByArticle(article);

		model.addAttribute("comments", comments);
		return "published-article";
	}

}
