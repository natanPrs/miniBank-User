package com.user.services

import com.user.dtos.ResponseTransactionDto
import com.user.dtos.TransactionDto
import com.user.dtos.UserDto
import com.user.exceptions.InsufficientBalanceException
import com.user.exceptions.UserNotFoundException
import com.user.mapper.toUserEntity
import com.user.models.UserModel
import com.user.producer.UserProducer
import com.user.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userProducer: UserProducer) {

    fun createUser(userDto: UserDto): UserModel {
        val newUser = userDto.toUserEntity(userDto)
        userProducer.publishMessageEmail(newUser)
        return userRepository.save(newUser)
    }

    fun getAllUsers(): List<UserModel> { return userRepository.findAll() }

    @Transactional
    fun deleteUser(document: String) {
        val userToDelete = userRepository.findUserByDocument(document) ?: throw UserNotFoundException()
        userRepository.deleteById(userToDelete.id)
    }

    fun validateTransaction(transactionDto: TransactionDto): ResponseTransactionDto {
        val sender = userRepository.findUserByDocument(transactionDto.senderDocument) ?: throw UserNotFoundException()
        val receiver = userRepository.findUserByDocument(transactionDto.receiverDocument) ?: throw UserNotFoundException()

        if (sender.balance < transactionDto.amount) {
            throw InsufficientBalanceException()
        }

        realizeTransaction(sender, receiver, transactionDto.amount)

        val response = ResponseTransactionDto(
            senderName = sender.firstName,
            senderDocument = sender.document,
            receiverName = receiver.firstName,
            receiverDocument = receiver.document,
            amount = transactionDto.amount
        )

        return response
    }

    fun realizeTransaction(sender: UserModel, receiver: UserModel, amount: BigDecimal){
        sender.balance = sender.balance.subtract(amount)
        receiver.balance = receiver.balance.add(amount)

        userRepository.save(sender)
        userRepository.save(receiver)
    }

    fun findUserByDocument(document: String): UserModel {
        return userRepository.findUserByDocument(document) ?: throw UserNotFoundException()
    }
}