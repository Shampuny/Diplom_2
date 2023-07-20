package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class ValidationUserSteps{
    @Step("Проверка успешного создания пользователя")
    public void userCreatePositive(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Попытка создания юзера без email, password или name")
    public void userCreateNegative(ValidatableResponse response){
        response
                .statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Попытка повторного создания существующего пользователя")
    public void createAnExistingUser(ValidatableResponse response){
        response
                .statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo("User already exists"));
    }

    @Step("Проверка ввода некорректных данных email или password")
    public void incorrectCredentials(ValidatableResponse response){
        response
                .statusCode(401)
                .and()
                .assertThat()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Попытка изменения неавторизованного юзера")
    public void updateNonAuthorizedUser(ValidatableResponse response){
        response
                .statusCode(401)
                .and()
                .assertThat()
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Попытка создания заказа без юзера")
    public static void createOrderWithoutUser(ValidatableResponse response){
        response
                .statusCode(401)
                .and()
                .assertThat()
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Проверка успешного создания заказа")
    public static void createOrderPositive(ValidatableResponse response){
        response
                .assertThat()
                .statusCode(200)
                .body("success", is(true));
    }
}

