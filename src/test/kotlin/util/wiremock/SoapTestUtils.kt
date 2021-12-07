package util.wiremock

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.equalToXml
import com.github.tomakehurst.wiremock.matching.EqualToXmlPattern
import javax.ws.rs.core.HttpHeaders.CONTENT_TYPE

object SoapTestUtils {

    const val TEST_SECURITY_HEADER = """
        <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" soap:mustUnderstand="1">
            <wsse:UsernameToken wsu:Id="[[xmlunit.ignore]]">
                <wsse:Username>dummyUserName</wsse:Username>
                <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">dummyPassword</wsse:Password>
           </wsse:UsernameToken>
        </wsse:Security>"""

    fun equalToXmlPattern(xml: String): EqualToXmlPattern = equalToXml(xml, true, "\\[\\[", "]]")

    fun soapResponse(body: String): ResponseDefinitionBuilder = WireMock
        .aResponse()
        .withStatus(200)
        .withHeader(CONTENT_TYPE, "text/xml;charset=UTF-8")
        .withBody(body.trimIndent())
}