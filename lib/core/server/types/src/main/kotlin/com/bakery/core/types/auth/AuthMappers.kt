package com.bakery.core.types.auth

import com.bakery.core.shared.types.Constants
import com.bakery.core.shared.types.auth.SignUpDto
import com.bakery.core.types.Role
import com.bakery.core.types.aliases.BakeryUser
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun SignUpDto.toDb(): BakeryUser = BakeryUser(
    id = Uuid.random().toString(),
    first_name = firstName,
    last_name = lastName,
    username = username,
    email = email,
    password = password,
    phone_number = phoneNumber,
    birth_date = birthDate,
    address1 = address1,
    address2 = address2,
    gender = gender,
    role = Role.USER.value,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
    deleted_at = null
)
