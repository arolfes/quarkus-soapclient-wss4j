package info.novatec.examples.soapclient.quarkus.soap

import info.novatec.examples.soapclient.quarkus.config.WSS4JConfigProperties
import io.quarkus.runtime.Startup
import javax.inject.Inject
import javax.inject.Singleton
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import org.apache.wss4j.common.ext.WSPasswordCallback

@Singleton
@Startup
class UsernameTokenPasswordClientCallback(
    @Inject val wss4jconfig: WSS4JConfigProperties
) : CallbackHandler {

    override fun handle(callbacks: Array<out Callback>) {
        val wpc = callbacks[0] as WSPasswordCallback
        if (wpc.identifier == wss4jconfig.username()) {
            wpc.password = wss4jconfig.password()
        }
    }
}