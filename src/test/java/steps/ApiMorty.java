package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import static settings.Configuration.getFromProperties;
import static io.restassured.RestAssured.given;

public class ApiMorty {
    public int lastEpisode;
    public String mortiname;
    public String mortilocation;
    public String mortirace;
    public String lastCharacterName;
    public String lastCharacterrace;
    public int lastCharacterNum;
    public String lastCharacterLocation;

    @Step("Получаем информацию о Морти")
    public void mortyInformation() {
        Response response1 = given()
                .baseUri(getFromProperties("url"))
                .contentType(ContentType.JSON)
                .when()
                .get("/character/2")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();
        mortiname = new JSONObject(response1.getBody().asString()).get("name").toString();
        mortilocation = new JSONObject(response1.getBody().asString()).getJSONObject("location").get("name").toString();
        mortirace = new JSONObject(response1.getBody().asString()).get("species").toString();
    }

    @Step("Получаем информацию о последнем эпизоде с Морти")
    public void lastEpisode() {
        Response response2 = given()
                .baseUri(getFromProperties("url"))
                .contentType(ContentType.JSON)
                .when()
                .get("/character/2")
                .then()
                .extract().response();

        int jsonSize1 = new JSONObject(response2.asString()).getJSONArray("episode").length();
        lastEpisode = Integer.parseInt(new JSONObject(response2.getBody().asString()).getJSONArray("episode").get(jsonSize1 - 1).toString().replaceAll("[^0-9]", ""));
    }

    @Step("Получаем индекс последнего персонажа")
    public void getLastCharacterNum() {
        Response response3 = given()
                .baseUri(getFromProperties("url"))
                .contentType(ContentType.JSON)
                .when()
                .get("/episode/" + lastEpisode)
                .then()
                .extract().response();

        int lastCharacterIndex = (new JSONObject(response3.getBody().asString()).getJSONArray("characters").length() - 1);
        lastCharacterNum = Integer.parseInt(new JSONObject(response3.getBody().asString()).getJSONArray("characters").get(lastCharacterIndex).toString().replaceAll("[^0-9]", ""));
    }

    @Step("Получаем информацию о последнем персонаже")
    public void getLastCharacterInfo() {
        Response response4 = given()
                .baseUri(getFromProperties("url"))
                .contentType(ContentType.JSON)
                .when()
                .get("/character/" + lastCharacterNum)
                .then()
                .extract().response();
        lastCharacterName = new JSONObject(response4.getBody().asString()).get("name").toString();
        lastCharacterLocation = new JSONObject(response4.getBody().asString()).getJSONObject("location").get("name").toString();
        lastCharacterrace = new JSONObject(response4.getBody().asString()).get("species").toString();
    }

    @Step("Сравниваем расу персонажей")
    public void assertionsRace() {
        Assertions.assertEquals(mortirace, lastCharacterrace);
    }

    @Step("Сравниваем местоположение персонажей")
    public void assertionsLoc() {
        Assertions.assertNotEquals(mortilocation, lastCharacterLocation);
    }

}
