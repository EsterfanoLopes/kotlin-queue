package com.example.producer.repository

import com.example.producer.model.Person
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PersonRepository: ReactiveMongoRepository<Person, String> {
    fun findOneById(id: ObjectId): Person
}
