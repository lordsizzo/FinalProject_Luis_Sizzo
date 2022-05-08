package luis.sizzo.finalproyecto_luis_sizzo.model

sealed class UIState {
    object LOADING : UIState()
    class SUCCESS<T>(val response : T) : UIState()
    class ERROR(val error: Exception) : UIState()
}