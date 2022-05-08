package luis.sizzo.finalproyecto_luis_sizzo.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import luis.sizzo.finalproyecto_luis_sizzo.R
import luis.sizzo.finalproyecto_luis_sizzo.common.Utilities.Companion.changeImageCategory
import luis.sizzo.finalproyecto_luis_sizzo.databinding.ItemsCategoriesBinding

class CategoriesAdapter(private val items: List<String>, val view: View) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(val binding: ItemsCategoriesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemsCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        try {
            holder.binding.categoryName.text = items[position]
            holder.binding.imCategoryIcon.setImageResource(changeImageCategory(position));
            holder.binding.root.setOnClickListener {
                val bundle = bundleOf("category" to items[position])
                view.findNavController().navigate(R.id.listProductsFragment2, bundle)
            }
        } catch (e: Exception) {
            //context.toast(e.toString())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}