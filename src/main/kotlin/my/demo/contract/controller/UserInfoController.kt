package my.demo.contract.controller

import my.demo.contract.client.UserInfoClient
import my.demo.contract.client.UserInfoClientResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class UserInfoController(private val client: UserInfoClient) {

    @GetMapping("/full-info/{name}")
    fun sayHello(@PathVariable name: String): Mono<UserInfoClientResponse> {
        return client.info(name)
    }
}