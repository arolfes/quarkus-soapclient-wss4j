package info.novatec.examples.soapclient.quarkus.config

import io.quarkus.test.junit.QuarkusTest
import javax.inject.Inject
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
class HttpConduitConfigPropertiesTest {

    @Inject
    lateinit var httpConduitConfig: HttpConduitConfigProperties

    @Test
    fun `all httpconduit config properties should be loaded`() {
        Assertions.assertThat(httpConduitConfig.receiveTimeout()).isEqualTo("60000")
        Assertions.assertThat(httpConduitConfig.connectionTimeout()).isEqualTo("30000")
    }

}