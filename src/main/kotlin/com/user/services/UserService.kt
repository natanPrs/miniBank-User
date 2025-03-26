package com.user.services

import com.user.dtos.EmailDto
import com.user.dtos.TransactionDto
import com.user.dtos.UserDto
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
        val userToDelete = userRepository.findUserByDocument(document) ?: throw Exception("User not found.")
        userRepository.deleteById(userToDelete.id)
    }

    fun validateTransaction(transactionDto: TransactionDto) {
        val sender = userRepository.findUserByDocument(transactionDto.senderDocument) ?: throw Exception("User not found.")
        val receiver = userRepository.findUserByDocument(transactionDto.receiverDocument) ?: throw Exception("User not found.")

        if (sender.balance < transactionDto.amount) {
            throw Exception("Insufficient balance.")
        }

        realizeTransaction(sender, receiver, transactionDto.amount)
    }

    fun realizeTransaction(sender: UserModel, receiver: UserModel, amount: BigDecimal){
        sender.balance = sender.balance.subtract(amount)
        receiver.balance = receiver.balance.add(amount)

        userRepository.save(sender)
        userRepository.save(receiver)
    }
}