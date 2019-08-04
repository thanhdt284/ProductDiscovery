package com.android.productdiscovery.ui.detail

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.android.productdiscovery.R
import com.android.productdiscovery.domain.remote.pojo.response.AttributeGroup
import kotlinx.android.synthetic.main.item_spec_layout.view.*

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class SpecAdapter(private var listItem: List<AttributeGroup> = mutableListOf()) :
        RecyclerView.Adapter<SpecAdapter.AttributeGroupVH>() {

    fun setData(list: List<AttributeGroup>) {
        listItem = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributeGroupVH {
        val inflater = LayoutInflater.from(parent.context)
        return AttributeGroupVH(inflater.inflate(R.layout.item_spec_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: AttributeGroupVH, position: Int) {
        holder.bind(listItem[position])
    }

    inner class AttributeGroupVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { }
        }

        fun bind(item: AttributeGroup) {
            itemView.apply {
                tvSpecName.text = item.name
                tvSpecInfo.text = item.value
                if (adapterPosition % 2 != 0) {
                    ctnRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    when (adapterPosition) {
                        0             -> ctnRoot.setBackgroundResource(R.drawable.bg_pale_grey_corner_top)
                        itemCount - 1 -> ctnRoot.setBackgroundResource(R.drawable.bg_pale_grey_corner_bottom)
                        else          -> ctnRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.paleGrey))
                    }
                }
            }
        }

    }
}