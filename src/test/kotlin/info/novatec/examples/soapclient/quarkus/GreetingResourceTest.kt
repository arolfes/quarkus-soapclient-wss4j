package info.novatec.examples.soapclient.quarkus

import info.novatec.examples.soapclient.quarkus.soap.HelloWorldServiceConsumer
import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest {

    @InjectMock
    lateinit var helloWorldServiceConsumer: HelloWorldServiceConsumer

    @Test
    fun testSayHiEndpoint() {
        every { helloWorldServiceConsumer.sayHi()} returns "He there, How it's going?"

        given()
          .`when`().get("/hello/sayhi")
          .then()
             .statusCode(200)
             .body(`is`("""{"response":"He there, How it's going?"}"""))
    }

}