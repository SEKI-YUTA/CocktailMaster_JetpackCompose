package com.yuta.cocktailmaster.util

import android.content.Context
import android.net.ConnectivityManager

class AppUtil {
    companion object {
        fun checkNetworkConnection(context: Context, onResult: (Boolean) -> Unit) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: android.net.Network) {
                    super.onAvailable(network)
                    onResult(true)
                }

                override fun onLost(network: android.net.Network) {
                    super.onLost(network)
                    onResult(false)
                }
            })
        }
    }
}
