package hu.tb.tasky

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class TaskyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}