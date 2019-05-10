package k.fiftytwochallenge.dataModels

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class WeekModel() : RealmObject(){


    init{
        //Empty default constructor
    }

    constructor(weekId:String, weekNumber:Int, weekDeposit:Float, weekTotal:Float, weekColor:Int, timeStamp:Date):this() {
        this.weekId = weekId
        this.weekNumber = weekNumber
        this.weekDeposit = weekDeposit
        this.weekTotal = weekTotal
        this.weekColor = weekColor
        this.timeStamp = timeStamp
    }

    @PrimaryKey
    var weekId:String? = ""
    var weekNumber:Int = 0
    var weekDeposit:Float? = 0f
    var weekTotal:Float? = 0f
    var weekColor:Int = 0
    var timeStamp:Date? = null

}