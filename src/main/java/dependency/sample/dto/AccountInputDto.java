package dependency.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInputDto {
    @Email(message = "{accountInputDto.email.notValid}")
    @NotBlank(message = "{accountInputDto.email.isBlank}")
    private String email;
    private String password;
    private String passwordConfirmation;
    @NotBlank(message = "{accountInputDto.firstName.isBlank}")
    private String firstName;
    private String middleName;
    @NotBlank(message = "{accountInputDto.lastName.isBlank}")
    private String lastName;
}
