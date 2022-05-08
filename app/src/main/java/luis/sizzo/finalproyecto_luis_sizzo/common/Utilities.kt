package luis.sizzo.finalproyecto_luis_sizzo.common

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import luis.sizzo.finalproyecto_luis_sizzo.R

class Utilities {

    companion object{
        fun changeImageCategory(position: Int): Int{
            when(position){
                0 -> {
                    return R.mipmap.electrronic
                }
                1 -> {
                    return R.mipmap.diamond
                }
                2 -> {
                    return R.mipmap.man
                }
                3 -> {
                    return R.mipmap.woman
                }else -> {
                    return R.mipmap.diamond
                }
            }
        }

        @ColorInt
        fun getContrastColor(@ColorInt color: Int): Int {
            val whiteContrast = ColorUtils.calculateContrast(Color.WHITE, color)
            val blackContrast = ColorUtils.calculateContrast(Color.BLACK, color)
            return if (whiteContrast > blackContrast) Color.BLACK else Color.WHITE
        }
    }
}