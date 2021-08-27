package com.example.cats.broadcastReceiver

//private val batteryBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        if (intent?.action == "android.intent.action.BATTERY_CHANGED") {
//            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
//            batteryState = level
//        }
//        if (intent?.action == "android.intent.action.ACTION_POWER_CONNECTED") {
//            powerPlugged = true
//        }
//        if (intent?.action == "android.intent.action.ACTION_POWER_DISCONNECTED") {
//            powerPlugged = false
//        }
//
//        if (!isAlertVisible && batteryState <= 20 && !powerPlugged) {
//            Log.d("isAlertVisible", "$isAlertVisible")
//            showAlert(1)
//            isAlertVisible = true
//            Log.d("isAlertVisible2", "$isAlertVisible")
//        }
//        if (powerPlugged && isAlertVisible) {
//            showAlert(0)
//            Log.d("isAlertVisible3", "$isAlertVisible")
//            isAlertVisible = false
//            Log.d("isAlertVisible4", "$isAlertVisible")
//        }
//    }
//}