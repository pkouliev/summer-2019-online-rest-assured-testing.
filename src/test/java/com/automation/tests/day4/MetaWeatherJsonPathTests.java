package com.automation.tests.day4;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class MetaWeatherJsonPathTests {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("meta.weather.uri");
    }

    /**
     * TASK
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is 'New'
     * Then user verifies that payload contains 5 objects
     */

    @Test
    @DisplayName("Verify that payload contain five objects")
    public void test1(){
        given().
                accept(ContentType.JSON).
                queryParam("query","New").
        when().
                get("/search").
        then().
                assertThat().
                statusCode(200).
                body("", hasSize(5)).
                log().body(true);

    }    /**

     *TASK
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is New
     * Then user verifies that 1st object has following info:
     *  |title   |location_type|woeid  |latt_long          |
     *  |New York|City         |2459115|40.71455,-74.007118|
     */

    @Test
    @DisplayName("Verify that payload contain has following info New York, City, 2459115, 40.71455,-74.007118")
    public void test2() {
        given().
                accept(ContentType.JSON).
                queryParam("query", "New").
        when().
                get("/search").
        then().
                assertThat().
                statusCode(200).
                body("title[0]", is("New York")).
                body("location_type[0]", is("City")).
                body("woeid[0]", is(2459115)).
                body("latt_long[0]", is("40.71455,-74.007118")).
                log().body(true);
    }

    @Test
    @DisplayName("Verify that payload contain has following info New York, City, 2459115, 40.71455,-74.007118")
    public void test2_2() {

        Map<String, String> expected = new HashMap<>();
        expected.put("title", "New York");
        expected.put("location_type", "City");
        expected.put("woeid", "2459115");
        expected.put("latt_long", "40.71455,-74.007118");

        Response response = given().
                                accept(ContentType.JSON).
                                queryParam("query", "New").
                            when().
                                get("/search");
        JsonPath jsonPath = response.jsonPath();

        assertEquals(expected, jsonPath.getMap("[0]", String.class, String.class));


        List<Map<String, ?>> values = jsonPath.get();

        System.out.println(values);
    }

    /**
     *TASK
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is 'Las'
     * Then user verifies that payload  contains following titles:
     *  |Glasgow  |
     *  |Dallas   |
     *  |Las Vegas|
     *
     */

    @Test
    @DisplayName("Verify that payload contains following title ")
    public void test3(){
        given().
                accept(ContentType.JSON).
                queryParam("query", "Las").
        when().
                get("/search").
        then().assertThat().body("title", hasItems("Glasgow", "Dallas", "Las Vegas"));

    }
    /**
     *TASK
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is 'Las'
     * Then verify that every item in payload has location_type City
     *
     */

    @Test
    @DisplayName("verify that every item in payload has location_type city")
    public void test4(){
        given().
                accept(ContentType.JSON).
                queryParam("query","Las").
        when().
                get("/search").
        then().assertThat().body( "location_type", hasItem( "City")).
        log().all(true);
    }

    /**
     *TASK
     * Given accept type is JSON
     * When users sends a GET request to "/location"
     * And path parameter is '44418'
     * Then verify following that payload contains weather forecast sources
     * |BBC                 |
     * |Forecast.io         |
     * |HAMweather          |
     * |Met Office          |
     * |OpenWeatherMap      |
     * |Weather Underground |
     * |World Weather Online|
     *
     *
     */
    @Test
    @DisplayName("verify that payload contains weather forecast sources ")
    public void test5(){
        given().
                accept(ContentType.JSON).
                pathParam( "id", "44418").
        when().
                get("/location/{id}").
                then().assertThat().body("sources.title", hasItems("BBC","Forecast.io","Met Office", "OpenWeatherMap",
                                                                         "Weather Underground", "World Weather Online"));

    }

    @Test
    @DisplayName("Verify following that payload contains weather forecast sources")
    public void test5_5() {
        List<String> expected = Arrays.asList("BBC", "Forecast.io", "HAMweather", "Met Office",
                "OpenWeatherMap", "Weather Underground",
                "World Weather Online");
        Response response = given().
                accept(ContentType.JSON).
                pathParam("woeid", 44418).
                when().
                get("/location/{woeid}");

        List<String> actual = response.jsonPath().getList("sources.title");

        assertEquals(expected, actual);
    }

}
