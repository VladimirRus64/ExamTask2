package Hooks;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WebHooks implements BeforeAllCallback {

    private static boolean started = false;

    @BeforeAll
    public void beforeAll(ExtensionContext extensionContext) {
        if (!started) {
            started = true;
            RestAssured.filters(new AllureRestAssured());
        }

    }
}


