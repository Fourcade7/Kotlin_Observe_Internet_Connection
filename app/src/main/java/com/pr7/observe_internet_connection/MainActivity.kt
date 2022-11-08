package com.pr7.observe_internet_connection

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData

class MainActivity : AppCompatActivity() {
    val livedata=MutableLiveData<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        livedata.observe(this,{
            if (it){
                Log.d("PR77777", "onCreate: Internet YES")
            } else{
                Log.d("PR77777", "onCreate: Internet NO")
            }
        })
        observeinternetConnection()




    }

    fun observeinternetConnection(){

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                livedata.postValue(true)
            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
               livedata.postValue(false)
            }
        }
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)


    }
}