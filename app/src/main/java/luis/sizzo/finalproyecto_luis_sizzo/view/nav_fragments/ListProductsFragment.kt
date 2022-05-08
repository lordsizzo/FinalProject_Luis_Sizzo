package luis.sizzo.finalproyecto_luis_sizzo.view.nav_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import luis.sizzo.finalproyecto_luis_sizzo.databinding.FragmentListProductsBinding
import luis.sizzo.finalproyecto_luis_sizzo.model.UIState
import luis.sizzo.finalproyecto_luis_sizzo.view.adapters.ProductsAdapter
import luis.sizzo.finalproyecto_luis_sizzo.viewmodel.InitViewModel
import luis.sizzo.umbrella.model.remote.Product

@AndroidEntryPoint
class ListProductsFragment : Fragment() {
    private lateinit var binding: FragmentListProductsBinding
    private val viewModel: InitViewModel by lazy {
        ViewModelProvider(this).get(InitViewModel::class.java)
    }
    private var category = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListProductsBinding.inflate(inflater, container, false)
        category = arguments?.getString("category").toString()
        initView()
        return binding.root
    }

    private fun initView() {
        viewModel.productsResponse.observe(viewLifecycleOwner) {
            it.let { action ->
                try {
                    when (action) {
                        is UIState.LOADING -> {
                            Toast.makeText(requireContext(), "Loading Products...", Toast.LENGTH_SHORT).show()
                        }
                        is UIState.SUCCESS<*> -> {

                            val categories = action.response as? List<Product>
                            categories?.let {
                                ProductsAdapter(categories, binding.root).apply {
                                    binding.listProductsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                                    binding.listProductsRecyclerView.adapter = this
                                }
                            }?: showError("Error at casting")
                        }
                        is UIState.ERROR -> {
                            showError(action.error.localizedMessage)
                        }
                    }
                } catch (e: Exception) {
                    showError(e.toString())
                }
            }
        }
        Log.d("ListProductsFragment", "Category: ${category}")
        viewModel.getProducts(category)
    }
    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error occurred")
            .setMessage(message)
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}