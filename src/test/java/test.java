import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class test {
    @Step("Получение JSONа, поиск по нему информации о 2х персонажах их сравнение")
    @Tag("1api")
    @Test
    @DisplayName("Сравнение 2х героев")
    public void morti() {

        Response response1 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .get("/api/character/2")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        String resp1 = response1.getBody().asString();
        JSONObject resp1json = new JSONObject(resp1);
        String mortiname = resp1json.getString("name");
        String mortirace = resp1json.getString("species");
        String mortilocation = resp1json.getJSONObject("location").getString("name");
        System.out.println("");
        System.out.println("1й обьект сравнения: " + mortiname + ", " + mortirace + " с планеты " + mortilocation );

        int jsonSize1 = (resp1json.getJSONArray("episode")).length();
        String lastEpisode = resp1json.getJSONArray("episode").getString(jsonSize1 -1);
        System.out.println("Сcылка на последнюю серию с Морти: " + lastEpisode);

        Response response3 = given()
                .get(lastEpisode)
                .then()
                .extract().response();

        String resp3 = response3.getBody().asString();
        JSONObject resp3json = new JSONObject(resp3);

        int jsonSize = (resp3json.getJSONArray("characters")).length();
        String lastCharacterNum = resp3json.getJSONArray("characters").getString(jsonSize - 1);

        Response response4 = given()
                .get(lastCharacterNum)
                .then()
                .extract().response();
        String resp4 = response4.getBody().asString();
        JSONObject resp4json = new JSONObject(resp4);

        String lastCharacterName = resp4json.get("name").toString();
        String lastCharacterrace = resp4json.getString("species").toString();
        String lastCharacterLocation = resp4json.getJSONObject("location").getString("name").toString();
        System.out.println("");
        System.out.println("2й обьект сравнения: " + lastCharacterName + ", " + lastCharacterrace + " с планеты "+ lastCharacterLocation );
        System.out.println("");
        Assertions.assertEquals(mortirace,lastCharacterrace);
        if (mortilocation.equals(lastCharacterLocation)) {
            System.out.println(mortiname + lastCharacterName + "с одной планеты");
        }else
            System.out.println("Если вы читаете данное сообщение, значить " + mortiname + " и " + lastCharacterName + " одной расы, но c разных планет =(");

    }
    @Step("Отправка post-запроса, получение и проверка ответа")
    //@Tag("2api")
    @Test
    @DisplayName("Задание на углубление в ApI")
    public void createPostRequest() throws IOException {

        JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/.json"))));

        jsonObject.put("name", "Tomato");
        jsonObject.put("job", "Eat maket");

           Response response5 = given()
                   .baseUri("https://reqres.in/")
                   .contentType("application/json;charset=UTF-8")
                  // .log().all()
                   .when()
                   .body(jsonObject.toString())
                   .post("api/users")
                   .then()
                   .statusCode(201)
                  // .log().all()
                   .extract().response();


        String resp5 = response5.getBody().asString();
        JSONObject resp5json = new JSONObject(resp5);
        Assertions.assertEquals(resp5json.getString("name"),"Tomato");
        Assertions.assertEquals(resp5json.getString("job"),"Eat maket");
    }

}


















