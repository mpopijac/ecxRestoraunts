package ecx.mpopijac.restaurants.service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import ecx.mpopijac.restaurants.models.Comment;
import ecx.mpopijac.restaurants.models.User;

@Component
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	Environment env;

	@Async
	public void sendEmail(User user, Comment comment) throws InterruptedException {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO,
						new InternetAddress(env.getProperty("spring.mail.username")));
				mimeMessage.setFrom(new InternetAddress(user.getEmail()));
				mimeMessage.setSubject("Comment Service Restaurants");
				String message = "Comment from: " + user.getFirstName() + " " + user.getLastName() + "<"
						+ user.getEmail() + ">\n\n";
				message += "Comment: \n\t" + comment.getMessageContent().replace("<br/>", "\n");
				message += "\n\nLink for approving comments is: http://localhost:8080/comment-approve?id="
						+ comment.getHash();
				mimeMessage.setText(message);
			}
		};

		try {
			javaMailSender.send(preparator);
		} catch (MailException e) {
			System.out.println("Ne šalje" + e.getMessage());
		}

	}
}
