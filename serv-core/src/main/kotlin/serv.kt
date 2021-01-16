package io.serv

import io.serv.http.Request
import io.serv.http.Response

typealias Handler = suspend (request: Request) -> Response
typealias Filter = suspend (Handler) -> Handler
