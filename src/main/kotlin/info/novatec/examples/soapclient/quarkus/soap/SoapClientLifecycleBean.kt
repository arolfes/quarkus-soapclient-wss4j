package info.novatec.examples.soapclient.quarkus.soap

import io.quarkus.runtime.StartupEvent
import org.apache.cxf.BusFactory
import org.apache.cxf.transport.http.HTTPConduitConfigurer
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
import org.apache.wss4j.common.ConfigurationConstants
import org.apache.wss4j.dom.WSConstants
import org.apache.wss4j.dom.handler.WSHandlerConstants
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.enterprise.inject.Produces

@ApplicationScoped
class SoapClientLifecycleBean {

    @ConfigProperty(name = "soapclient.receiveTimeout")
    lateinit var receiveTimeout: String

    @ConfigProperty(name = "soapclient.connectionTimeout")
    lateinit var connectionTimeout: String

    @ConfigProperty(name = "wss4j.username")
    lateinit var wss4jUsername: String

    fun onStart(@Observes ev: StartupEvent) {
        configureCxfSoapClient()
    }

    private fun configureCxfSoapClient() {
        val httpConduitConfigurer =
            HTTPConduitConfigurer { _, _, conduit ->
                val clientPolicy = HTTPClientPolicy().apply {
                    receiveTimeout = this@SoapClientLifecycleBean.receiveTimeout.toLong()
                    connectionTimeout = this@SoapClientLifecycleBean.connectionTimeout.toLong()
                }
                conduit.client = clientPolicy
            }

        BusFactory.getThreadDefaultBus().setExtension(httpConduitConfigurer, HTTPConduitConfigurer::class.java)
    }

    @Produces
    fun getWSS4JOutInterceptor(): WSS4JOutInterceptor {
        val outProps: MutableMap<String, Any> = mutableMapOf(
            ConfigurationConstants.ACTION to WSHandlerConstants.USERNAME_TOKEN,
            ConfigurationConstants.PASSWORD_TYPE to WSConstants.PW_TEXT,
            ConfigurationConstants.PW_CALLBACK_CLASS to UsernameTokenPasswordClientCallback::class.java.name,
            ConfigurationConstants.USER to wss4jUsername
        )
        return WSS4JOutInterceptor(outProps)
    }

}