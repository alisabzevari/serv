package http;

import io.serv.http.Status
import java.net.http.HttpHeaders

fun Int.toStatus() = Status.fromCode(this)!!

// TODO: fixme: How should I convert list of header values to string header value?
fun HttpHeaders.toHeaders() = this.map().map { it.key to it.value.joinToString() }.toMap()
