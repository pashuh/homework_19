package pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegPage {

    public RegPage openPage() {
        open("/register");
        return this;
    }
    
    public RegPage setGenderMale() {
        $("[for='gender-male']").click();
        return this;
    }

    public RegPage setFirstName(String value) {
        $("#FirstName").setValue(value);
        return this;
    }

    public RegPage setLastName(String value) {
        $("#LastName").setValue(value);
        return this;
    }

    public RegPage setMail(String value) {
        $("#Email").setValue(value);
        return this;
    }
    public RegPage setPassword(String value) {
        $("#Password").setValue(value);
        return this;
    }

    public RegPage setConfirmPassword(String value) {
        $("#ConfirmPassword").setValue(value);
        return this;
    }

    public RegPage clickRegister() {
        $("#register-button").click();
        return this;
    }

    public RegPage checkResult() {
        $(".page.registration-result-page").shouldHave(Condition.text("Your registration completed"));
        return this;
    }

}
