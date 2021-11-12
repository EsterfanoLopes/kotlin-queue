package com.example.producer.repository

import com.example.producer.model.Person
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface PersonRepository: ReactiveMongoRepository<Person, String> {
    fun findOneByExternalId(externalId: UUID): Mono<Person>
    fun deleteByExternalId(externalId: UUID): Mono<Person>
}
