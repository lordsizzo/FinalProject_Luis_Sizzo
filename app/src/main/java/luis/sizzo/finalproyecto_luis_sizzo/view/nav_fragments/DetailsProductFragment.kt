package luis.sizzo.finalproyecto_luis_sizzo.view.nav_fragments

import android.graphics.Bitmap
import android.os.*
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import dagger.hilt.android.AndroidEntryPoint
import luis.sizzo.finalproyecto_luis_sizzo.common.Utilities.Companion.getContrastColor
import luis.sizzo.finalproyecto_luis_sizzo.common.settingsGrid
import luis.sizzo.finalproyecto_luis_sizzo.databinding.FragmentDetailsProductBinding
import luis.sizzo.finalproyecto_luis_sizzo.model.UIState
import luis.sizzo.finalproyecto_luis_sizzo.view.MainActivity
import luis.sizzo.finalproyecto_luis_sizzo.view.adapters.ProductsAdapter
import luis.sizzo.finalproyecto_luis_sizzo.viewmodel.InitViewModel
import luis.sizzo.umbrella.model.remote.Product
import java.net.URL


@AndroidEntryPoint
class DetailsProductFragment : Fragment() {

    private var category = ""
    private var id = ""
    private lateinit var binding: FragmentDetailsProductBinding

    private val viewModel: InitViewModel by lazy {
        ViewModelProvider(this).get(InitViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsProductBinding.inflate(inflater, container, false)
        id = arguments?.getString("id").toString()
        category = arguments?.getString("category").toString()
        initView()
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {

        binding.tvProductName.text = arguments?.getString("title").toString()
        binding.tvProductDescription.text = arguments?.getString("description").toString()
        binding.tvProductPrice.text = "$${arguments?.getString("price").toString()}"
        binding.tvProductCategory.text = "Category: ${arguments?.getString("category").toString()}"
        binding.rbProductRating.rating = arguments?.getString("rate")?.toFloat() ?: 5.0f
        Glide.with(this).asBitmap().load(URL(arguments?.getString("image").toString())).listener(object :RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Bitmap,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onPalette(Palette.from(resource).generate())
                return false
            }

            fun onPalette(palette: Palette?) {
                if (null != palette) {
                    if (palette.dominantSwatch != null) {
                        binding.linearLayoutProductDetails.setBackgroundColor(palette.mutedSwatch!!.rgb)

                        if (Build.VERSION.SDK_INT >= 21) {
                            val window: Window = requireActivity().getWindow()
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                            window.setStatusBarColor(palette.dominantSwatch!!.rgb)
                            MainActivity.toolbar.setBackgroundColor(palette.dominantSwatch!!.rgb)

                            MainActivity.toolbar.setTitleTextColor(getContrastColor(palette.mutedSwatch!!.rgb))
                            MainActivity.toolbar.setSubtitleTextColor(getContrastColor(palette.mutedSwatch!!.rgb))

                        }
                    }
                }
            }
        }).into(binding.imProduct)

        viewModel.productsResponse.observe(viewLifecycleOwner) {

            it.let { action ->

                try {
                    when (action) {
                        is UIState.LOADING -> {
                            Toast.makeText(requireContext(), "Loading Details...", Toast.LENGTH_SHORT).show()
                        }
                        is UIState.SUCCESS<*> -> {

                            val product = action.response as? List<Product>

                            product?.let {
                                product.filter {
                                    it.id != id.toInt()
                                }.apply {
                                    ProductsAdapter(this, binding.root).apply {
                                        binding.recyclerViewList.settingsGrid(this)
                                    }
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
        Log.d("DetailsProductFragment", "Category: ${category}")
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


    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

}