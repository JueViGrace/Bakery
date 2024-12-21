package com.bakery.core.types.user

import com.bakery.core.shared.types.user.UserDto
import com.bakery.core.types.aliases.BakeryUser

fun BakeryUser.toDto(): UserDto = UserDto(
    id = id,
    firstName = first_name,
    lastName = last_name,
    username = username,
    email = email,
    phoneNumber = phone_number,
    birthDate = birth_date,
    address1 = address1,
    address2 = address2,
    gender = gender,
    createdAt = created_at
)
