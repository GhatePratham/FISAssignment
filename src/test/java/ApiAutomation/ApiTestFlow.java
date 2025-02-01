package ApiAutomation;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; 
public class ApiTestFlow {

	@Test
	public void TC_ApiFlowTest() {
		
		//Step 1 :Send the GET request
		RestAssured.baseURI = "https://api.coindesk.com";
		
      //Fetch Response
		String Response = given().log().all().
				when().get("/v1/bpi/currentprice.json").
				then().log().all().
				assertThat().statusCode(200).extract().response().asString();
		
		//Parse Response with JsonPath
		JsonPath js = new JsonPath(Response);
		
		//Step 2 : Verify the response contains USD, GBP, EUR
		int bpiCount = js.getInt("bpi.size()");
		System.out.println("Total Number of bpi Components are : "+bpiCount);
		
        Assert.assertTrue(js.getMap("bpi").containsKey("USD"), "USD not found in Bpi");
        Assert.assertTrue(js.getMap("bpi").containsKey("GBP"), "GBP not found in Bpi");
        Assert.assertTrue(js.getMap("bpi").containsKey("EUR"), "EUR not found in Bpi");

        // Step 3: Verify The GBP ‘description’ equals ‘British Pound Sterling’.
        String ExpgbpDescrption = "British Pound Sterling";
        String ActgbpDescription = js.getString("bpi.GBP.description");
        Assert.assertEquals(ActgbpDescription, ExpgbpDescrption, "GBP description does not match");
		
	}
}
