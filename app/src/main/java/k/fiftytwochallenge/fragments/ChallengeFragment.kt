package k.fiftytwochallenge.fragments

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.santalu.emptyview.EmptyView
import k.fiftytwochallenge.R
import k.fiftytwochallenge.models.ChallengeRepo
import java.lang.Exception

class ChallengeFragment : Fragment(){

    var chRepo:ChallengeRepo? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_challenge, container, false)
        initViews(rootView)
        chRepo = ChallengeRepo(activity as Activity, etInitialAmount as EditText,
            txTotalAmount as TextView, empView as EmptyView, rcView as RecyclerView)

        return rootView
    }


    override fun onResume() {
        super.onResume()
        try{
            chRepo?.displayResults()
        }catch (r:Exception){
            r.printStackTrace()
        }
    }

    var etInitialAmount:EditText? = null
    var txTotalAmount:TextView? = null
    var empView:EmptyView? = null
    var rcView:RecyclerView? = null

    fun initViews(v:View){
        etInitialAmount = v.findViewById(R.id.etInitialAmount)
        txTotalAmount = v.findViewById(R.id.txTotalAmount)
        empView = v.findViewById(R.id.empView)
        rcView = v.findViewById(R.id.rvMain)

        rcView?.layoutManager = LinearLayoutManager(activity)

        //Edit initial amount
        etInitialAmount?.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

}