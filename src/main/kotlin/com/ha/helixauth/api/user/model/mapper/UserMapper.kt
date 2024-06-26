package com.ha.helixauth.api.user.model.mapper

import com.ha.helixauth.api.company.model.Company
import com.ha.helixauth.api.user.model.User
import com.ha.helixauth.api.user.model.dto.UserDto

object UserMapper {
    fun toDTO(user: User): UserDto = UserDto(
        id = user.id,
        username = user.username,
        email = user.email,
        password = user.password,
        firstName = user.firstName,
        lastName = user.lastName,
        avatar = user.avatar,
        companyId = user.company.id,
        sessionToken = user.sessionToken,
        sessionTokenExpiry = user.sessionTokenExpiry
    )

    fun toEntity(userDto: UserDto, company: Company): User = User(
        username = userDto.username,
        email = userDto.email,
        password = userDto.password,
        firstName = userDto.firstName,
        lastName = userDto.lastName,
        avatar = userDto.avatar,
        company = company,
        sessionToken = userDto.sessionToken,
        sessionTokenExpiry = userDto.sessionTokenExpiry
    )
}
