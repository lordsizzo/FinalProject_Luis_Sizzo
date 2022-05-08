package luis.sizzo.umbrella.model.remote


data class Product(
    val id: Int,
    val title: String,
    val price: Float,
    val image: String,
    val description: String,
    val category: String,
    val rating: Rating
)

data class Rating(
    val rate: Float,
    val count: Int
)
