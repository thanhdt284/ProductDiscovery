package com.android.productdiscovery.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import timber.log.Timber

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class ProductDetailPagerAdapter(fragment: Fragment, private val product: String) : FragmentStateAdapter(fragment) {

//    override fun getItem(position: Int): Fragment {
//        return when (position) {
//            0 -> DescriptionFragment.getInstance(product)
//            1 -> DescriptionFragment.getInstance(product)
//            else -> DescriptionFragment.getInstance(product)
////            0    -> DescriptionFragment.getInstance(product)
////            1    -> SpecFragment.getInstance(product)
////            else -> PriceComparisonFragment()
//        }
//    }
//
//    override fun getCount(): Int = NUMB_OF_DETAIL_TAB
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return when (position) {
//            0 -> context.getString(R.string.product_description)
//            1 -> context.getString(R.string.technical_spec)
//            else -> context.getString(R.string.price_comparison)
//        }
//    }


    override fun createFragment(position: Int): Fragment {
        Timber.e("position = $position")
        return when (position) {
            0    -> DescriptionFragment.getInstance(product)
            1    -> SpecFragment.getInstance(product)
            else -> PriceComparisonFragment()
//            0    -> DescriptionFragment.getInstance(product)
//            1    -> PriceComparisonFragment()
//            else -> PriceComparisonFragment()
        }
    }

    override fun getItemCount(): Int = NUMB_OF_DETAIL_TAB

    companion object {
        const val NUMB_OF_DETAIL_TAB = 3
    }
}