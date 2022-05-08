package luis.sizzo.finalproyecto_luis_sizzo.model

import kotlinx.coroutines.flow.*
import luis.sizzo.finalproyecto_luis_sizzo.model.remote.ConnectionApi
import javax.inject.Inject

interface Repository {
    fun getCategoriesResponse(): Flow<UIState>
    fun getProductsResponse(category: String): Flow<UIState>
    fun getAllProductsResponse(): Flow<UIState>
}

class RepositoryImpl @Inject constructor(
    private val service: ConnectionApi
): Repository {
    override fun getCategoriesResponse() = flow {
        emit(UIState.LOADING)
        try {
            val respose = service.getCategories()
            if (respose.isSuccessful) {
                respose.body()?.let {
                    emit(UIState.SUCCESS(it))
                } ?: throw Exception("Error null")
            } else {
                throw Exception("Error failure")
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }

    override fun getProductsResponse(category: String) = flow {
        emit(UIState.LOADING)
        try {
            val respose = service.getProducts(category)
            if (respose.isSuccessful) {
                respose.body()?.let {
                    emit(UIState.SUCCESS(it))
                } ?: throw Exception("Error null")
            } else {
                throw Exception("Error failure")
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }

    override fun getAllProductsResponse() = flow {
        emit(UIState.LOADING)
        try {
            val respose = service.getProduct()
            if (respose.isSuccessful) {
                respose.body()?.let {
                    emit(UIState.SUCCESS(it))
                } ?: throw Exception("Error null")
            } else {
                throw Exception("Error failure")
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }

//    override suspend fun getCategoriesResponse(): Flow<UIState> {
//        val catchinData = GlobalScope.async {
//            catchCategories()
//        }
//        return catchinData.await()
//    }

//    override suspend fun getProductsResponse(category: String): Flow<UIState> {
//        val catchinData = GlobalScope.async {
//            catchProducts(category)
//        }
//        return catchinData.await()
//    }

//    override suspend fun getAllProductsResponse(): Flow<UIState> {
//        val catchinData = GlobalScope.async {
//            catchProduct()
//        }
//        return catchinData.await()
//    }

//    private fun catchCategories(): List<String>? {
//        var items: List<String>? = null
//        ConnectionApi.initRetrofit()
//            .getCategories()
//            .execute()
//            .body()?.let {
//                items = it
//            } ?: run {
//                items = null
//            }
//        Log.d("catchCategories", "catchWeatherCityRetrofit: $items")
//        return items
//    }

//    private fun catchProducts(category: String): List<Product>? {
//        var items: List<Product>? = null
//        ConnectionApi.initRetrofit()
//            .getProducts(category)
//            .execute()
//            .body()?.let {
//                items = it
//            } ?: run {
//            items = null
//        }
//
//        Log.d("catchProducts", "catchWeatherCityRetrofit: $category $items")
//        return items
//    }
//
//
//    private fun catchProduct():List<Product>? {
//        var items: List<Product>? = null
//        ConnectionApi.initRetrofit()
//            .getProduct()
//            .execute()
//            .body()?.let {
//                items = it
//            } ?: run {
//            items = null
//        }
//
//        Log.d("catchSpecificProduct", "catchWeatherCityRetrofit: $items")
//        return items
//    }


//    private fun catchWeatherResponseRetrofit(zipCode: String, zipCountry: String, units:String): WeatherResponse? {
//        var items: WeatherResponse? = null
//        ConnectionApi.initRetrofit()
//            .getCityWeatherDetails("$zipCountry,$zipCode", units, WEATHER_TOKEN)
//            .execute()
//            .body()?.let {
//                items = it
//            } ?: run {
//            items = null
//        }
//        Log.d("Repository", "catchWeatherResponseRetrofit: $items")
//
//        return items
//    }
}