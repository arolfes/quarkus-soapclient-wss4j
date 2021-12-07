package info.novatec.examples.soapclient.quarkus.soap

import info.novatec.examples.soapclient.quarkus.config.HttpConduitConfigProperties
import info.novatec.examples.soapclient.quarkus.config.WSS4JConfigProperties
import io.quarkus.runtime.Startup
import io.quarkus.runtime.StartupEvent
import org.apache.cxf.BusFactory
import org.apache.cxf.transport.http.HTTPConduitConfigurer
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
import org.apache.wss4j.common.ConfigurationConstants
import org.apache.wss4j.dom.WSConstants
import org.apache.wss4j.dom.handler.WSHandlerConstants
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.event.Observes
import javax.enterprise.inject.Produces
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Startup
class SoapClientLifecycleBean(
    @Inject val httpConduitConfig: HttpConduitConfigProperties,
    @Inject val wss4jconfig: WSS4JConfigProperties,
    @Inject val usernameTokenPasswordClientCallback: UsernameTokenPasswordClientCallback
) {

    fun onStart(@Observes ev: StartupEvent) {
        configureCxfSoapClient()
    }

    private fun configureCxfSoapClient() {
        val httpConduitConfigurer =
            HTTPConduitConfigurer { _, _, conduit ->
                val clientPolicy = HTTPClientPolicy().apply {
                    receiveTimeout = this@SoapClientLifecycleBean.httpConduitConfig.receiveTimeout().toLong()
                    connectionTimeout = this@SoapClientLifecycleBean.httpConduitConfig.connectionTimeout().toLong()
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
            ConfigurationConstants.PW_CALLBACK_REF to usernameTokenPasswordClientCallback,
            ConfigurationConstants.USER to wss4jconfig.username()
        )
        return WSS4JOutInterceptor(outProps)
    }

}