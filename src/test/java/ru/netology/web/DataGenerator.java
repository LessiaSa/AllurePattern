
package ru.netology.web;

import com.github.javafaker.Faker;
import lombok.Value;
import java.util.Locale;


public class DataGenerator {

    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    public static String getRandomLogin() {
        return FAKER.name().username();
    }

    public static String getRandomPassword() {
        return FAKER.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            return new RegistrationDto(getRandomLogin(),
                    getRandomPassword(), status);
        }

        public static RegistrationDto getRegisteredUser(String status) {
            return ru.netology.web.ApiHelper.sendRequest(getUser(status));
        }
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}






