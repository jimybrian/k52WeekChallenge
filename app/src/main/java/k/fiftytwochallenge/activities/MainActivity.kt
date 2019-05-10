package k.fiftytwochallenge.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import k.fiftytwochallenge.R
import k.fiftytwochallenge.fragments.ChallengeFragment

class MainActivity : AppCompatActivity() {

    var tlBar:Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        startActivity(Intent(this, SplashScreenActivity::class.java))
    }


    fun initViews(){
        tlBar = findViewById(R.id.toolbar)
        val txTitle = tlBar?.findViewById<TextView>(R.id.txToolbarTitle)
        txTitle?.setText(resources?.getString(R.string.app_name))

        setSupportActionBar(tlBar)
        supportActionBar?.title = ""

        this.fragmentNavigation(ChallengeFragment())
    }


    fun fragmentNavigation(frag: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, frag)
        fragmentTransaction.commit()
    }
}
