package com.example.airqualityindex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityindex.databinding.ItemAQIListBinding

class AQIListAdapter(
    private var siteNameList: List<String>,
    private val clickListener: OnItemClickListener?
) : RecyclerView.Adapter<AQIListAdapter.ViewHolder>() {
    private val TAG = AQIListAdapter::class.java.simpleName

    private lateinit var binding: ItemAQIListBinding

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(
        private val binding: ItemAQIListBinding,
        private val listener: OnItemClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemBackground.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

        fun bind(siteName: String) {
            this.binding.siteName = siteName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.binding =
            ItemAQIListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(this.binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = this.siteNameList[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int {
        return this.siteNameList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: List<String>) {
        this.siteNameList = filterList
        notifyDataSetChanged()
    }
}