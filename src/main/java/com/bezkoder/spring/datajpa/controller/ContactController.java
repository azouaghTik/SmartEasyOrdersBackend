package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//@CrossOrigin(origins = "http://192.168.0.246:4200")
@CrossOrigin(origins = "https://smarteasyorders.com")

@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/client/contact/send-email")
    public String sendEmail(@RequestBody ContactForm contactForm) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("azouagh.yassin@gmail.com"); // Adresse de destination
            helper.setSubject(contactForm.getSubject());
            helper.setText("De : " + contactForm.getName() + "\nE-mail : " + contactForm.getEmail() + "\n\n" + contactForm.getMessage());

            javaMailSender.send(message);
            return "E-mail envoyé avec succès";
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
            return "Erreur lors de l'envoi de l'e-mail";
        }
    }
}
