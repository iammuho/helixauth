package com.ha.helixauth.api.user.service

import com.ha.helixauth.api.company.repository.CompanyRepository
import com.ha.helixauth.api.user.model.User
import com.ha.helixauth.api.user.model.dto.UserDto
import com.ha.helixauth.api.user.model.mapper.UserMapper
import com.ha.helixauth.api.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val companyRepository: CompanyRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(userDTO: UserDto, companyId: Long): User {
        val company = companyRepository.findById(companyId).get()
        val userInfo = "User: ${userDTO.username}, Name: ${userDTO.firstName} ${userDTO.lastName}"

        company.blockchain.addTransactionToBlockchain(userInfo)

        return userRepository.save(UserMapper.toEntity(userDTO, company))
    }

    fun updateUser(userId: Long, userDTO: UserDto): User {
        val user = userRepository.findById(userId).orElseThrow { RuntimeException("User not found") }

        val hashedPassword = passwordEncoder.encode(userDTO.password)

        // Update user details
        user.apply {
            username = userDTO.username
            password = hashedPassword // Ensure password is encrypted
            email = email
            firstName = firstName
            lastName = lastName
            avatar = avatar
        }

        // Record the update in the blockchain
        val updateInfo = "User Updated: ${user.username}, Time: ${LocalDateTime.now()}"
        user.company.blockchain.addTransactionToBlockchain(updateInfo)

        // Save the updated user and company (to persist the blockchain changes)
        userRepository.save(user)
        companyRepository.save(user.company)

        return user
    }

    fun authenticateUser(email: String, password: String): UserDto {
        println("authenticateUser REQUEST:: ${email}, $password")

        val user = userRepository.findByEmailAndPassword(email, password)
        println("authenticateUser RESPONSE:: ${email}, $password")

        return UserMapper.toDTO(user)
    }
}
