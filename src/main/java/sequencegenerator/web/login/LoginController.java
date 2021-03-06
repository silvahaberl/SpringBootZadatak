package sequencegenerator.web.login;

import org.hibernate.mapping.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sequencegenerator.model.User;
import sequencegenerator.service.PasswordService;
import sequencegenerator.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by silva on 20.09.16..
 */
@Controller
@RequestMapping("login")
public class LoginController {

    private static final String LOGIN_VIEW = "login";

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public String showForm(Model model) {
        logger.info("showForm");

        model.addAttribute("loginInfo", new LoginDto());


        return LOGIN_VIEW;
    }


    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute("loginInfo")LoginDto loginInfo, Model model,RedirectAttributes redirectAttributes) {


        logger.info("Login submit -> loginInfo: '" + loginInfo.getUsername() + "' password: '" + loginInfo.getPassword() + "'");
        //logger.info("hasshed password: " + PasswordService.getPasswordHash(loginInfo.getPassword()));


        User u = new User();
        u.setId(1);
        u.setUsername("silva");
        u.setPassword("1234");
        service.save(u);

        //logger.info(" u: " + u.getId() + " username: " + u.getUsername() + " password: " + u.getPassword());
        //logger.info( "service.save(u): " + service.save(u) );
        logger.info("service.isValid::  " + service.isValid(u.getUsername(), u.getPassword()));

        if(service.isValid(loginInfo.getUsername(), loginInfo.getPassword())) {

            redirectAttributes.addFlashAttribute("loginInfo", loginInfo);

            return "redirect:sequence/list";
        }

        model.addAttribute("isWrongPassword", true);


        return showForm(model);
    }


}
