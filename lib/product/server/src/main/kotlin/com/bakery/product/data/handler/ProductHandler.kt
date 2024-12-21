package com.bakery.product.data.handler

import com.bakery.core.shared.types.product.CreateProductDto
import com.bakery.core.shared.types.product.ProductDto
import com.bakery.core.shared.types.product.UpdateProductDto
import com.bakery.core.types.APIResponse
import com.bakery.core.types.ServerResponse
import com.bakery.product.data.storage.ProductStorage
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface ProductHandler {
    suspend fun getProducts(): APIResponse<List<ProductDto>>
    suspend fun getProductById(id: String): APIResponse<ProductDto?>
    suspend fun getExistingProducts(): APIResponse<List<ProductDto>>
    suspend fun getExistingProductById(id: String): APIResponse<ProductDto?>
    suspend fun createProduct(dto: CreateProductDto, images: List<String>): APIResponse<ProductDto?>
    suspend fun updateProduct(dto: UpdateProductDto, images: List<String>): APIResponse<ProductDto?>
    suspend fun softDeleteProduct(id: String): APIResponse<String>
    suspend fun deleteProduct(id: String): APIResponse<String>
}

class DefaultProductHandler(
    private val coroutineContext: CoroutineContext,
    private val store: ProductStorage
) : ProductHandler {
    override suspend fun getProducts(): APIResponse<List<ProductDto>> {
        return withContext(coroutineContext) {
            val result = store.getProducts()

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    data = result,
                    message = "Products were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getProductById(id: String): APIResponse<ProductDto?> {
        return withContext(coroutineContext) {
            val result = store.getProductById(id)

            if (result == null) {
                return@withContext ServerResponse.notFound(
                    message = "Products with id $id was not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getExistingProducts(): APIResponse<List<ProductDto>> {
        return withContext(coroutineContext) {
            val result = store.getExistingProducts()

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    data = result,
                    message = "Products were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getExistingProductById(id: String): APIResponse<ProductDto?> {
        return withContext(coroutineContext) {
            val result = store.getExistingProductById(id)

            if (result == null) {
                return@withContext ServerResponse.notFound(
                    message = "Products with id $id was not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createProduct(
        dto: CreateProductDto,
        images: List<String>
    ): APIResponse<ProductDto?> {
        return withContext(coroutineContext) {
            val result = store.createProduct(dto, images)

            if (result == null) {
                return@withContext ServerResponse.notFound(
                    message = "Unable to create product, try again later"
                )
            }

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun updateProduct(
        dto: UpdateProductDto,
        images: List<String>
    ): APIResponse<ProductDto?> {
        return withContext(coroutineContext) {
            val result = store.updateProduct(dto, images = emptyList())

            if (result == null) {
                return@withContext ServerResponse.notFound(
                    message = "Unable to update product, try again later"
                )
            }

            ServerResponse.accepted(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun softDeleteProduct(id: String): APIResponse<String> {
        return withContext(coroutineContext) {
            val result = store.softDeleteProduct(id)

            if (result != null) {
                return@withContext ServerResponse.notFound(
                    message = "Unable to delete product, try again later"
                )
            }

            ServerResponse.ok(
                data = "Product with id $id was deleted!",
                message = "Processed successfully"
            )
        }
    }

    override suspend fun deleteProduct(id: String): APIResponse<String> {
        return withContext(coroutineContext) {
            val result = store.deleteProduct(id)

            if (result != null) {
                return@withContext ServerResponse.notFound(
                    message = ""
                )
            }

            ServerResponse.ok(
                data = "Product with id $id was permanently deleted!",
                message = "Processed successfully"
            )
        }
    }
}
