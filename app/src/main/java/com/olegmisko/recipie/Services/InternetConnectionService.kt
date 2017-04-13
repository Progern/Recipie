package com.olegmisko.recipie.Services

import android.content.Context
import com.olegmisko.recipie.R
import org.jetbrains.anko.alert
import java.io.IOException
internal object InternetConnectionService {
/*
    This function checks actually is there a connection
    or not. So when the Wi-Fi or Mobile network will be on
    but there will be no data transferring it will return false.
 */

    fun checkInternetConnection(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }

    fun showNoConnectionDialog(cnt: Context) {
        cnt.alert {
            title("Oops! No Internet connection found.")
            message("Check your connection and try again please.")
            positiveButton("OK") {
                dismiss()
            }
            icon(cnt.resources.getDrawable(R.drawable.ic_no_signal))
        }.show()
    }
}
