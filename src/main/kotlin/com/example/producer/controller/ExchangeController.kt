package com.example.producer.controller

import com.example.producer.model.Person
import com.example.producer.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/exchanges")
class ExchangeController(
    private val personService: PersonService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping()
    fun getHealthcheck(): HttpEntity<Any?> {
        return ResponseEntity.ok().build()
    }

    @PostMapping("/persons/{exchange}/{routingKey}")
    fun postPersonOnExchange(
        @PathVariable exchange: String,
        @PathVariable routingKey: String,
        @RequestBody person: Person
    ): Mono<Person> {
        log.info("sending message $person to exchange $exchange with routing key $routingKey")
        return personService.saveAndEnqueue(exchange, routingKey, person)
    }
}
