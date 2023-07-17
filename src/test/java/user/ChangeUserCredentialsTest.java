package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserCredentialsTest{
    private User user;
    private ActionSteps actionSteps;
    private ValidationUserSteps validationUserSteps;


    @Before
    public void setUp(){
        user = UserMaker.random();
        actionSteps = new ActionSteps();
        validationUserSteps = new ValidationUserSteps();
    }

    @Test
    @DisplayName("Изменение данных зарегистрированого пользователя")
    @Description("Проверка успешного изменения данных зарегистрированного пользователя")
    public void checkUpdateUser() {
        User userUpdate = user.clone();
        userUpdate.setEmail(RandomStringUtils.randomAlphabetic(10) + "@test1.ru");
        String accessToken = actionSteps.createNewUser(user).extract().header("Authorization");
        actionSteps.updateUser(accessToken, userUpdate);
        ValidatableResponse updatedUserResponse = actionSteps.getUserInfo(accessToken);
        updatedUserResponse.body("user.name", equalTo(user.getName())).and().body("user.email", equalTo(userUpdate.getEmail().toLowerCase()));
    }

    @Test
    @DisplayName("Изменения данных незарегистрированого пользователя")
    @Description("Проверка ответа с кодом 401 при изменении данных незарегистрированого пользователя")
    public void checkUpdateNonAuthorizedUser() {
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@example.com");
        ValidatableResponse response = actionSteps.updateUser("", user);
        validationUserSteps.updateNonAuthorizedUser(response);
    }

}
