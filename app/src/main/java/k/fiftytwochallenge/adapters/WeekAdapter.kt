package k.fiftytwochallenge.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import k.fiftytwochallenge.R
import k.fiftytwochallenge.dataModels.WeekModel
import k.fiftytwochallenge.getTwoDp
import k.fiftytwochallenge.intToString

class WeekViewHolder(v:View) : RecyclerView.ViewHolder(v){

    var imgColor:ImageView? = null
    var txWeekNo:TextView? = null
    var txWeekDeposit:TextView? = null
    var txWeekTotal:TextView? = null

    init {

        imgColor = v.findViewById(R.id.imgColor)
        txWeekNo = v.findViewById(R.id.txWeekNo)
        txWeekDeposit = v.findViewById(R.id.txWeekDeposit)
        txWeekTotal = v.findViewById(R.id.txWeekTotal)
    }

}



class WeekAdapter(act:Activity, lsWeeks:List<WeekModel>) : RecyclerView.Adapter<WeekViewHolder>(){

    var act:Activity? = null
    var lsWeeks:List<WeekModel>? = null

    init {
        this.act = act
        this.lsWeeks = lsWeeks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        return WeekViewHolder(LayoutInflater.from(act).inflate(R.layout.list_weeks, parent, false))
    }

    override fun getItemCount(): Int {
        return lsWeeks?.size as Int
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val week = this.lsWeeks?.get(position)

        holder?.txWeekTotal?.setText("Total " + getTwoDp(week?.weekTotal as Float) + " " + act?.resources?.getString(R.string.currency))
        holder?.txWeekDeposit?.setText("Deposit " + getTwoDp(week?.weekDeposit as Float) + " " + act?.resources?.getString(R.string.currency))
        holder?.txWeekNo?.setText("Week # " + intToString(week?.weekNumber as Int as Int))
        holder?.imgColor?.setBackgroundColor(week?.weekColor as Int)

    }

}