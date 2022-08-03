package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static helpers.CustomApiListener.withCustomTemplates;

public class DemoWebShopTests extends TestBase {


    @Test
    @Tag("TestDemoWebShop")
    @DisplayName("UI. Registration user")
    void registrationTest() {
        regPage.openPage()
                .setGenderMale()
                .setFirstName(dataForTests.firstName)
                .setLastName(dataForTests.lastName)
                .setMail(dataForTests.newMail)
                .setPassword(dataForTests.password)
                .setConfirmPassword(dataForTests.password)
                .clickRegister()
                .checkResult();
    }


    @Test
    @Tag("TestDemoWebShop")
    @DisplayName("API+UI. User authorization")
    void authTest() {
        String cookieKey = "NOPCOMMERCE.AUTH";
        String cookieValue = given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .log().all()
                .formParam("Email", dataForTests.email)
                .formParam("Password", dataForTests.password)
                .when()
                .post("http://demowebshop.tricentis.com/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(cookieKey);

        open("/login");
        Cookie authCookie = new Cookie(cookieKey, cookieValue);
        getWebDriver().manage().addCookie(authCookie);
        open("");
        $(".account").shouldHave(text(dataForTests.email));
    }

    @Test
    @Tag("TestDemoWebShop")
    @DisplayName("API. Add an item to the cart")
    void addToCartTest() {
        String cookie = "Nop.customer=1ee03efd-225f-4da1-b3c5-613a16c29944";
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(cookie)
                .body("addtocart_31.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/31/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("updatetopcartsectionhtml", is("(1)"));
    }

    @Test
    @Tag("TestDemoWebShop")
    @DisplayName("API+UI. Update info user")
    void updateTest() {
        String cookieKey = "NOPCOMMERCE.AUTH";
        String cookieValue = given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .log().all()
                .formParam("Email", dataForTests.email)
                .formParam("Password", dataForTests.password)
                .when()
                .post("http://demowebshop.tricentis.com/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(cookieKey);
        open("");
        Cookie authCookie = new Cookie(cookieKey, cookieValue);
        getWebDriver().manage().addCookie(authCookie);
        open("/customer/info");
        $("#FirstName").setValue("TestName");
        $("#LastName").setValue("TestFamily");
        $("[for='gender-female']").click();
        $("[name='save-info-button']").click();
        $("#FirstName").shouldHave(Condition.value("TestName"));
    }
}