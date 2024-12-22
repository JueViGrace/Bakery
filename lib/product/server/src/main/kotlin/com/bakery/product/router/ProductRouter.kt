package com.bakery.product.router

import com.bakery.core.types.JwtAuthName
import com.bakery.product.data.handler.ProductHandler
import com.bakery.product.router.routes.admin.adminGetProductById
import com.bakery.product.router.routes.admin.adminGetProducts
import com.bakery.product.router.routes.admin.createProduct
import com.bakery.product.router.routes.admin.deleteProduct
import com.bakery.product.router.routes.admin.softDeleteProduct
import com.bakery.product.router.routes.admin.updateProduct
import com.bakery.product.router.routes.getProductById
import com.bakery.product.router.routes.getProducts
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

// todo: manage product image files uploads
// todo: validate product image files
// todo: manage deletion or insertion of new images

fun Route.productRouter() {
    val handler by inject<ProductHandler>()

    route("/products") {
        // Product admin routes
        authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
            route("/admin") {
                adminGetProducts(handler)

                adminGetProductById(handler)

                createProduct(handler)

                updateProduct(handler)

                softDeleteProduct(handler)

                deleteProduct(handler)
            }
        }

        getProducts(handler)

        getProductById(handler)
    }
}
