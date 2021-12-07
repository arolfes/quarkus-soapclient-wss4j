package info.novatec.examples.soapclient.quarkus

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest {

    @Test
    @Disabled("TODO change to wiremock or mock soapclient")
    fun testSayHiEndpoint() {
        given()
          .`when`().get("/hello/sayhi")
          .then()
             .statusCode(200)
             .body(`is`("""{"response":"He there, How it's going?"}"""))
    }

}