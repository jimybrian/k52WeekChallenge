package k.fiftytwochallenge.dataModels

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class ChallengeModel() : RealmObject(){

    init {

    }

    constructor(challengeId:String, initialAmount:Int, totalAmount:Int, timeStamp:Date):this(){
        this.challengeId = challengeId
        this.initialAmount = initialAmount
        this.totalAmount = totalAmount
        this.timeStamp = timeStamp
    }

    @PrimaryKey
    var challengeId:String? = ""
    var initialAmount:Int = 0
    var totalAmount:Int = 0
    var timeStamp:Date? = null

}