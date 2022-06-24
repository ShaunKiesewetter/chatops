package net.wedocode.chatopsdemo.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatOpsController {
    @GetMapping("/")
    fun healthCheck(): ResponseEntity<String> {
        // Sends a 200 back to the load balancer
        return ResponseEntity.ok("Banking service is running")
    }
}