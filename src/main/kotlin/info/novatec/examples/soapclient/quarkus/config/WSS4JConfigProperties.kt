package info.novatec.examples.soapclient.quarkus.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix="wss4j")
interface WSS4JConfigProperties {

    fun username(): String

    fun password(): String
}