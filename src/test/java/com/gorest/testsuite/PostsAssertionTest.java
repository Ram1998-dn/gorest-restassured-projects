package com.gorest.testsuite;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostsAssertionTest {

    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "public/v2/posts";

        response = given().log().all()
                .queryParam("page", "1")
                .queryParam("per_page", "25")
                .get()
                .then().statusCode(200);
    }

    // 1.  Verify the if the total record is 25
    @Test
    public void test001() {
        response.body("size()", equalTo(25));
    }

    //2. Verify the if the title of id = 184654 is equal to ”Defendo cometes vigor argumentum mollitia excepturi."
    @Test
    public void test002() {
        response.body("findAll{it.id == 184654 }.title[0]", equalTo("Defendo cometes vigor argumentum mollitia excepturi."));
    }


    //3. Check the single user_id in the Array list (7609167)
    @Test
    public void test003() {
        response.body("user_id", hasItem(7609167)).log();
    }

    //4. Check the multiple ids in the ArrayList ( 184647, 184645, 184637)
    @Test
    public void test004() {
        response.body("id", hasItems(184647, 184645, 184637));
    }

    //5. Verify the body of userid = 7609153 is equal “Nemo vindico suus. Venustas vel demum. Tot atqui sono. Aqua denuncio animadverto. Arx desparatus ut. Triumphus brevis optio. Solium vicissitudo conspergo. Thalassinus adicio adfectus. Quasi campana degero. Amoveo bene stella. Attero color ustilo. Eius altus brevis. Atrox ater absum. Bis et cupiditate.”
    @Test
    public void test005() {
        response.body("findAll{it.user_id == 7609153}.body", equalTo(Collections.singletonList("Nemo vindico suus. Venustas vel demum. Tot atqui sono. Aqua denuncio animadverto. Arx desparatus ut. Triumphus brevis optio. Solium vicissitudo conspergo. Thalassinus adicio adfectus. Quasi campana degero. Amoveo bene stella. Attero color ustilo. Eius altus brevis. Atrox ater absum. Bis et cupiditate.")));
    }

}
