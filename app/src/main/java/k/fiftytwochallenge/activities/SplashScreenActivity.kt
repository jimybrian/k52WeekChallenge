package k.fiftytwochallenge.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import k.fiftytwochallenge.R

class SplashScreenActivity : AppCompatActivity(){

    private val SPLASH_TIME_OUT = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            //Start the bottom bar activity
            //Check if the user has logged in
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

}