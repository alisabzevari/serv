import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class HandlerTest: StringSpec({
    "it should run the handler over the request and return the response" {
        "test" shouldBe "test"
    }
})
