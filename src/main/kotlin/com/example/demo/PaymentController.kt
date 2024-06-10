package com.example.demo


import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
class PaymentController(
    private val stripePaymentService: StripePaymentService,
    private val userRepository: UserRepository
) {

    @PostMapping
    fun createPayment(@RequestBody paymentDTO: PaymentDTO): ResponseEntity<PaymentIntentDTO> {
        val paymentIntentId = stripePaymentService.createPaymentIntent(
            paymentDTO.amount, paymentDTO.currency, paymentDTO.source, paymentDTO.description, paymentDTO.userId
        )
        val user = userRepository.findById(paymentDTO.userId).orElseThrow { RuntimeException("User not found") }
        user.coins = (user.coins ?: 0) + 5
        userRepository.save(user)
        return ResponseEntity.ok(PaymentIntentDTO(id = paymentIntentId))
    }
}