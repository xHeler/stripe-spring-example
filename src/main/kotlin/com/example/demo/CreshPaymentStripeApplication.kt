package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
open class CreshPaymentStripeApplication

fun main(args: Array<String>) {
	runApplication<CreshPaymentStripeApplication>(*args)
}

@Component
class DatabaseInitializer(private val userService: UserService) : CommandLineRunner {
	private val logger = LoggerFactory.getLogger(DatabaseInitializer::class.java)

	override fun run(vararg args: String?) {
		userService.initUsers()
		logger.info("Users initialized successfully!")
	}
}