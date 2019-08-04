package com.android.productdiscovery.data.manager

import androidx.lifecycle.MutableLiveData
import com.android.productdiscovery.domain.remote.pojo.response.CartItem
import com.android.productdiscovery.domain.remote.pojo.response.Product

/**
 * @author ThanhDT
 * @since 2019-08-02
 */
object CartManager {

    private var itemMap: MutableMap<String, CartItem> = mutableMapOf()

    var cartCountData = MutableLiveData<Int>()

    init {
        cartCountData.postValue(0)
    }

    /**
     * Add item to cart
     * @return true if add successfully
     */
    fun addItemToCart(product: Product): Boolean {
        if (itemMap[product.sku] != null) {
            itemMap[product.sku]?.let {
                it.quantity += 1
                return true
            }
        } else {
            itemMap[product.sku] = CartItem(product.price.sellPrice, 1)
            cartCountData.postValue(itemMap.size)
            return true
        }

        return false
    }

    /**
     * Remove item from cart
     * @return true if remove successfully
     */
    fun removeItemFromCart(product: Product): Boolean {
        if (itemMap[product.sku] != null) {
            itemMap[product.sku]?.let {
                if (it.quantity <= 1) {
                    itemMap.remove(product.sku)
                    cartCountData.postValue(itemMap.size)
                } else {
                    it.quantity -= 1
                }

                return true
            }
        }

        return false
    }

    /**
     * Get current cart data of product with specific sku id
     * @param sku: sku id of the product
     */
    fun getCartItem(sku: String): CartItem? {
        return itemMap[sku]
    }
}