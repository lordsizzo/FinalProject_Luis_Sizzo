package luis.sizzo.finalproyecto_luis_sizzo.view.adapters

import android.annotation.SuppressLint
import android.view.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import luis.sizzo.finalproyecto_luis_sizzo.R
import luis.sizzo.finalproyecto_luis_sizzo.databinding.ItemsProductsBinding
import luis.sizzo.umbrella.model.remote.Product

class ProductsAdapter(private val items: List<Product>, val view: View) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    class ProductsViewHolder(val binding: ItemsProductsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ItemsProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        try {
            holder.binding.tvProductName.text = items[position].title
            holder.binding.tvProductPrice.text = "$${items[position].price}"
            holder.binding.rbProductRating.rating = items[position].rating.rate ?: 5.0f
            Picasso.get().load(items[position].image).into(holder.binding.imProduct)
            holder.binding.imProduct.alpha = 1.0f
            holder.binding.imProduct.visibility = View.VISIBLE

            holder.binding.root.setOnClickListener {
                val bundle = bundleOf("id" to items[position].id.toString(),
                    "title" to items[position].title,
                    "price" to items[position].price.toString(),
                    "category" to items[position].category,
                    "description" to items[position].description,
                    "image" to items[position].image,
                    "rate" to items[position].rating.rate.toString(),
                    "count" to items[position].rating.count.toString())
                view.findNavController().navigate(R.id.detailsProductFragment, bundle)
            }
        } catch (e: Exception) {
            //context.toast(e.toString())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}