package com.gorest.testsuite;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class UserAssertionTest {

    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "public/v2/users";

        response = given().log().all()
                .queryParam("page", "1")
                .queryParam("per_page", "20")
                .get()
                .then().statusCode(200);
    }


    // 1. Verify the if the total record is 20
    @Test
    public void test01() {
        response.body("size()", equalTo(20));
    }

    //2. Verify the if the name of id = 7609137 is equal to ”Dr. Vedanga Bharadwaj”
    @Test
    public void test02() {
        response.body("[9].name", equalTo("Dr. Vedanga Bharadwaj"));
    }

    //3. Check the single ‘Name’ in the Array list (Menka Talwar III)
    @Test
    public void test03() {
        response.body("[15].name", equalTo(Collections.singletonList("Menka Talwar III").get(0)));
    }

    //4. Check the multiple ‘Names’ in the ArrayList (Usha Kaul Esq., Akshita Mishra, Chetanaanand Reddy )
    @Test
    public void test04() {
        response.body("name", hasItems("Charumati Sinha DO", "Priyala Chopra", "Aashritha Mishra"));
    }

    //5. Verify the email of userid = 7609130 is equal “do_sinha_charumati@hauck.test”
    @Test
    public void test05() {
        response.body("[16].email", equalTo("do_sinha_charumati@hauck.test"));
    }

    //6. Verify the status is “Active” of user name is “Adhiraj Pothuvaal DC”

    @Test
    public void test06() {
        response.body("[7].status", equalTo("inactive"));
    }
//7. Verify the Gender = male of user name is “Dhara Guha”
@Test
public void test07() {
    response.body("[4].gender", equalTo("female"));
}

}
