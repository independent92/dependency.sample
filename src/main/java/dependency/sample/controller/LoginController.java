package dependency.sample.controller;

import dependency.sample.dto.AccountInputDto;
import dependency.sample.service.AccountService;
import dependency.sample.validator.AccountInputDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final AccountService accountService;
    private final AccountInputDtoValidator accountInputDtoValidator;

    @Autowired
    public LoginController(AccountService accountService,
                           AccountInputDtoValidator accountInputDtoValidator) {
        this.accountService = accountService;
        this.accountInputDtoValidator = accountInputDtoValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountInputDtoValidator);
    }

    @GetMapping(value = "/registration")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("accountInputDto", new AccountInputDto());
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public String create(@Valid @ModelAttribute AccountInputDto dto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "registration";
        }
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
