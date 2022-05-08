package luis.sizzo.finalproyecto_luis_sizzo.view.nav_fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import luis.sizzo.finalproyecto_luis_sizzo.common.*
import luis.sizzo.finalproyecto_luis_sizzo.databinding.FragmentCategoriesBinding
import luis.sizzo.finalproyecto_luis_sizzo.model.UIState
import luis.sizzo.finalproyecto_luis_sizzo.view.adapters.*
import luis.sizzo.finalproyecto_luis_sizzo.viewmodel.InitViewModel
import luis.sizzo.umbrella.model.remote.Product

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private val viewModel: InitViewModel by lazy {
        ViewModelProvider(this).get(InitViewModel::class.java)
    }
    private lateinit var binding: FragmentCategoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        viewModel.categoriesResponse.observe(viewLifecycleOwner) { action ->
            try {
                when (action) {
                    is UIState.LOADING -> {
                        Toast.makeText(requireContext(), "Loading Categories...", Toast.LENGTH_SHORT).show()
                    }
                    is UIState.SUCCESS<*> -> {

                        val categories = action.response as? List<String>
                        categories?.let {
                            CategoriesAdapter(it, binding.root).apply {
                                binding.recyclerView.settingsLinearHorizontal(this)
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

        viewModel.allProductsResponse.observe(viewLifecycleOwner) {
            it.let { action ->
                try {
                    when (action) {
                        is UIState.LOADING -> {
                            //Toast.makeText(requireContext(), "Loading Products...", Toast.LENGTH_SHORT).show()
                        }
                        is UIState.SUCCESS<*> -> {

                            val products = action.response as? List<Product>
                            products?.let {
                                ProductsAdapter(products, binding.root).apply {
                                    binding.recyclerViewList.settingsGrid(this)
                                    binding.shimmerViewContainer.stopShimmerAnimation();
                                    binding.shimmerViewContainer.setVisibility(View.GONE);
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
        viewModel.getCategories()
        viewModel.getAllProducts()
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
    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

}