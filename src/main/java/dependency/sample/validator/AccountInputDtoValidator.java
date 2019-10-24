package dependency.sample.validator;

import dependency.sample.dto.AccountInputDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountInputDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountInputDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountInputDto dto = (AccountInputDto) target;

        boolean passwordSpecified = true;

        if(StringUtils.isEmpty(dto.getPassword())) {
            errors.rejectValue("password", "accountInputDto.password.isBlank");
            passwordSpecified = false;
        }
        if (StringUtils.isEmpty(dto.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation", "accountInputDto.passwordConfirmation.isBlank");
            passwordSpecified = false;
        }
        if(passwordSpecified && !dto.getPassword().equals(dto.getPasswordConfirmation())) {
            errors.rejectValue("password", "accountInputDto.password.notMatch");
        }
    }
}
