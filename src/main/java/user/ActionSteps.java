package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import specification.RequestSpecifications;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class ActionSteps extends RequestSpecifications{
    private static final String CREATE_NEW_USER_ENDPOINT = "/api/auth/register";
    private static final String LOGIN_USER_ENDPOINT = "/api/auth/login";
    private static final String UPDATE_OR_DELETE_USER_ENDPOINT = "/api/auth/user";
    private static final String CREATE_ORDER_ENDPOINT = "/api/orders";
    private static final String GET_ORDER_ENDPOINT = "/api/orders";
    private String json;
    public ActionSteps(String json){
        this.json = json;
    }
    public ActionSteps(){}

    @Step("Регистрация нового пользователя")
    public ValidatableResponse createNewUser(User user){
        return given().log().all()
                .spec(getSpec())
                .body(user)
                .when()
                .post(CREATE_NEW_USER_ENDPOINT)
                .then()
                .log().all();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(Credentials credentials){
        return given()
                .log().all()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(LOGIN_USER_ENDPOINT)
                .then()
                .log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken){
        return given()
                .log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(UPDATE_OR_DELETE_USER_ENDPOINT)
                .then()
                .log().all();
    }

    @Step("Изменение пользователя")
    public ValidatableResponse updateUser(String accessToken, User user){
        return given()
                .log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(UPDATE_OR_DELETE_USER_ENDPOINT)
                .then()
                .log().all();
    }

    @Step("Получение информации о пользователе")
    public ValidatableResponse getUserInfo(String accessToken){
        return given()
                .log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(UPDATE_OR_DELETE_USER_ENDPOINT)
                .then()
                .log().all();
    }

    @Step("Создание заказа пользователя")
    public ValidatableResponse createOrderOfAuthorizedUser(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .body(json)
                .post(CREATE_ORDER_ENDPOINT)
                .then()
                .log().all();
    }

    @Step("Создание заказа c неправильным хэшем ингредиентов")
    public ValidatableResponse createOrderWithIncorrectIngredients(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .body(json)
                .post(CREATE_ORDER_ENDPOINT)
                .then()
                .log().all()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("One or more ids provided are incorrect"));
    }

    @Step("Создание заказа без ингридиентов")
    public ValidatableResponse createWithoutIngredients(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Создание заказа неавторизованого пользователя")
    public ValidatableResponse createOrderOfNonAuthorizedUser(){
        return given()
                .spec(getSpec())
                .when()
                .body(json)
                .post(CREATE_ORDER_ENDPOINT)
                .then()
                .log().all()
                .statusCode(401)
                .and()
                .assertThat()
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Получение заказа авторизованного пользователя")
    public ValidatableResponse getAuthorizedUserOrders(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(GET_ORDER_ENDPOINT)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Получение заказа неавторизованного пользователя")
    public ValidatableResponse getNotAuthorizedUserOrders(){
        return given()
                .spec(getSpec())
                .when()
                .get(GET_ORDER_ENDPOINT)
                .then()
                .log().all();
    }
}
