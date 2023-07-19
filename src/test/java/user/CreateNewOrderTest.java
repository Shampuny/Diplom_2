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
        credentials = new Credentials(user);
    }
    @Test
    @DisplayName("Успешное создание заказа")
    @Description("Проверка успешного создания заказа с ингредиентами с авторизацией")
    public void createOrder(){
        Ingredients ingredients = new Ingredients("61c0c6671d1f82001bdaaa6d");
        actionSteps = new ActionSteps(ingredients);
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
        Ingredients ingredients = new Ingredients("61c0c6671d1f82001bdaaa6d");
        actionSteps = new ActionSteps(ingredients);
        actionSteps.createOrderOfNonAuthorizedUser();
    }
    @Test
    @DisplayName("Создание заказа с неверным хэшем ингредиентов")
    @Description("Проверка получения 400 ошибки при создании заказа c неизвестными ингредиентами")
    public void createOrderWithIncorrectIngredients(){
        Ingredients ingredients = new Ingredients("61c0c6671d1f82001bdaaa6d");
        actionSteps = new ActionSteps(ingredients);
        actionSteps.createNewUser(user);
        ValidatableResponse response = actionSteps.loginUser(credentials);
        accessToken = response.extract().path("accessToken").toString();
        actionSteps.createOrderWithIncorrectIngredients(accessToken);
    }
    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    @Description("Проверка получения 400 ошибки при создании заказа без ингредиентов")
    public void createOrderWithoutIngredients(){
        actionSteps = new ActionSteps();
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
