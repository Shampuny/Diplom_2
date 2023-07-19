package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetOrderTest{
    private User user;
    private ActionSteps actionSteps;
    private String accessToken;
    private Credentials credentials;

    @Before
    public void setUp(){
        user = UserMaker.random();
        credentials = new Credentials(user);
    }
    @Test
    @DisplayName("Получение списка заказов пользователя")
    @Description("Проверка успешного получения заказа авторизованного пользователя")
    public void getOrderPositiveTest(){
        Ingredients ingredients = new Ingredients("61c0c6671d1f82001bdaaa6d");
        actionSteps = new ActionSteps(ingredients);
        actionSteps.createNewUser(user);
        ValidatableResponse response = actionSteps.loginUser(credentials);
        accessToken = response.extract().path("accessToken").toString();
        actionSteps.createOrderOfAuthorizedUser(accessToken);
        actionSteps.getAuthorizedUserOrders(accessToken);
    }
    @Test
    @DisplayName("Получение списка заказов без регистрации")
    @Description("Проверка получения ошибки 401 при получении заказа неавторизованного пользователя")
    public void getOrderWithoutUserTest(){
        actionSteps = new ActionSteps();
        ValidatableResponse response = actionSteps.getNotAuthorizedUserOrders();
        ValidationUserSteps.createOrderWithoutUser(response);
    }
    @After
    public void cleanUp(){
        if (accessToken != null){
            actionSteps.deleteUser(accessToken);
        }
    }
}