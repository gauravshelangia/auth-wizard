package com.indorse.assign.auth.authwizard.service;

import com.indorse.assign.auth.authwizard.model.Users;
import com.indorse.assign.auth.authwizard.service.api.EmailService;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String CNF_SUBJECT = "Auth Wizard. Please confirm your email!!";
    private static final String CNF_TEXT = "Click the below link to confirm your account \n ";

    @Autowired
    private SendGrid sendGrid;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    @Override
    public boolean sendUserRegistrationEmail(Users user) {

        Email from = new Email(fromEmail);
        Email to = new Email(user.getEmail());
        Content content = new Content("text/plain",
                CNF_TEXT+"http://localhost:8080/auth-wizard/confirm?token="+user.getConfirmationToken());
        Mail mail = new Mail(from, CNF_SUBJECT, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = this.sendGrid.api(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
