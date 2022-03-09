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

import static io.restassured.RestAssured.given;

public class test {

       @Tag("1api")
    @Test
    @DisplayName("Характер Морти")
    public void morti() {

        Response response1 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/character/2")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        String resp1 = response1.getBody().asString();

    }

    @Tag("2api")
    @Test
    @DisplayName("Последний эпизод Морти")
    public void lastMortiEpisode() {

        Response response2 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/character/2")
                .then()
                .extract().response();


        String resp2 = response2.getBody().asString();
        JSONObject resp2json = new JSONObject(resp2);

        int jsonSize = (resp2json.getJSONArray("episode")).length();
        String lastEpisode = resp2json.getJSONArray("episode").getString(jsonSize -1);
        System.out.println("Сcылка на последнюю серию с Морти: " + lastEpisode);
    }
    @Tag("3api")
    @Test
    @DisplayName("Последний персонаж последнего эпизода")
    public void lastCharacter() {

        Response response3 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/episode/51")
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
        System.out.println("Имя последнего персонажа последнего эпизода: " + lastCharacterName);
        String lastCharacterrace = resp4json.getString("species").toString();
        System.out.println("Его раса: " + lastCharacterrace);
        String lastCharacterLocation = resp4json.getJSONObject("location").getString("name").toString();
        System.out.println("Его местнахождение: " + lastCharacterLocation);
    }
    @Tag("4api")
    @Test
    @DisplayName("Задание на углубление в ApI")
    public void createPostRequest() {
           String body = "{\"name\":\"potato\"}";
        Response response5 = given()
                .baseUri("https://reqres.in/")
                .contentType("application/json;charset=UTF-8")
                .log().all()
                .when()
                .body(body)
                .post("api/users")
                .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        body = "{\"name\":\"potato\", \"job\":\"Eat maket\"}";
        Response response6 = given()
                .baseUri("https://reqres.in/")
                .contentType("application/json;charset=UTF-8")
                .log().all()
                .when()
                .body(body)
                .post("api/users")
                .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        JSONObject resp5json = new JSONObject(response6);
        Assertions.assertEquals(resp5json.getString("name"),"Tomato");
        Assertions.assertEquals(resp5json.getString("job"),"Eat maket");
    }

}


















