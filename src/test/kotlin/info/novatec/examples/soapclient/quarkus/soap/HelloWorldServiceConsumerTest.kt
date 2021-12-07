package info.novatec.examples.soapclient.quarkus.soap

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import info.novatec.examples.soapclient.quarkus.soap.HelloWorldServiceConsumer.Companion.SERVICE_ENDPOINT
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import javax.inject.Inject
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import util.profiles.TestAutomationProfile
import util.wiremock.InjectWireMock
import util.wiremock.SoapTestUtils.TEST_SECURITY_HEADER
import util.wiremock.SoapTestUtils.equalToXmlPattern
import util.wiremock.SoapTestUtils.soapResponse

@QuarkusTest
@TestProfile(TestAutomationProfile::class)
class HelloWorldServiceConsumerTest {

    @InjectWireMock
    lateinit var wireMockServer: WireMockServer

    @Inject
    lateinit var helloWorldConsumer: HelloWorldServiceConsumer

    @BeforeEach
    fun initMockServer() {
        wireMockServer.resetAll()
    }

    @Test
    fun `should send Wss4J Sec Header`() {
        val responsePayload = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
              xmlns:typ="http://apache.org/hello_world_soap_http/types">
              <soapenv:Header/>
              <soapenv:Body>
                <typ:greetMeResponse>
                  <typ:responseType>Hello Alex</typ:responseType>
                </typ:greetMeResponse>
              </soapenv:Body>
            </soapenv:Envelope>
        """.trimIndent()

        stubMockServerWithResponse(responsePayload)

        val response = helloWorldConsumer.greetMe("Alex")

        val expectedRequest = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
              <soapenv:Header>
                $TEST_SECURITY_HEADER
              </soapenv:Header>
              <soapenv:Body>
                <greetMe xmlns="http://apache.org/hello_world_soap_http/types">
                  <requestType>Alex</requestType>
                </greetMe>
              </soapenv:Body>
            </soapenv:Envelope>
        """.trimIndent()
        verifyMockServerRequest(expectedRequest)
        assertThat(response).isEqualTo("Hello Alex")
    }

    private fun stubMockServerWithResponse(responsePayload: String) {
        wireMockServer.stubFor(
            post(SERVICE_ENDPOINT)
                .willReturn(
                    soapResponse(responsePayload)
                )
        )
    }

    private fun verifyMockServerRequest(expectedRequest: String) {
        wireMockServer.verify(
            postRequestedFor(
                urlEqualTo(SERVICE_ENDPOINT)
            ).withRequestBody(
                equalToXmlPattern(expectedRequest)
            )
        )
    }


}