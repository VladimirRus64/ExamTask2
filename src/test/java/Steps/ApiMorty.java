package Steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

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
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .get("/api/character/2")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();
        mortiname = new JSONObject(response1.getBody().asString()).get("name").toString();
        mortilocation = new JSONObject(response1.getBody().asString()).getJSONObject("location").get("name").toString();
        mortirace = new JSONObject(response1.getBody().asString()).get("species").toString();
        System.out.println("");
        System.out.println("1й обьект сравнения: " + mortiname + ", " + mortirace + " с планеты " + mortilocation);
    }

    @Step("Получаем информацию о последнем эпизоде с Морти")
    public void lastEpisode() {
        Response response2 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .get("/api/character/2")
                .then()
                .extract().response();

        int jsonSize1 = new JSONObject(response2.asString()).getJSONArray("episode").length();
        lastEpisode = Integer.parseInt(new JSONObject(response2.getBody().asString()).getJSONArray("episode").get(jsonSize1 - 1).toString().replaceAll("[^0-9]", ""));
    }

    @Step("Получаем индекс последнего персонажа")
    public void getLastCharacterNum() {
        Response response3 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .get("api/episode/" + lastEpisode)
                .then()
                .extract().response();

        int lastCharacterIndex = (new JSONObject(response3.getBody().asString()).getJSONArray("characters").length() - 1);
        lastCharacterNum = Integer.parseInt(new JSONObject(response3.getBody().asString()).getJSONArray("characters").get(lastCharacterIndex).toString().replaceAll("[^0-9]", ""));
    }

    @Step("Получаем информацию о последнем персонаже")
    public void getLastCharacterInfo() {
        Response response4 = given()
                .baseUri("https://rickandmortyapi.com")
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .get("api/character/" + lastCharacterNum)
                .then()
                .extract().response();
        lastCharacterName = new JSONObject(response4.getBody().asString()).get("name").toString();
        lastCharacterLocation = new JSONObject(response4.getBody().asString()).getJSONObject("location").get("name").toString();
        lastCharacterrace = new JSONObject(response4.getBody().asString()).get("species").toString();
        System.out.println("2й обьект сравнения: " + lastCharacterName + ", " + lastCharacterrace + " с планеты " + lastCharacterLocation);
       }

    @Step("Сравниваем расу и местоположение персонажей")
    public void assertions() {
        Assertions.assertEquals(mortirace, lastCharacterrace);
        if (mortilocation.equals(lastCharacterLocation)) {
            System.out.println(mortiname + lastCharacterName + "с одной планеты");
        } else
            System.out.println("Если вы читаете данное сообщение, значить " + mortiname + " и " + lastCharacterName + " одной расы, но c разных планет =(");
    }

}
