package info.novatec.examples.soapclient.quarkus.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix="httpconduit")
interface HttpConduitConfigProperties {

    fun receiveTimeout(): String

    fun connectionTimeout(): String
}