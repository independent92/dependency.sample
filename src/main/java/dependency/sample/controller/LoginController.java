package dependency.sample.controller;

import dependency.sample.dto.AccountInputDto;
import dependency.sample.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final AccountService accountService;

    @Autowired
    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/registration")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("registration");
    }

    @PostMapping(value = "/registration")
    public String create(@ModelAttribute AccountInputDto dto) {
        accountService.save(dto);
        return "redirect:/homepage";
    }

    @GetMapping(value = "/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping(value = "/homepage")
    public ModelAndView showHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("accountName", accountService.findLoggedInUsername());
        modelAndView.setViewName("homepage");
        return modelAndView;
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
