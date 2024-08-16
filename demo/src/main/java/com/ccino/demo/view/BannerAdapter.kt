package com.ccino.demo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccino.demo.databinding.SqBannerItemBinding

class BannerAdapter() : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = SqBannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {

        holder.bind(position.toString(), position)
    }

    override fun getItemCount(): Int {
        return 5
    }

    class BannerViewHolder(private val binding: SqBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String, pos: Int) {
            binding.root.tag = data
            binding.imgView.text = data
        }
    }
}
