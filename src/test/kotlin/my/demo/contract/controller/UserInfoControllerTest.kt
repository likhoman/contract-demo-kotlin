package my.demo.contract.controller

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.LambdaDsl
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit.MockServerConfig
import au.com.dius.pact.consumer.junit5.PactConsumerTest
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.PactSpecVersion
import au.com.dius.pact.core.model.V4Pact
import au.com.dius.pact.core.model.annotations.Pact
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@MockServerConfig

@PactConsumerTest
@PactTestFor(providerName = "provider", pactVersion = PactSpecVersion.V4)

@ExtendWith(value = [PactConsumerTestExt::class, SpringExtension::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserInfoControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @SuppressWarnings("unused")
    @Pact(provider = "provider", consumer = "consumer")
    fun userInfoPact(builder: PactDslWithProvider): V4Pact {
        return builder
            .given("A user info exists")
            .uponReceiving("User info request")
            .method("GET")
            .path("/info/Barry")
            .willRespondWith()
            .status(200)
            .body(
                LambdaDsl.newJsonBody { parameter ->
                    parameter.stringMatcher("id", "\\d{5,10}", "1234567")
                    parameter.stringType("firstName", "Barry")
                    parameter.stringType("lastName", "Irvine")
                    parameter.array("phoneNumbers") { phoneNumbers ->
                        phoneNumbers.`object` { number ->
                            number.stringMatcher("type", "MOBILE|HOME|WORK", "MOBILE")
                            number.stringType("number", "0711-111-111")
                        }
                    }
                }.build()
            ).toPact(V4Pact::class.java)
    }

    @Test
    fun `should return success`(mockServer: MockServer) {
        webTestClient.get()
            .uri("${mockServer.getUrl()}/info/{name}", "Barry")
            .exchange()
            .expectBody()
            .json(
                UserInfoControllerTest::class.java.getResource("/__files/clients/userInfoClient/response.json").readText()
            )

    }
}