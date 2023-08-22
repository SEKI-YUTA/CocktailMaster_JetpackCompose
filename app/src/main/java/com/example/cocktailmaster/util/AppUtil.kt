package com.example.cocktailmaster.util

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.flow.MutableStateFlow

class AppUtil {
    companion object {
        fun checkNetworkConnection(context: Context, status: MutableStateFlow<Boolean>) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: android.net.Network) {
                    super.onAvailable(network)
                    status.value = true
                    println("network status changed ${status.value}")
                }

                override fun onLost(network: android.net.Network) {
                    super.onLost(network)
                    status.value = false
                    println("network status changed ${status.value}")
                }
            })
            status.value = activeNetwork != null && activeNetwork.isConnected
        }
    }
}
