package com.android.productdiscovery.ui.detail

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.productdiscovery.R
import com.android.productdiscovery.domain.remote.pojo.response.Image
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_image_layout.view.*

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class ImageAdapter(private var listItem: List<Image> = mutableListOf()) :
    RecyclerView.Adapter<ImageAdapter.ImageVH>() {

    fun setData(list: List<Image>) {
        listItem = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
        val inflater = LayoutInflater.from(parent.context)
        return ImageVH(inflater.inflate(R.layout.item_image_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ImageVH, position: Int) {
        holder.bind(listItem[position])
    }

    inner class ImageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { }
        }

        fun bind(item: Image) {
            itemView.apply {
                Glide.with(context)
                    .load(item.url)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_item_placeholder))
                    .into(image)
            }
        }

    }
}