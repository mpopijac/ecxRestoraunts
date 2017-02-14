package ecx.mpopijac.restaurants.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ecx.mpopijac.restaurants.models.ServiceStatus;
import ecx.mpopijac.restaurants.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/comment-approve")
	public String publishedArticlePage(HttpServletRequest request, Model model) {
		String hash = request.getParameter("id");
		ServiceStatus status = commentService.approveCommentWithHash(hash);
		switch (status) {
		case SUCCESS:
			model.addAttribute("status", "Uspješno");
			break;
		case ERROR:
			model.addAttribute("status", "Komentar je već odobren ili je došlo do greške.");
			break;
		case UNKNOWN_ERROR:
			model.addAttribute("status", "Nepoznata greška.");
			break;
		}

		return "comment-approve";
	}
}
