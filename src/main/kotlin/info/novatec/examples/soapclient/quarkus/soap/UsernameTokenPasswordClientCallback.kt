package info.novatec.examples.soapclient.quarkus.soap

import io.quarkus.runtime.Startup
import javax.inject.Singleton
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import org.apache.wss4j.common.ext.WSPasswordCallback
import org.eclipse.microprofile.config.inject.ConfigProperty

@Singleton
@Startup
class UsernameTokenPasswordClientCallback(
    @ConfigProperty(name = "wss4j.username") val username: String,
    @ConfigProperty(name = "wss4j.password") val password: String
) : CallbackHandler {

    override fun handle(callbacks: Array<out Callback>) {
        val wpc = callbacks[0] as WSPasswordCallback
        if (wpc.identifier == username) {
            wpc.password = password
        }
    }
}