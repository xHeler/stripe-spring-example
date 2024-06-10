package com.example.demo

import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentConfirmParams
import com.stripe.param.PaymentIntentCreateParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class StripePaymentService(@Value("\${stripe.api.key}") private val stripeApiKey: String) {

    init {
        Stripe.apiKey = stripeApiKey
    }


    fun createPaymentIntent(amount: Long, currency: String, paymentMethodId: String, description: String, userId: Long): String {
        return try {
            val params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .addPaymentMethodType("card")
                .setDescription(description)
                .build()

            val paymentIntent = PaymentIntent.create(params)

            val confirmParams = PaymentIntentConfirmParams.builder()
                .setPaymentMethod(paymentMethodId)
                .build()

            val confirmedPaymentIntent = paymentIntent.confirm(confirmParams)
            confirmedPaymentIntent.id
        } catch (e: StripeException) {
            throw RuntimeException("Failed to create payment intent: ${e.message}", e)
        }
    }
}