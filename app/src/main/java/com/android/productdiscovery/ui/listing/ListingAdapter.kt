package com.android.productdiscovery.ui.listing

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.productdiscovery.R
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.utils.CurrencyUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_listing_layout.view.*


/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class ListingAdapter(private var listItem: MutableList<Product> = mutableListOf()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val PRODUCT_ITEM = 0
        const val LOADING_ITEM = 1
    }

    fun addLoadingView() {
        listItem.add(Product(name = "loading", url = "loading"))
        notifyItemInserted(listItem.size - 1)
    }

    fun removeLoadingView() {
        val index = listItem.size - 1
        if (index in 0 until listItem.size) {
            listItem.removeAt(index)
            notifyItemRemoved(listItem.size)
        }
    }

    fun addData(list: List<Product>) {
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    fun setData(list: List<Product>) {
        listItem.apply {
            clear()
            addAll(list)
        }

        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (listItem[position].name == "loading" && listItem[position].url == "loading") {
            return LOADING_ITEM
        }

        return PRODUCT_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == PRODUCT_ITEM) {
            ProductVH(inflater.inflate(R.layout.item_listing_layout, parent, false))
        } else {
            LoadingVH(inflater.inflate(R.layout.item_loading_layout, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductVH) {
            holder.bind(listItem[position])
        }
    }

    inner class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { }
        }

        fun bind(item: Product) {
            itemView.apply {
                Glide.with(context)
                        .load(item.images[0].url)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_item_placeholder))
                        .into(ivItem)

                tvItemName.text = item.name



                tvItemPrice.text = CurrencyUtils.formatCurrency(item.price.sellPrice)

                tvItemOldPrice.paintFlags = tvItemOldPrice.paintFlags or STRIKE_THRU_TEXT_FLAG
                if (item.price.supplierSalePrice != item.price.sellPrice) {
                    tvItemOldPrice.visibility = View.VISIBLE
                    tvDiscount.visibility = View.VISIBLE

                    tvItemOldPrice.text = CurrencyUtils.formatCurrency(item.price.supplierSalePrice, withCurrency = false)
                    var percent = (item.price.supplierSalePrice - item.price.sellPrice) * 100 / item.price.supplierSalePrice
                    if (percent < 1) {
                        percent = 1.0
                    }
                    tvDiscount.text = context.getString(R.string.format_discount_percent, percent.toInt())
                } else {
                    tvItemOldPrice.visibility = View.GONE
                    tvDiscount.visibility = View.GONE
                }
            }
        }

    }
}