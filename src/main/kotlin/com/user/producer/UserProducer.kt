package com.user.producer

import com.user.dtos.EmailDto
import com.user.models.UserModel
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class UserProducer(private val rabbitTemplate: RabbitTemplate) {

    @Value("\${broker.queue.email.name}")
    lateinit var routingKey: String

    fun publishMessageEmail(userModel: UserModel) {
        val message = EmailDto(
            userId = userModel.id,
            userName = userModel.firstName,
            userEmail = userModel.email
        )
        rabbitTemplate.convertAndSend("", routingKey, message)
    }
}