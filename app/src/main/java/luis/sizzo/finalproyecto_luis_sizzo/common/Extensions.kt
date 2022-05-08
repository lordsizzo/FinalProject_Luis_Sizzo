package luis.sizzo.finalproyecto_luis_sizzo.common

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import luis.sizzo.finalproyecto_luis_sizzo.view.adapters.CategoriesAdapter
import luis.sizzo.finalproyecto_luis_sizzo.view.adapters.ProductsAdapter

fun RecyclerView.settingsGrid(adapter: ProductsAdapter){
    this.layoutManager = GridLayoutManager(this.context, 2)
    this.adapter = adapter
}
fun RecyclerView.settingsLinearHorizontal(adapter: CategoriesAdapter){
    this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    this.adapter = adapter
}