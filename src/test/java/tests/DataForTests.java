package tests;

import com.github.javafaker.Faker;
import credentials.CredentialsConfig;
import org.aeonbits.owner.ConfigFactory;

public class DataForTests {
    CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    String password = config.password();
    String email = config.email();

    Faker faker = new Faker();
    String firstName = faker.funnyName().name(),
            lastName = faker.name().lastName(),
            newMail = faker.internet().safeEmailAddress();
}