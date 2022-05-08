package luis.sizzo.finalproyecto_luis_sizzo.di

import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import luis.sizzo.finalproyecto_luis_sizzo.common.BASE_URL
import luis.sizzo.finalproyecto_luis_sizzo.model.*
import luis.sizzo.finalproyecto_luis_sizzo.model.remote.ConnectionApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideApiService(): ConnectionApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConnectionApi::class.java)

    @Provides
    fun provideRepositoryLayer(service: ConnectionApi): Repository =
        RepositoryImpl(service)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(dispatcher)
}