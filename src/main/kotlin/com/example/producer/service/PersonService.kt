package com.example.producer.service

import com.example.producer.model.Person
import com.example.producer.repository.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.amqp.rabbit.core.RabbitTemplate
import reactor.core.publisher.Mono

@Service
class PersonService(
    val personRepository: PersonRepository,
    val rabbitTemplate: RabbitTemplate
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun saveAndEnqueue(exchange: String, routingKey: String, person: Person): Mono<Person> {
        person.delivered = false
        log.info("saving person $person")
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, person)
            return personRepository.save(person)
        } catch (e: IllegalArgumentException) {
            log.error("Exception $e")
            throw e
        }
    }
}
