package com.bakery.user.data.handler

import com.bakery.core.shared.types.user.UpdateUserDto
import com.bakery.core.shared.types.user.UserDto
import com.bakery.core.types.APIResponse
import com.bakery.core.types.ServerResponse
import com.bakery.user.data.storage.UserStorage
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

// todo: check for conflicts
interface UserHandler {
    suspend fun getUsers(): APIResponse<List<UserDto>>
    suspend fun getUserById(id: String): APIResponse<UserDto?>
    suspend fun getExistingUserById(id: String): APIResponse<UserDto?>
    suspend fun updateUser(dto: UpdateUserDto): APIResponse<UserDto?>
    suspend fun softDeleteUser(id: String): APIResponse<String>
    suspend fun deleteUser(id: String): APIResponse<String>
}

class DefaultUserHandler(
    private val coroutineContext: CoroutineContext,
    private val store: UserStorage
) : UserHandler {
    override suspend fun getUsers(): APIResponse<List<UserDto>> {
        return withContext(coroutineContext) {
            val result = store.getUsers()

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    data = result,
                    message = "No users were found"
                )
            }

            ServerResponse.ok(data = result, message = "Processed successfully")
        }
    }

    override suspend fun getUserById(id: String): APIResponse<UserDto?> {
        return withContext(coroutineContext) {
            val result = store.getUserById(id)
                ?: return@withContext ServerResponse.notFound(
                    message = "User with id $id was not found"
                )

            ServerResponse.ok(data = result, message = "Processed successfully")
        }
    }

    override suspend fun getExistingUserById(id: String): APIResponse<UserDto?> {
        return withContext(coroutineContext) {
            val result = store.getExistingUserById(id)
                ?: return@withContext ServerResponse.notFound(
                    message = "User with id $id was not found"
                )

            ServerResponse.ok(data = result, message = "Processed successfully")
        }
    }

    override suspend fun updateUser(dto: UpdateUserDto): APIResponse<UserDto?> {
        return withContext(coroutineContext) {
            val result = store.updateUser(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to update user, try again later"
                )

            ServerResponse.accepted(data = result, message = "Processed successfully")
        }
    }

    override suspend fun softDeleteUser(id: String): APIResponse<String> {
        return withContext(coroutineContext) {
            val result = store.softDeleteUser(id)

            if (result != null) {
                return@withContext ServerResponse.badRequest(
                    message = "Unable to delete user, try again later"
                )
            }

            ServerResponse.ok(data = "User deleted!", message = "Processed successfully")
        }
    }

    override suspend fun deleteUser(id: String): APIResponse<String> {
        return withContext(coroutineContext) {
            val result = store.deleteUser(id)

            if (result != null) {
                return@withContext ServerResponse.badRequest(
                    message = "Unable to delete user, try again later"
                )
            }

            ServerResponse.ok(data = "User deleted!", message = "Processed successfully")
        }
    }
}
