package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthorizationUserTest{
    private User user;
    private Credentials credentials;
    private ActionSteps actionSteps;
    private ValidationUserSteps validationUserSteps;
    private String accessToken;

    @Before
    public void setUser(){
        user = UserMaker.random();
        actionSteps = new ActionSteps();
        validationUserSteps = new ValidationUserSteps();
        credentials = new Credentials(user);
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка успешной авторизации пользователя")
    public void authorizationUser(){
        actionSteps.createNewUser(user);
        ValidatableResponse response = actionSteps.loginUser(credentials);
        accessToken = response.extract().path("accessToken").toString();
        validationUserSteps.userCreatePositive(response);
    }

    @Test
    @DisplayName("Авторизация пользователя без логина")
    @Description("Проверка ответа с кодом 401 при авторизации пользователя без логина")
    public void authorizationUserWithoutEmail(){
        user.setEmail("");
        ValidatableResponse response = actionSteps.loginUser(credentials);
        validationUserSteps.incorrectCredentials(response);
    }

    @Test
    @DisplayName("Авторизация пользователя без пароля")
    @Description("Проверка ответа с кодом 401 при авторизации пользователя без пароля")
    public void authorizationUserWithoutPassword(){
        user.setPassword("");
        ValidatableResponse response = actionSteps.loginUser(credentials);
        validationUserSteps.incorrectCredentials(response);
    }

    @After
    public void cleanUp(){
        if (accessToken != null){
            actionSteps.deleteUser(accessToken);
        }
    }
}
