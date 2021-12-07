package info.novatec.examples.soapclient.quarkus.soap

import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import org.apache.wss4j.common.ext.WSPasswordCallback
import org.eclipse.microprofile.config.ConfigProvider

class UsernameTokenPasswordClientCallback : CallbackHandler {

    override fun handle(callbacks: Array<out Callback>) {
        val username = ConfigProvider.getConfig().getValue(WSS4J_USERNAME_PROP_KEY, String::class.java)
        val password = ConfigProvider.getConfig().getValue(WSS4J_PASSWORD_PROP_KEY, String::class.java)
        val wpc = callbacks[0] as WSPasswordCallback
        if (wpc.identifier == username) {
            wpc.password = password
        }
    }

    companion object {
        private const val WSS4J_USERNAME_PROP_KEY = "wss4j.username"
        private const val WSS4J_PASSWORD_PROP_KEY = "wss4j.password"
    }

}