package luis.sizzo.finalproyecto_luis_sizzo.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import luis.sizzo.finalproyecto_luis_sizzo.model.*
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val repository: Repository,
    private val ioDispatcher: CoroutineDispatcher,
    private val coroutineScope: CoroutineScope
) : ViewModel() {


    private val _categoriesResponse = MutableLiveData<UIState>()
    val categoriesResponse: MutableLiveData<UIState>
        get() = _categoriesResponse



    private val _productsResponse = MutableLiveData<UIState>()
    val productsResponse: MutableLiveData<UIState>
        get() = _productsResponse

    private val _allProductsResponse = MutableLiveData<UIState>()
    val allProductsResponse: MutableLiveData<UIState>
        get() = _allProductsResponse

    fun getCategories(){

        coroutineScope.launch {
            repository.getCategoriesResponse().collect {
                _categoriesResponse.postValue(it)
            }
        }
    }

    fun getProducts(category: String){
        coroutineScope.launch {
            repository.getProductsResponse(category).collect {
                _productsResponse.postValue(it)
            }
        }
    }

    fun getAllProducts(){

        coroutineScope.launch {
            repository.getAllProductsResponse().collect {
                _allProductsResponse.postValue(it)
            }
        }
    }
}