package info.novatec.examples.soapclient.quarkus.config

import io.quarkus.test.junit.QuarkusTest
import javax.inject.Inject
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
class WSS4JConfigPropertiesTest {

    @Inject
    lateinit var wss4jconfig: WSS4JConfigProperties

    @Test
    fun `all wss4j config properties should be loaded`() {
        Assertions.assertThat(wss4jconfig.username()).isEqualTo("testUserName")
        Assertions.assertThat(wss4jconfig.password()).isEqualTo("testPassword")
    }
}