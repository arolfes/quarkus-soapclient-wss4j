package util.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import info.novatec.examples.soapclient.quarkus.soap.HelloWorldServiceConsumer.Companion.SERVICE_ENDPOINT
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager.TestInjector
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager.TestInjector.AnnotatedAndMatchesType

class WireMockResource : QuarkusTestResourceLifecycleManager {

    val wireMockServer = WireMockServer(
        WireMockConfiguration
            .wireMockConfig()
            .dynamicPort()
            .httpsPort(0)
    )

    override fun start(): MutableMap<String, String> {
        wireMockServer.start()

        return mutableMapOf(
            "quarkus.cxf.client.\"greeterService\".client-endpoint-url"
                    to "http://localhost:${wireMockServer.port()}$SERVICE_ENDPOINT"
        )
    }

    override fun stop() {
        wireMockServer.stop()
    }

    override fun inject(testInjector: TestInjector) {
        testInjector.injectIntoFields(
            wireMockServer,
            AnnotatedAndMatchesType(InjectWireMock::class.java, WireMockServer::class.java)
        )
    }
}
