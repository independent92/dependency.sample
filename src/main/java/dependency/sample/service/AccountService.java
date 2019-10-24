package dependency.sample.service;

import dependency.sample.dto.AccountInputDto;
import dependency.sample.entity.Account;
import dependency.sample.repository.AccountRepository;
import dependency.sample.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class AccountService extends AbstractUserDetailsAuthenticationProvider implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Account save(AccountInputDto dto) {
        Account newAccount = new Account();
        newAccount.setEmail(dto.getEmail());
        newAccount.setPassword(passwordEncoder.encode(dto.getPassword()));
        newAccount.setEnabled(true);
        newAccount.setRoles(Collections.singletonList(roleRepository.getOne(1L)));
        newAccount.setFirstName(dto.getFirstName());
        newAccount.setMiddleName(dto.getMiddleName());
        newAccount.setLastName(dto.getLastName());

        Account persistent = accountRepository.save(newAccount);
        autoLogin(dto.getEmail(), dto.getPassword());

        return persistent;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findAccountByEmail(email);
    }

    private void autoLogin(String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticate(token);

        if(token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }

    public String findLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //NOP
    }

    /**
     * параметр name - возвращаемое значение в реализации метода интерфейса UserDetails#getUserName()
     */
    @Override
    protected UserDetails retrieveUser(String name, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return loadUserByUsername(name);
    }

    /**
     * Таким образом можно обернуть в транзакцию метод супер-класса,
     * на который не действует @Transactional
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return super.authenticate(authentication);
    }
}
