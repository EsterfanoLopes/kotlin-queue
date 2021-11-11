package com.example.producer.controller

import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v2")
class ApiController {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getHealthcheck(): HttpEntity<Any?> {
        return ResponseEntity.ok("{\"status\": \"ok\"}")
    }
}
