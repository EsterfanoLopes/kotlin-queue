package com.example.producer.service

import com.example.producer.model.Person
import com.example.producer.model.PersonUpdate
import com.example.producer.repository.PersonRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.amqp.rabbit.core.RabbitTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class PersonService(
    val personRepository: PersonRepository,
    val rabbitTemplate: RabbitTemplate
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun createAndEnqueue(exchange: String, routingKey: String, person: Person): Person {
        try {
            val saved = personRepository.save(person).awaitFirst()
            rabbitTemplate.convertAndSend(exchange, routingKey, saved)
            return saved
        } catch (e: IllegalArgumentException) {
            log.error("Error delivering person message and saving it $e")
            throw e
        }
    }

    fun updatePersonByExternalId(externalId: UUID, person: PersonUpdate): Mono<Person> {
        try {
            val found = personRepository.findOneByExternalId(externalId)
            return found.flatMap {
                personRepository.save(
                    Person(
                        id = it.id,
                        externalId = it.externalId,
                        name = person.name ?: it.name,
                        collageCompletedYear = person.collageCompletedYear ?: it.collageCompletedYear,
                        bornAt = person.bornAt ?: it.bornAt,
                        active = it.active,
                        delivered = it.delivered
                    )
                )
            }
        } catch (e: Exception) {
            log.error("Error updating person $e")
            throw e
        }
    }

    fun getPersons(): Flux<Person> {
        return personRepository.findAll()
    }

    fun getPersonByExternalId(externalId: UUID): Mono<Person> {
        return personRepository.findOneByExternalId(externalId)
    }

    suspend fun deletePersonByExternalId(externalId: UUID): Person? {
        return personRepository.deleteByExternalId(externalId).awaitSingleOrNull()
    }
}
