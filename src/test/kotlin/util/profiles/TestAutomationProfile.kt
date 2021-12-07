package util.profiles

import io.quarkus.test.junit.QuarkusTestProfile
import io.quarkus.test.junit.QuarkusTestProfile.TestResourceEntry
import util.wiremock.WireMockResource

class TestAutomationProfile : QuarkusTestProfile {

    override fun getConfigProfile(): String = "test-automation"

    override fun getConfigOverrides(): Map<String, String> = mutableMapOf(
        "httpconduit.receiveTimeout" to "1000",
        "httpconduit.connectionTimeout" to "1000",
        "wss4j.username" to "dummyUserName",
        "wss4j.password" to "dummyPassword"
    )

    override fun testResources(): MutableList<TestResourceEntry> =
        mutableListOf(TestResourceEntry(WireMockResource::class.java))

}