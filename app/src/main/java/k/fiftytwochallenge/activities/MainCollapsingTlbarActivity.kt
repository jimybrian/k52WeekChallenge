package k.fiftytwochallenge.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.santalu.emptyview.EmptyView
import k.fiftytwochallenge.R
import k.fiftytwochallenge.dataModels.ChallengeModel
import k.fiftytwochallenge.getTwoDp
import k.fiftytwochallenge.models.ChallengeRepo
import k.fiftytwochallenge.utils.Collapsar

class MainCollapsingTlbarActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_collapsing_toolbar)
        initViews()

        startActivity(Intent(this, SplashScreenActivity::class.java))

        chRepo = ChallengeRepo(this as Activity, etInitialAmount as EditText,
            txTotalAmount as TextView, empView as EmptyView, rcView as RecyclerView)

        //Initialise
        initChallenge = chRepo?.getInitialChallengeAmounts()
    }

    var chRepo: ChallengeRepo? = null


    var etInitialAmount: EditText? = null
    var txTotalAmount: TextView? = null
    var empView: EmptyView? = null
    var rcView: RecyclerView? = null

    var tlBar:Toolbar? = null
    var clpTlb:CollapsingToolbarLayout? = null

    var appBar:AppBarLayout? = null

    var initChallenge:ChallengeModel? = null


    override fun onResume() {
        super.onResume()
        try{
            chRepo?.displayResults()
            initChallenge = chRepo?.getInitialChallengeAmounts()
            etInitialAmount?.setText(getTwoDp(initChallenge?.initialAmount as Float))
        }catch (r: Exception){
            r.printStackTrace()
        }
    }

    fun initViews(){
        etInitialAmount = findViewById(R.id.etInitialAmount)
        txTotalAmount = findViewById(R.id.txTotalAmount)
        empView = findViewById(R.id.empView)
        rcView = findViewById(R.id.rvMain)

        tlBar = findViewById(R.id.tlBar)
        clpTlb = findViewById(R.id.clpsTlbar)
        appBar = findViewById(R.id.app_bar)

        setSupportActionBar(tlBar)
        supportActionBar?.title = ""

        clpTlb?.setTitle("")
        tlBar?.setTitle("")
        //Tries to show the total amount on the toolbar
        appBar?.addOnOffsetChangedListener(object:Collapsar() {
            override fun onStateChanged(v:AppBarLayout, st:State) {
                when(st){
                    State.COLLAPSED -> {
                        supportActionBar?.setTitle("Saved: " + (getTwoDp(initChallenge?.totalAmount as Float) + " " + resources?.getString(
                                R.string.currency
                            )))
                        clpTlb?.setTitle(("Saved: " + getTwoDp(initChallenge?.totalAmount as Float) + " " + resources?.getString(R.string.currency)))
                    }
                    State.EXPANDED -> {
                        supportActionBar?.setTitle(resources?.getString(R.string.app_name))
                        clpTlb?.setTitle("")
                    }

                }
            }
        })

        rcView?.layoutManager = LinearLayoutManager(this)

        //Edit initial amount
        etInitialAmount?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
//                chRepo?.displayResults()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(p0.toString())) {
                    val x = (p0.toString()).toFloat()
                    //Validate inputs that they fall in the required range
                    if (x > 0 && x < 50000000) {
                        //Call the methods to update the values
                        chRepo?.calcAndDisplay(x)
                    }else{
                        etInitialAmount?.setText(getTwoDp(initChallenge?.initialAmount as Float))
                        Toast.makeText(this@MainCollapsingTlbarActivity, "Cannot have values greater than 0 or less than 50,000,000", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new, menu)
        val searchItem = menu?.findItem(R.id.mnuSearch)
        val scView = searchItem?.actionView as SearchView
        scView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                try{

                    var i = Integer.parseInt(query)
                    chRepo?.searchForWeek(i)
                }catch (r:Exception){
                    r.printStackTrace()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.mnuSearch){
            Toast.makeText(this, "Search for week by entering the week number", Toast.LENGTH_SHORT).show()
        }else if(item?.itemId == R.id.mnuRefresh){
            chRepo?.displayResults()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig?.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }
}