package luis.sizzo.finalproyecto_luis_sizzo.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import luis.sizzo.finalproyecto_luis_sizzo.R

class Splash : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler()
            .postDelayed(Runnable {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }, 1500)
    }
}