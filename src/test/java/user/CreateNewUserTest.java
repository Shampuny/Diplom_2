package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateNewUserTest{
    private User user;
    private ActionSteps actionSteps;
    private ValidationUserSteps validationUserSteps;
    private String accessToken;

    @Before
    public void setUp(){
        user = UserMaker.random();
        actionSteps = new ActionSteps();
        validationUserSteps = new ValidationUserSteps();
    }
    @Test
    @DisplayName("Создание пользователя")
    @Description("Создание пользователя и проверка успешного выполнения операции")
    public void createUserPositive(){
        ValidatableResponse response = actionSteps.createNewUser(user);
        accessToken = response.extract().path("accessToken").toString();
        validationUserSteps.userCreatePositive(response);
    }
    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проверка получения ошибки с кодом 403 при создании юзера без имени")
    public void createUserWithoutName(){
        user.setName("");
        ValidatableResponse response = actionSteps.createNewUser(user);
        validationUserSteps.userCreateNegative(response);
    }
    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Проверка получения ошибки с кодом 403 при создании юзера без email")
    public void createUserWithoutEmail(){
        user.setEmail("");
        ValidatableResponse response = actionSteps.createNewUser(user);
        validationUserSteps.userCreateNegative(response);
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверка получения ошибки с кодом 403 при создании юзера без пароля")
    public void createUserWithoutPassword(){
        user.setPassword("");
        ValidatableResponse response = actionSteps.createNewUser(user);
        validationUserSteps.userCreateNegative(response);
    }
    @Test
    @DisplayName("Создание существующего пользователя")
    @Description("Проверка получения ошибки с кодом 403 при попытке создания существующего пользователя")
    public void createAnExistUser(){
        actionSteps.createNewUser(user);
        ValidatableResponse response = actionSteps.createNewUser(user);
        validationUserSteps.createAnExistingUser(response);
    }

    @After
    public void cleanUp(){
        if (accessToken != null){
            actionSteps.deleteUser(accessToken);
        }
    }
}
