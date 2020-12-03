package org.jugistanbul.hibernatesearch;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.jugistanbul.hibernatesearch.model.Event;
import org.jugistanbul.hibernatesearch.model.Host;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 3.12.2020
 **/
@QuarkusTest
public class SearchResourceIT
{
    @Test
    public void allHostsTest(){
        List<Host> hosts = RestAssured.when().get("/search/hosts")
                .then().statusCode(200)
                .extract().body().jsonPath().getList(".", Host.class);

        assertTrue(hosts.size() == 3);
    }

    @Test
    public void allEventsTest(){
        List<Event> events = RestAssured.when().get("/search/events")
                .then().statusCode(200)
                .extract().body().jsonPath().getList(".", Event.class);

        assertTrue(events.size() == 4);
    }

    @Test
    public void searchHostsByNameTest(){
        List<Host> hosts = RestAssured.given().pathParam("name", "huseyin")
                        .when().get("/search/host/name/{name}")
                        .then()
                        .statusCode(200)
                .extract().jsonPath().getList(".", Host.class);

        assertTrue(hosts.isEmpty());

        RestAssured.given().pathParam("name", "Huseyin")
                .when().get("/search/host/name/{name}")
                .then()
                .statusCode(200)
                .body("firstname", contains("Huseyin"),
                        "lastname", contains("Akdogan"));
    }

    @Test
    public void searchHostsByTitleTest(){
        RestAssured.given().pathParam("title", "consultation")
                .when().get("/search/host/title/{title}")
                .then()
                .statusCode(200)
                .body("firstname", contains("Huseyin"),
                        "lastname", contains("Akdogan"));
    }

    @Test
    public void searchEventsByNameTest(){
        List<Event> events = RestAssured.given().pathParam("name", "quarkus")
                .when().get("/search/event/{name}")
                .then().statusCode(200)
                .extract().jsonPath().getList(".", Event.class);

        assertFalse(events.isEmpty());
    }
}
