package my.demo.contract.client

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono

@Component
@ReactiveFeignClient(name = "user-info-client", url = "\${user-info-client.url}")
interface UserInfoClient {
    
    @GetMapping("/request/{name}")
    fun info(@PathVariable name: String): Mono<UserInfoClientResponse>
}