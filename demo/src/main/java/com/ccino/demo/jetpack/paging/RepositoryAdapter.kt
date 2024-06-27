package com.ccino.demo.jetpack.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ccino.demo.R

class RepositoryAdapter(private val context: Context): PagingDataAdapter<RepositoryItem, RepositoryAdapter.ViewHolder>(COMPARATOR) {

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<RepositoryItem>() {
            override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.tvName.text = it.name
            holder.tvDesc.text = it.description
            holder.tvStar.text = it.stargazers_count.toString()
        }

        holder.llItem.setOnClickListener {

//            val intent = Intent(context,CommonWebActivity::class.java)
//            intent.putExtra("url",item?.htmlUrl)
//            context.startActivity(intent)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.page_item_repository, parent, false)
        return ViewHolder(view)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        val tvStar: TextView = itemView.findViewById(R.id.tv_star)
        val llItem: LinearLayout = itemView.findViewById(R.id.ll_item)
    }
}
