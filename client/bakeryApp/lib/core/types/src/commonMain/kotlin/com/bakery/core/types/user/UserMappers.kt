package com.bakery.core.types.user

import com.bakery.core.types.user.dto.UserDto
import com.bakery.core.types.aliases.BakeryUser

fun UserDto.dtoToDomain(): User = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    username = username,
    email = email,
    phoneNumber = phoneNumber,
    birthDate = birthDate,
    address1 = address1,
    address2 = address2,
    gender = gender,
    createdAt = createdAt
)

fun User.domainToDb(): BakeryUser = BakeryUser(
    id = id,
    first_name = firstName,
    last_name = lastName,
    username = username,
    email = email,
    phone_number = phoneNumber,
    birth_date = birthDate,
    address1 = address1,
    address2 = address2,
    gender = gender,
    created_at = createdAt
)
