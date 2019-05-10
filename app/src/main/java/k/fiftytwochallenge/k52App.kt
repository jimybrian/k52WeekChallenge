package k.fiftytwochallenge

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.multidex.MultiDex
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import io.realm.Realm
import io.realm.RealmConfiguration
import java.text.SimpleDateFormat
import java.util.*

class k52App : Application(){


    object instance{
        @JvmStatic var apiUrl:String = "http://nothing"

        @JvmStatic var kApp : k52App? = null

        @JvmStatic var rqQueue: RequestQueue? = null

        @JvmStatic val realmFileName = "k52.realm"
        @JvmStatic val schemaVersion:Long = 0

        @JvmStatic var rlm: Realm? = null

        @JvmStatic fun initRequestQueue(){
            rqQueue = Volley.newRequestQueue(kApp)
        }

        @JvmStatic fun getRequestQueue(): RequestQueue?{
            if(rqQueue == null){
                rqQueue = Volley.newRequestQueue(kApp)
            }

            return rqQueue
        }

        @JvmStatic fun <T> addToRequestQueue(req: Request<T>) {
            // set the default tag if tag is empty
            if (isNetworkAvailable(kApp?.applicationContext as Context)) {
                req.retryPolicy = DefaultRetryPolicy(20 * 1000 , 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

                if(rqQueue != null)
                    rqQueue?.add(req)
                else
                    getRequestQueue()?.add(req)
            } else
                Toast.makeText(kApp, "An internet connection is needed to use this app", Toast.LENGTH_LONG).show()
        }

        @JvmStatic fun cancelPendingRequests(tag: Any) {
            if (rqQueue != null) {
                rqQueue?.cancelAll(tag)
            }
        }
    }


    public override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
        val realmConfiguration = RealmConfiguration.Builder()
            .name(k52App.instance.realmFileName)
            .schemaVersion(k52App.instance.schemaVersion)
//            .migration(MbMigration())
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)

//        Realm.deleteRealm(realmConfiguration)
        instance.kApp = this
        instance.rlm = Realm.getDefaultInstance()

//        DataFiller()
    }

    public override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}

//COMMON METHODS

//Checks if network is available
private fun isNetworkAvailable(cxt:Context): Boolean {
    val connectivityManager = cxt.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}


//Gets current date for calendar
fun getCurrentDate(): Date? {
    try {
        val currentDate = Calendar.getInstance() //Get the current date
        return currentDate.time
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

//Converts date to string
fun parseTimeAndDateFromString(d: Date): String {
    try {
        val date = SimpleDateFormat("E-dd-MMM-yyyy").format(d)
        val time = SimpleDateFormat("h:mm:ss a").format(d)
        return "$date $time"
    } catch (r: Exception) {
        r.printStackTrace()
        return ""
    }
}

//Converts current date time to string
fun getCurrentDateTimeReq():String{
    try {
        val d = getCurrentDate()
        val date = SimpleDateFormat("yyyyMMddhhmmss").format(d)
        return "$date"
    } catch (r: Exception) {
        r.printStackTrace()
        return ""
    }
}

//Two dp
fun getTwoDp(num: Float): String? {
    try {
        return String.format("%.02f", num)
    } catch (r: Exception) {
        r.printStackTrace()
        return null
    }

}

//Converts floats to strings
fun getFloatString(num: String): Float {
    try {
        return java.lang.Float.parseFloat(num)
    } catch (r: Exception) {
        r.printStackTrace()
        return 0f
    }
}

