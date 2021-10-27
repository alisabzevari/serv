import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.serv.Handler
import io.serv.http.Method
import io.serv.http.Request
import io.serv.http.Response
import io.serv.http.Status
import kotlinx.coroutines.delay
import java.net.URI

class HandlerTest : StringSpec({
    "it should run the handler over the request and return the response" {
        val expectedResponse = Response(Status.OK, emptyMap(), null)
        val h: Handler = {
            delay(100)
            expectedResponse
        }

        val res = h(Request(Method.GET, URI("/"), emptyMap(), null))

        res shouldBe expectedResponse
    }
})
