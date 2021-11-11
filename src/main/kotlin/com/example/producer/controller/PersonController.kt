package com.example.producer.controller

import com.example.producer.model.Person
import com.example.producer.model.PersonUpdate
import com.example.producer.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("api/v2/person")
class PersonController(
    private val personService: PersonService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/{exchange}/{routingKey}")
    fun postPersonOnExchange(
        @PathVariable(required = true) exchange: String,
        @PathVariable(required = true) routingKey: String,
        @RequestBody person: Person
    ): Mono<Person> {
        log.info("sending message $person to exchange $exchange with routing key $routingKey")
        return personService.createAndEnqueue(exchange, routingKey, person)
    }

    @GetMapping
    fun getPersons(): Flux<Person> {
        return personService.getPersons()
    }

    @GetMapping("{externalId}")
    fun getPerson(
        @PathVariable(required = true) externalId: UUID,
    ): Mono<Person> {
        return personService.getPersonByExternalId(externalId)
    }

    @PutMapping("{externalId}")
    fun putPerson(
        @PathVariable(required = true) externalId: UUID,
        @RequestBody person: PersonUpdate
    ): Mono<Person> {
        return personService.updatePersonByExternalId(externalId, person)
    }

    @DeleteMapping("{externalId}")
    suspend fun deletePerson(
        @PathVariable(required = true) externalId: UUID,
    ): HttpEntity<Any?> {
        val deleted = personService.deletePersonByExternalId(externalId)
        if (deleted != null) {
            return ResponseEntity.noContent().build()
        }

        return ResponseEntity.notFound().build()
    }
}
