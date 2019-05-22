package by.akulich.tacocloud.domain;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private final String username;
    private final String password;
    private final String fullName;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(password);
        return new User(
                username, encodedPassword, fullName,
                street, city, state, zip, password);
    }
}
