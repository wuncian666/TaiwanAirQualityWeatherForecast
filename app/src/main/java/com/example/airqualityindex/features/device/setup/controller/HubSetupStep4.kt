package com.example.airqualityindex.features.device.setup.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentHubSetupStep4Binding
import com.example.airqualityindex.features.device.setup.services.MqttEventListener
import com.example.airqualityindex.features.device.setup.view.WifiListAdapter
import com.example.airqualityindex.features.device.setup.viewmodels.HubViewModel
import com.example.airqualityindex.shared.constant.MqttConfig
import com.example.airqualityindex.shared.models.WifiInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONArray
import org.json.JSONObject
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit

class HubSetupStep4 : Fragment(), WifiListAdapter.OnItemClickListener, MqttEventListener {
    private val viewModel: HubViewModel = get()

    private lateinit var binding: FragmentHubSetupStep4Binding
    private lateinit var selectedWifiInfo: WifiInfo

    private var scanWifiTimer: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentHubSetupStep4Binding.inflate(inflater, container, false)
        this.binding.listener = this

        this.binding.wifiDialog.visibility = View.INVISIBLE

        viewModel.setMqttUseCase(this)
        viewModel.mqttConnect()

        return this.binding.root
    }

    fun onButtonClick(view: View) {
        when (view.id) {
            R.id.btn_cancel -> {
                this.binding.wifiDialog.visibility = View.INVISIBLE
            }
            R.id.btn_confirm -> {
                val wifiPassword = this.binding.editPassword.text

                if (wifiPassword.isNullOrEmpty()) {

                } else {
                    this.selectedWifiInfo.password = wifiPassword.toString()
                    val action =
                        HubSetupStep4Directions.actionHubSetupStep4ToHubSetupStep5(selectedWifiInfo)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onConnectFail(error: Throwable?) {
        mqttReconnect()
    }

    private fun mqttReconnect() {
        Observable.interval(0, 5, TimeUnit.SECONDS)
            .take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { viewModel.mqttConnect() }
            .subscribe()
    }

    override fun onConnected() {
        this.scanWifi()
    }

    private fun scanWifi() {
        this.scanWifiTimer?.dispose()

        this.scanWifiTimer = Observable.interval(5, 10, TimeUnit.SECONDS)
            .doOnNext {
                viewModel.mqttPublish(MqttConfig.PUBLISH_TOPIC, getJsonWifiScanEvent())
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun getJsonWifiScanEvent(): String {
        val json = JSONObject()
        json.put("EVENT", "WIFI_SCAN")
        return json.toString()
    }

    private fun stopScanWifi() {
        this.scanWifiTimer!!.dispose()
    }

    override fun onMessageArrived(message: MqttMessage?) {
        val wifiInfoList = turnJsonArrayToList(message)
        this.setupRecyclerView(wifiInfoList)
    }

    private fun turnJsonArrayToList(message: MqttMessage?): List<WifiInfo> {
        val wifiInfoList: ArrayList<WifiInfo> = ArrayList()
        val jsonObject = JSONObject(message.toString())
        val wifiInfoJsonArray = jsonObject.getJSONObject("Content").getJSONArray("SSID")
        for (i in 0 until wifiInfoJsonArray.length()) {
            val wifiInfoArray = JSONArray(wifiInfoJsonArray[i].toString())
            val ssid = wifiInfoArray[0].toString()
            val encrypt = wifiInfoArray[1].toString()
            wifiInfoList.add(WifiInfo(ssid, encrypt, ""))
        }
        return wifiInfoList
    }

    private fun setupRecyclerView(wifiInfoList: List<WifiInfo>) {
        val wifiListAdapter = WifiListAdapter(wifiInfoList, this)
        this.binding.recyclerview.adapter = wifiListAdapter
        this.binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        this.binding.recyclerview.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int, wifiInfoList: List<WifiInfo>) {
        this.stopScanWifi()
        this.binding.wifiDialog.visibility = View.VISIBLE
        this.selectedWifiInfo = wifiInfoList[position]
    }
}