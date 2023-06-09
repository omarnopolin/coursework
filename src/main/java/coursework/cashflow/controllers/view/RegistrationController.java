package coursework.cashflow.controllers.view;

import coursework.cashflow.repositories.UserRepo;
import coursework.cashflow.utils.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import coursework.cashflow.models.Role;
import coursework.cashflow.models.dao.Users;
import coursework.cashflow.utils.MailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Controller
@RequestMapping("/sign_up")
@RequiredArgsConstructor
public class RegistrationController {
    private final Token token;
    private final MailSender mailSender;
    private final UserRepo userRepo;
    @PostMapping
    public void postSignUp(@RequestBody Users user, HttpServletResponse response) throws IOException {
        if (userRepo.findByLogin(user.getLogin()) == null) {
            user.setRoles(Collections.singleton(Role.USER));
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            user.setToken(token.getJWTToken(user.getLogin()));
            user.setValidationStatus(0);
            System.out.println(user);
            userRepo.save(user);
            mailSender.sendMailConfirmation(user);
        }
        response.encodeRedirectURL("authorization");
    }
    @GetMapping
    public String getSignUp(HttpServletRequest request) {
        return "registration";
    }
    @GetMapping("confirmation")
    public RedirectView confirmation(@RequestParam("token") String token){
        RedirectView redirectView = null;
        Users user = userRepo.findByToken(token).get(0);
        if (user != null) {
            user.setValidationStatus(1);
            userRepo.save(user);
            redirectView = new RedirectView("/login");
            return redirectView;
        }
        redirectView = new RedirectView("error");
        return redirectView;
    }
} 

