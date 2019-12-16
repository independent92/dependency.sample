package dependency.sample.controller;

import dependency.sample.dto.AccountInputDto;
import dependency.sample.service.AccountService;
import dependency.sample.validator.AccountInputDtoValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final AccountService accountService;
    private final AccountInputDtoValidator accountInputDtoValidator;
    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public LoginController(AccountService accountService,
                           AccountInputDtoValidator accountInputDtoValidator,
                           JobLauncher jobLauncher,
                           Job job) {
        this.accountService = accountService;
        this.accountInputDtoValidator = accountInputDtoValidator;
        this.jobLauncher = jobLauncher;
        this.job = job;
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

    @GetMapping("/exception")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void test() throws JobParametersInvalidException,
                              JobExecutionAlreadyRunningException,
                              JobRestartException,
                              JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        jobLauncher.run(job, new JobParameters(map));
    }
}
