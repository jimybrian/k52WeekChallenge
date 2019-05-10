package k.fiftytwochallenge.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import java.lang.Exception

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

        appBar?.addOnOffsetChangedListener(object:Collapsar() {
            override fun onStateChanged(v:AppBarLayout, st:State) {
                when(st){
                    State.COLLAPSED -> supportActionBar?.setTitle((getTwoDp(initChallenge?.totalAmount as Float) + " " + resources?.getString(R.string.currency)))
                    State.EXPANDED -> supportActionBar?.setTitle(resources?.getString(R.string.app_name))
                }
            }
        })

        rcView?.layoutManager = LinearLayoutManager(this)

        //Edit initial amount
        etInitialAmount?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                chRepo?.displayResults()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(p0.toString())) {
                    val x = (p0.toString()).toFloat()
                    if (x > 0 && x < 50000000) {
                        //Call the methods to update the values
                        chRepo?.calcSaveTotalsUpdateTextView(x)
                    }else{
                        etInitialAmount?.setText(getTwoDp(initChallenge?.initialAmount as Float))
                        Toast.makeText(this@MainCollapsingTlbarActivity, "Cannot have values greater than 0 or less than 50,000,000", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


}