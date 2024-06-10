package com.example.demo

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
open class UserService(private val userRepository: UserRepository) {

    @Transactional
    open fun initUsers() {
        listOf(
            User(username = "user1", coins = 0),
            User(username = "user2", coins = 0),
            User(username = "user3", coins = 0),
            User(username = "user4", coins = 0),
            User(username = "user5", coins = 0)
        ).forEach { user ->
            userRepository.save(user)
        }
    }
}