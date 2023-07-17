package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateNewOrderTest{
    private User user;
    private ActionSteps actionSteps;
    private Credentials credentials;
    private String accessToken;

    @Before
    public void setUp(){
        user = UserMaker.random();
        actionSteps = new ActionSteps();
        credentials = new Credentials(user);
    }
    @Test
    @DisplayName("Успешное создание заказа")
    @Description("Проверка успешного создания заказа с ингредиентами с авторизацией")
    public void createOrder(){
        actionSteps.createNewUser(user);
        ValidatableResponse response = actionSteps.loginUser(credentials);
        accessToken = response.extract().path("accessToken").toString();
        actionSteps.createOrderOfAuthorizedUser(accessToken);
        ValidationUserSteps.createOrderPositive(response);
    }
    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка получения 401 ошибки при создании заказа без авторизации")
    public void createOrderWithoutAuthorization(){
        actionSteps.createOrderOfNonAuthorizedUser();
    }
    @Test
    @DisplayName("Создание заказа с неверным хэшем ингредиентов")
    @Description("Проверка получения 400 ошибки при создании заказа c неизвестными ингредиентами")
    public void createOrderWithIncorrectIngredients(){
        actionSteps.createNewUser(user);
        ValidatableResponse response = actionSteps.loginUser(credentials);
        accessToken = response.extract().path("accessToken").toString();
        actionSteps.createOrderWithIncorrectIngredients(accessToken);
    }
    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    @Description("Проверка получения 400 ошибки при создании заказа без ингредиентов")
    public void createOrderWithoutIngredients(){
        actionSteps.createNewUser(user);
        ValidatableResponse response = actionSteps.loginUser(credentials);
        accessToken = response.extract().path("accessToken").toString();
        actionSteps.createWithoutIngredients(accessToken);
    }
    @After
    public void cleanUp() {
        if (accessToken != null){
            actionSteps.deleteUser(accessToken);
        }
    }
}
