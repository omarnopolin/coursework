package coursework.cashflow.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import coursework.cashflow.models.dao.Users;
import coursework.cashflow.utils.MailSender;

import java.net.UnknownHostException;

@Controller
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {
    @Autowired
    private MailSender mailSender;
    @PostMapping
    public String getForgotPassword(@RequestBody Users user) throws UnknownHostException {
        mailSender.sendMailPassword(user.getLogin());
        return "authorization";
    }
}
