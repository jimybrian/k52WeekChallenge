package k.fiftytwochallenge.dataModels

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class ChallengeModel() : RealmObject(){

    init {

    }

    constructor(challengeId:String, initialAmount:Float?, totalAmount:Float?, timeStamp:Date):this(){
        this.challengeId = challengeId
        this.initialAmount = initialAmount
        this.totalAmount = totalAmount
        this.timeStamp = timeStamp
    }

    @PrimaryKey
    var challengeId:String? = ""
    var initialAmount:Float? = 0f
    var totalAmount:Float? = 0f
    var timeStamp:Date? = null

}