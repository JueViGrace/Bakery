package com.bakery.core.types

import com.bakery.core.shared.types.Constants
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class APIResponse<out T> {
    @Serializable
    data class Success<T>(
        @SerialName("status")
        val status: Int,
        @SerialName("description")
        val description: String,
        @SerialName("data")
        val data: T? = null,
        @SerialName("message")
        val message: String? = null,
        @SerialName("time")
        val time: String = Constants.currentTime
    ) : APIResponse<T>()

    @Serializable
    data class Failure(
        @SerialName("status")
        val status: Int,
        @SerialName("description")
        val description: String,
        @SerialName("data")
        val data: String? = null,
        @SerialName("message")
        val message: String? = null,
        @SerialName("time")
        val time: String = Constants.currentTime
    ) : APIResponse<Nothing>()
}

object ServerResponse {
    inline fun <reified T>ok(data: T? = null, message: String? = null): APIResponse<T> {
        return APIResponse.Success(
            status = HttpStatusCode.OK.value,
            description = HttpStatusCode.OK.description,
            data = data,
            message = message
        )
    }

    inline fun <reified T>created(data: T? = null, message: String? = null): APIResponse<T> {
        return APIResponse.Success(
            status = HttpStatusCode.Created.value,
            description = HttpStatusCode.Created.description,
            data = data,
            message = message
        )
    }

    inline fun <reified T>accepted(data: T? = null, message: String? = null): APIResponse<T> {
        return APIResponse.Success(
            status = HttpStatusCode.Accepted.value,
            description = HttpStatusCode.Accepted.description,
            data = data,
            message = message
        )
    }

    inline fun <reified T>noContent(data: T? = null, message: String? = null): APIResponse<T> {
        return APIResponse.Success(
            status = HttpStatusCode.NoContent.value,
            description = HttpStatusCode.NoContent.description,
            data = data,
            message = message
        )
    }

    inline fun<reified T> badRequest(data: T? = null, message: String? = null): APIResponse<T> {
        return if (data != null) {
            APIResponse.Success(
                status = HttpStatusCode.BadRequest.value,
                description = HttpStatusCode.BadRequest.description,
                data = data,
                message = message
            )
        } else {
            APIResponse.Failure(
                status = HttpStatusCode.BadRequest.value,
                description = HttpStatusCode.BadRequest.description,
                data = data,
                message = message
            )
        }
    }

    fun unauthorized(message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.Unauthorized.value,
            description = HttpStatusCode.Unauthorized.description,
            message = message
        )
    }

    fun forbidden(message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.Forbidden.value,
            description = HttpStatusCode.Forbidden.description,
            message = message
        )
    }

    inline fun <reified T> notFound(data: T? = null, message: String? = null): APIResponse<T> {
        return if (data != null) {
            APIResponse.Success(
                status = HttpStatusCode.NotFound.value,
                description = HttpStatusCode.NotFound.description,
                data = data,
                message = message
            )
        } else {
            APIResponse.Failure(
                status = HttpStatusCode.NotFound.value,
                description = HttpStatusCode.NotFound.description,
                data = data,
                message = message
            )
        }
    }

    fun methodNotAllowed(message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.MethodNotAllowed.value,
            description = HttpStatusCode.MethodNotAllowed.description,
            message = message
        )
    }

    fun notAcceptable(message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.NotAcceptable.value,
            description = HttpStatusCode.NotAcceptable.description,
            message = message
        )
    }

    fun requestTimeout(message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.RequestTimeout.value,
            description = HttpStatusCode.RequestTimeout.description,
            message = message
        )
    }

    fun conflict(message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.Conflict.value,
            description = HttpStatusCode.Conflict.description,
            message = message
        )
    }

    fun unsupportedMediaType(message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.UnsupportedMediaType.value,
            description = HttpStatusCode.UnsupportedMediaType.description,
            message = message
        )
    }

    fun internalServerError(data: String? = null, message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.InternalServerError.value,
            description = HttpStatusCode.InternalServerError.description,
            data = data,
            message = message
        )
    }

    fun serviceUnavailable(message: String? = null): APIResponse<Nothing> {
        return APIResponse.Failure(
            status = HttpStatusCode.ServiceUnavailable.value,
            description = HttpStatusCode.ServiceUnavailable.description,
            message = message
        )
    }
}
