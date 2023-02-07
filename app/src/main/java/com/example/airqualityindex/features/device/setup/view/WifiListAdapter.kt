package com.example.airqualityindex.features.device.setup.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityindex.databinding.ItemWifiListBinding
import com.example.airqualityindex.shared.models.WifiInfo

class WifiListAdapter(
    private var wifiInfoList: List<WifiInfo>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<WifiListAdapter.ViewHolder>() {
    private lateinit var binding: ItemWifiListBinding

    interface OnItemClickListener {
        fun onItemClick(position: Int, wifiInfoList: List<WifiInfo>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.binding =
            ItemWifiListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(this.binding, this.listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wifiInfo = this.wifiInfoList[position]
        holder.bind(wifiInfo, this.wifiInfoList)
    }

    override fun getItemCount(): Int {
        return this.wifiInfoList.size
    }

    inner class ViewHolder(
        private val binding: ItemWifiListBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var wifiInfoList: List<WifiInfo>

        init {
            this.itemView.setOnClickListener(this)
        }

        fun bind(wifiInfo: WifiInfo, wifiInfoList: List<WifiInfo>) {
            this.binding.wifiName.text = wifiInfo.ssid
            this.wifiInfoList = wifiInfoList
        }

        override fun onClick(v: View?) {
            val clickPosition = adapterPosition
            if (clickPosition != RecyclerView.NO_POSITION)
                this.listener.onItemClick(clickPosition, this. wifiInfoList)
        }
    }
}