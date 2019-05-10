package k.fiftytwochallenge.models

import android.app.Activity
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.santalu.emptyview.EmptyView
import io.realm.Realm
import io.realm.Sort
import k.fiftytwochallenge.R
import k.fiftytwochallenge.adapters.WeekAdapter
import k.fiftytwochallenge.dataModels.ChallengeModel
import k.fiftytwochallenge.dataModels.WeekModel
import k.fiftytwochallenge.getCurrentDate
import k.fiftytwochallenge.getRandomColor
import k.fiftytwochallenge.getTwoDp
import org.ocpsoft.prettytime.units.Week
import java.util.*
import kotlin.collections.ArrayList

class ChallengeRepo(){

    var rlm: Realm? = null

    init {
        rlm = Realm.getDefaultInstance()
    }

    var act:Activity? = null

    constructor(act:Activity):this(){
        this.act = act
    }

    constructor(act:Activity, etInitialAmount:EditText, txTotalAmount:TextView, empView:EmptyView, rcView:RecyclerView):this(){
        this.act = act
        this.etInitialAmount = etInitialAmount
        this.rcView = rcView
        this.txTotalAmount = txTotalAmount
        this.empView = empView
    }

    var etInitialAmount:EditText? = null; var txTotalAmount:TextView? = null;
    var empView:EmptyView? = null
    var rcView:RecyclerView? = null



    //Setup the initial amounts and weeks
    fun setupInitialAmountAndWeeks(){

        var challengeModel:ChallengeModel = ChallengeModel()
        challengeModel.challengeId = UUID.randomUUID().toString()
        challengeModel.initialAmount = 2f
        challengeModel.totalAmount = 0f
        challengeModel.timeStamp = getCurrentDate()


        var initialChallenge = rlm?.where(ChallengeModel::class.java)?.findFirst()

        var lsInitialCats = rlm?.where(WeekModel::class.java)?.findAll()

        var lsCats:MutableList<WeekModel> = ArrayList<WeekModel>()
        for (i in 1..52){

            var weekModel:WeekModel = WeekModel()
            weekModel.weekId = UUID.randomUUID().toString()
            weekModel.weekNumber = i
            weekModel.weekDeposit = 0f
            weekModel.weekTotal = 0f
            weekModel.weekColor = getRandomColor()
            weekModel.timeStamp = getCurrentDate()

            lsCats.add(weekModel)
        }



        if(initialChallenge == null){
            rlm?.beginTransaction()
            rlm?.copyToRealm(challengeModel)
            rlm?.commitTransaction()
        }

        if((lsInitialCats?.size as Int) < lsCats.size){
            rlm?.beginTransaction()
            lsCats?.forEach { w ->
                rlm?.copyToRealm(w)
            }
            rlm?.commitTransaction()
        }

    }

    //Calculate and save the model totals()
    //This doen't calculate correct values
//    fun calcSaveTotals(initialAmount:Float){
//        var initialChallenge = rlm?.where(ChallengeModel::class.java)?.findFirst()
//
//        rlm?.beginTransaction()
//        initialChallenge?.initialAmount = initialAmount
//
//        var lsInitialCats = rlm?.where(WeekModel::class.java)?.sort("weekNumber", Sort.ASCENDING)?.findAll()
//
//        var i = 0
//        var totalAmount:Float = 0f
//
//        lsInitialCats?.forEach { w ->
//            if(w.weekNumber == 1) {
//                w.weekDeposit = initialAmount
//                w.weekTotal = initialAmount
//            }else{
//                w.weekDeposit = initialAmount
//                w.weekTotal = w.weekDeposit as Float + lsInitialCats.get(i)?.weekDeposit as Float
//            }
//
//            i += 1
//        }
//
//
//        //Calculate the total amount
//        lsInitialCats?.forEach { w ->
//            totalAmount += (w?.weekDeposit as Float)
//        }
//
//        initialChallenge?.totalAmount = totalAmount
//
//        rlm?.commitTransaction()
//    }

    fun calcSaveTotals(initialAmount:Float){
        var initialChallenge = rlm?.where(ChallengeModel::class.java)?.findFirst()

        rlm?.beginTransaction()
        initialChallenge?.initialAmount = initialAmount

        var lsInitialCats = rlm?.where(WeekModel::class.java)?.sort("weekNumber", Sort.ASCENDING)?.findAll()

        var i = 0
        var totalAmount:Float = 0f

        lsInitialCats?.forEach { w ->
            w.weekDeposit = (w.weekNumber * initialAmount)
            w.weekTotal = w.weekDeposit + (lsInitialCats.get(i)?.weekTotal as Float)
            i += 1
        }


        //Calculate the total amount
        lsInitialCats?.forEach { w ->
            totalAmount += (w?.weekDeposit as Float)
        }

        initialChallenge?.totalAmount = totalAmount

        rlm?.commitTransaction()
    }



    fun displayResults(){
        var initialChallenge = rlm?.where(ChallengeModel::class.java)?.findFirst()

        var lsInitialCats = rlm?.where(WeekModel::class.java)?.sort("weekNumber", Sort.ASCENDING)?.findAll()

        if(initialChallenge != null){

            txTotalAmount?.setText(getTwoDp(initialChallenge?.totalAmount) + " " + act?.resources?.getString(R.string.currency))
            etInitialAmount?.setText(getTwoDp(initialChallenge?.initialAmount))

            if(lsInitialCats?.size as Int > 0){
                var wkAdapter:WeekAdapter = WeekAdapter(act as Activity, lsInitialCats)
                rcView?.adapter = wkAdapter
                empView?.showContent()
            }else{
                empView?.showEmpty()
            }
        }
    }

}