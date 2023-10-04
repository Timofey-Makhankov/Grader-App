package ch.timofey.grader

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GraderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        private var application: Application? = null;
        private fun getApplication(): Application? {
            return application
        }

        fun getContext(): Context? {
            return getApplication()?.applicationContext
        }
    }
}