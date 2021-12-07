package info.novatec.examples.soapclient.quarkus.soap

import javax.enterprise.context.ApplicationScoped
import org.apache.wss4j.common.ext.WSPasswordCallback
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import org.eclipse.microprofile.config.inject.ConfigProperty

//@ApplicationScoped
class UsernameTokenPasswordClientCallback : CallbackHandler {

//    @ConfigProperty(name = "wss4j.username")
//    lateinit var wss4jUsername: String
//
//    @ConfigProperty(name = "wss4j.password")
//    lateinit var wss4jPassword: String

    override fun handle(callbacks: Array<out Callback>) {
        for (callback in callbacks) {
            val wpc = callback as WSPasswordCallback
            if (wpc.identifier == "dummyUserName") {
                wpc.password = "dummyPassword"
                return
            }
        }
    }

}