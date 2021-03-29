package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class LoginRequest {


        public LoginRequest() {
        }

        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static void createUser(LoginInfo user) {
            // сам запрос
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(new Gson().toJson(user))
                    .when() // "когда"
                    .post("/api/system/users")
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }

        public static LoginInfo account(String status) {
            Faker faker = new Faker();
            LoginInfo user = new LoginInfo(
                    faker.name().username(),
                    faker.internet().password(),
                    status
            );
            createUser(user);
            return user;
        }
    }

}