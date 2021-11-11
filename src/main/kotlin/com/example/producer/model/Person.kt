package com.example.producer.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDate
import java.util.UUID

data class Person(
    @Id
    val id: ObjectId?,
    @Field
    val externalId: UUID = UUID.randomUUID(),
    val name: String,
    val collageCompletedYear: Int?,
    val bornAt: LocalDate,
    val active: Boolean,
    var delivered: Boolean? = false
)

data class PersonUpdate(
    val name: String?,
    val collageCompletedYear: Int?,
    val bornAt: LocalDate?,
)
