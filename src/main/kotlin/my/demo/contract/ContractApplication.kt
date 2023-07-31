package my.demo.contract

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactivefeign.spring.config.EnableReactiveFeignClients

@EnableReactiveFeignClients
@SpringBootApplication
class ContractApplication

fun main(args: Array<String>) {
    runApplication<ContractApplication>(*args)
}
