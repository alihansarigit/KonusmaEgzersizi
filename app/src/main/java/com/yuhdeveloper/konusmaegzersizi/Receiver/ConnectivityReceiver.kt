package com.yuhdeveloper.konusmaegzersizi.Receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo


/** Author : http://devdeeds.com
 *  Project : Sample Project - Internet status checking
 *  Date : 24 Feb 2018*/

class ConnectivityReceiver(context:Context) : BroadcastReceiver() {


    private var mManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var mListeners: MutableList<NetworkStateReceiverListener> = ArrayList()
    private var mConnected: Boolean = false

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null || intent.extras == null)
            return

        if (checkStateChanged()) notifyStateToAll()
    }

    private fun checkStateChanged(): Boolean {
        val prev = mConnected
        val activeNetwork = mManager.activeNetworkInfo
        mConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return prev != mConnected
    }

    private fun notifyStateToAll() {
        for (listener in mListeners) {
            notifyState(listener)
        }
    }

    private fun notifyState(listener: NetworkStateReceiverListener?) {
        if (listener != null) {
            if (mConnected)
                listener.onNetworkAvailable()
            else
                listener.onNetworkUnavailable()
        }
    }

    //call this method to add a listener
    fun addListener(l: NetworkStateReceiverListener) {
        mListeners.add(l)
        notifyState(l)
    }

    //call this method to remove a listener
    fun removeListener(l: NetworkStateReceiverListener) {
        mListeners.remove(l)
    }

    interface NetworkStateReceiverListener {
        fun onNetworkAvailable()

        fun onNetworkUnavailable()
    }

    init {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(this, intentFilter)
        checkStateChanged()
    }

}

//    override fun onReceive(context: Context, arg1: Intent) {
//        if (connectivityReceiverListener != null) {
//            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnectedOrConnecting(context))
//        }
//    }
//
//    private fun isConnectedOrConnecting(context: Context): Boolean {
//        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = connMgr.activeNetworkInfo
//        return networkInfo != null && networkInfo.isConnected
//    }
//
//    interface ConnectivityReceiverListener {
//        fun onNetworkConnectionChanged(isConnected: Boolean)
//    }
//
//    companion object {
//        var connectivityReceiverListener: ConnectivityReceiverListener? = null
//    }
//}