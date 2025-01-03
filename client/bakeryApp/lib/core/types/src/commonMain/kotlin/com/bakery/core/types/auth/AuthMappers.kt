package com.bakery.core.types.auth

import com.bakery.core.database.Bakery_session
import com.bakery.core.database.FindActiveAccount
import com.bakery.core.shared.types.auth.AuthDto
import com.bakery.core.types.user.User
import com.bakery.core.types.user.dtoToDomain

private typealias BakerySession = Bakery_session

fun FindActiveAccount.findToDomain(): Session = Session(
    accessToken = access_token,
    refreshToken = refresh_token,
    user = User(
        id = id ?: "",
        firstName = first_name ?: "",
        lastName = last_name ?: "",
        username = username ?: "",
        email = email ?: "",
        phoneNumber = phone_number ?: "",
        birthDate = birth_date ?: "",
        address1 = address1 ?: "",
        address2 = address2 ?: "",
        gender = gender ?: "",
        createdAt = created_at ?: "",
    )
)

fun AuthDto.dtoToDomain(): Session = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    user = user.dtoToDomain()
)

fun Session.sessionToDb(): BakerySession = BakerySession(
    access_token = accessToken,
    refresh_token = refreshToken,
    user_id = user.id,
    active = active
)
