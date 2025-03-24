package com.user.controllers

import com.user.dtos.UserDto
import com.user.models.UserModel
import com.user.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody userDto: UserDto): ResponseEntity<UserModel> {
        val newUser = userService.createUser(userDto)
        return ResponseEntity(newUser, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserModel>> { return ResponseEntity(userService.getAllUsers(), HttpStatus.OK) }

    @DeleteMapping("/{document}")
    fun deleteUser(@PathVariable document: String): ResponseEntity<String> {
        userService.deleteUser(document)
        return ResponseEntity("User has been deleted!", HttpStatus.OK)
    }
}