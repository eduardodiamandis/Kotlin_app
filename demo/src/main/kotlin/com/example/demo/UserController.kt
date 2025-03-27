package com.example.demo.users

import org.springframework.beans.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(@Autowired private val UserRepository: UserRepository) {

    // Obter todos os usuários
    @GetMapping("")
    fun getAllUsers(): List<User> =
        UserRepository.findAll().toList()

    // Criar um novo usuário
    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val savedUser = UserRepository.save(user)
        return ResponseEntity(savedUser, HttpStatus.CREATED)
    }

    // Obter um usuário pelo ID
    @GetMapping("/{id}")
    fun getUserByid(@PathVariable("id") userId: Int): ResponseEntity<User> {
        val user = UserRepository.findById(userId).orElse(null)
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Atualizar um usuário pelo ID
    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Int, @RequestBody user: User): ResponseEntity<User> {
        val existingUser = UserRepository.findById(userId).orElse(null)
        if (existingUser == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedUser = existingUser.copy(name = user.name, email = user.email)
        UserRepository.save(updatedUser)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    // Deletar um usuário pelo ID
    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        if (!UserRepository.existsById(userId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        UserRepository.deleteById(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}