package com.example.feature_list.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.domain.model.User
import com.example.feature_list.R
import com.example.feature_list.databinding.CompInfoItemFavouriteListBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class FavouriteAdapter @Inject constructor(@ActivityContext private val context: Context) :
    RecyclerView.Adapter<FavouriteAdapter.MovieListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, item: User)
    }

    inner class MovieListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CompInfoItemFavouriteListBinding.bind(view)
    }

    private var onItemClickListener: OnItemClickListener? = null
    var datas = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.comp_info_item_favourite_list,
            parent, false
        )
        return MovieListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val data = datas[position]
        val glideOpt = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).fitCenter()
            .placeholder(com.example.commonui.R.drawable.ic_launcher_background)
            .error(com.example.commonui.R.drawable.ic_launcher_background)
        Glide.with(context)
            .load(data.avatarUrl)
            .apply(glideOpt)
            .thumbnail(0.1f)
            .into(holder.binding.ivThumbnail)
        holder.binding.apply {
            labelName.text = data.login
            labelCompany.text = data.company
            labelEmail.text = data.email
            labelLocation.text = data.location
        }

        holder.binding.itemUser.setOnClickListener {
            onItemClickListener?.onItemClick(it, datas[position])
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    fun reloadData(paramDatas: List<User>) {
        datas.clear()
        datas.addAll(paramDatas)
        notifyDataSetChanged()
    }

    fun addData(newData: List<User>) {
        val previousCount = datas.size
        datas.addAll(newData)
        notifyItemRangeInserted(previousCount, datas.size)
    }

    fun clearData() {
        datas.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}