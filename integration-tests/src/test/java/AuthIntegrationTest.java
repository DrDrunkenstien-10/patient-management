import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthIntegrationTest {

  @BeforeAll
  static void setUp() {
    RestAssured.baseURI = "http://localhost:4004";
  }

  @Test
  public void shouldReturnOKWithValidToken() {
    String loginPayload = """
          {
            "email": "testuser@test.com",
            "password": "password123"
          }
        """;

    Response response = given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .body("token", notNullValue())
        .extract()
        .response();

    System.out.println("Generated Token: " + response.jsonPath().getString("token"));
  }

  @Test
  public void shouldReturnUnauthorizedOnInvalidLogin() {
    String loginPayload = """
          {
            "email": "invaliduser@test.com",
            "password": "wrongpassword"
          }
        """;

    given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(401);
  }
}

// Structure of test:
// 1. Arrange: Set up the test environment, including any necessary data or
// configurations.
// 2. Act: Execute the code being tested, such as making an API call.
// 3. Assert: Verify the results of the action, ensuring that the expected
// outcomes are met.
// The test should be structured to follow the Arrange-Act-Assert pattern, which
// is a common practice in unit testing.
// The test should be written in a way that is easy to read and understand, with
// clear naming conventions and comments where necessary.
// The test should be self-contained, meaning that it should not rely on any
// external factors or dependencies.