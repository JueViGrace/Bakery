package com.bakery.product.routes

import com.bakery.core.types.JwtAuthName
import com.bakery.core.types.applicationResponse
import com.bakery.product.data.handler.ProductHandler
import com.bakery.core.shared.types.product.CreateProductDto
import com.bakery.core.shared.types.product.ProductByIdDto
import com.bakery.core.shared.types.product.UpdateProductDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

// todo: manage product image files uploads
// todo: validate product image files
// todo: manage deletion or insertion of new images

fun Route.productRoutes() {
    val handler by inject<ProductHandler>()

    route("/products") {
        // Product admin routes
        authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
            route("/admin") {
                get {
                    val response = handler.getProducts()

                    call.applicationResponse(
                        response = response,
                        onFailure = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        },
                        onSuccess = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        }
                    )
                }

                post<ProductByIdDto> { body ->
                    val response = handler.getProductById(body.id)

                    call.applicationResponse(
                        response = response,
                        onFailure = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        },
                        onSuccess = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        }
                    )
                }

                // todo: get path for images
                post<CreateProductDto> { body ->
//                    val multipart = call.receiveMultipart()
                    val response = handler.createProduct(
                        dto = body,
                        images = emptyList()
                    )

                    call.applicationResponse(
                        response = response,
                        onFailure = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        },
                        onSuccess = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        }
                    )
                }

                patch<UpdateProductDto> { body ->
                    val response = handler.updateProduct(
                        dto = body,
                        images = emptyList()
                    )

                    call.applicationResponse(
                        response = response,
                        onFailure = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        },
                        onSuccess = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        }
                    )
                }

                delete {
                    val body = call.receive<ProductByIdDto>()
                    val response = handler.softDeleteProduct(body.id)

                    call.applicationResponse(
                        response = response,
                        onFailure = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        },
                        onSuccess = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        }
                    )
                }

                delete("/forever") { body ->
                    val body = call.receive<ProductByIdDto>()
                    val response = handler.deleteProduct(body.id)

                    call.applicationResponse(
                        response = response,
                        onFailure = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        },
                        onSuccess = { res ->
                            call.respond(
                                status = HttpStatusCode(
                                    value = res.status,
                                    description = res.description
                                ),
                                message = res
                            )
                        }
                    )
                }
            }
        }

        get {
            val response = handler.getExistingProducts()

            call.applicationResponse(
                response = response,
                onFailure = { res ->
                    call.respond(
                        status = HttpStatusCode(
                            value = res.status,
                            description = res.description
                        ),
                        message = res
                    )
                },
                onSuccess = { res ->
                    call.respond(
                        status = HttpStatusCode(
                            value = res.status,
                            description = res.description
                        ),
                        message = res
                    )
                }
            )
        }

        post<ProductByIdDto> { body ->
            val response = handler.getExistingProductById(body.id)

            call.applicationResponse(
                response = response,
                onFailure = { res ->
                    call.respond(
                        status = HttpStatusCode(
                            value = res.status,
                            description = res.description
                        ),
                        message = res
                    )
                },
                onSuccess = { res ->
                    call.respond(
                        status = HttpStatusCode(
                            value = res.status,
                            description = res.description
                        ),
                        message = res
                    )
                }
            )
        }
    }
}
