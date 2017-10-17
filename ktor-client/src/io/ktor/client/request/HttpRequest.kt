package io.ktor.client.request

import io.ktor.client.utils.*
import io.ktor.http.HttpMethod
import io.ktor.util.Attributes
import java.nio.charset.Charset
import javax.net.ssl.SSLSocketFactory


class HttpRequest(
        val url: Url,
        val method: HttpMethod,
        val headers: Headers,
        val payload: Any,
        val charset: Charset?
) {
    val cacheControl: HttpRequestCacheControl by lazy { headers.computeRequestCacheControl() }
}

class HttpRequestBuilder() {
    var method = HttpMethod.Get
    var payload: Any = Unit
    var charset: Charset? = null
    var sslSocketFactory: SSLSocketFactory? = null
    val url = UrlBuilder()
    val headers = HeadersBuilder()
    val flags = Attributes()
    val cacheControl: HttpRequestCacheControl get() = headers.computeRequestCacheControl()

    constructor(data: HttpRequest) : this() {
        method = data.method
        url.takeFrom(data.url)
        headers.appendAll(data.headers)
    }

    fun headers(block: HeadersBuilder.() -> Unit) = headers.apply(block)

    fun url(block: UrlBuilder.() -> Unit) = url.block()

    fun build(): HttpRequest = HttpRequest(url.build(), method, headers.build(), payload, charset)
}

fun HttpRequestBuilder.takeFrom(builder: HttpRequestBuilder): HttpRequestBuilder {
    method = builder.method
    payload = builder.payload
    charset = builder.charset
    sslSocketFactory = builder.sslSocketFactory
    url.takeFrom(builder.url)
    headers.appendAll(builder.headers)

    return this
}
