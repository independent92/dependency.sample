package dependency.sample.dto;

public class AccountInputDto {
    private final String email;
    private final String password;
    private final String firstName;
    private final String middleName;
    private final String lastName;


    public AccountInputDto(String email, String password, String firstName, String middleName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }
}
