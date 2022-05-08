package luis.sizzo.finalproyecto_luis_sizzo.model.remote

import luis.sizzo.finalproyecto_luis_sizzo.common.*
import luis.sizzo.umbrella.model.remote.Product
import retrofit2.*
import retrofit2.http.*

interface ConnectionApi{

    @GET(END_POINT_CATEGORIES)
    suspend fun getCategories(
    ): Response<List<String>>

    @GET("$END_POINT_PRODUCTS_BY_CATEGORY/{category}")
    suspend fun getProducts(@Path("category")category: String): Response<List<Product>>

    @GET("$END_POINT_PRODUCTS")
    suspend fun getProduct(): Response<List<Product>>

}