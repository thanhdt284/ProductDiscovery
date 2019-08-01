package com.android.productdiscovery.domain.remote.pojo.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author ThanhDT
 * @since 2019-08-01
 */

data class Product(
        @Expose
        @SerializedName("allActiveFlashSales")
        val allActiveFlashSales: List<Any> = listOf(),
        @Expose
        @SerializedName("attributeGroups")
        val attributeGroups: List<AttributeGroup> = listOf(),
        @Expose
        @SerializedName("attributeSet")
        val attributeSet: AttributeSet = AttributeSet(),
        @Expose
        @SerializedName("attributes")
        val attributes: List<Attribute> = listOf(),
        @Expose
        @SerializedName("brand")
        val brand: Brand = Brand(),
        @Expose
        @SerializedName("categories")
        val categories: List<Category> = listOf(),
        @Expose
        @SerializedName("color")
        val color: Color = Color(),
        @Expose
        @SerializedName("createdAt")
        val createdAt: String = "",
        @Expose
        @SerializedName("displayName")
        val displayName: String = "",
        @Expose
        @SerializedName("flashSales")
        val flashSales: List<Any> = listOf(),
        @Expose
        @SerializedName("images")
        val images: List<Image> = listOf(),
        @Expose
        @SerializedName("magentoId")
        val magentoId: Int = 0,
        @Expose
        @SerializedName("name")
        val name: String = "",
        @Expose
        @SerializedName("price")
        val price: Price = Price(),
        @Expose
        @SerializedName("productLine")
        val productLine: ProductLine = ProductLine(),
        @Expose
        @SerializedName("promotionPrices")
        val promotionPrices: List<Any> = listOf(),
        @Expose
        @SerializedName("promotions")
        val promotions: List<Any> = listOf(),
        @Expose
        @SerializedName("rating")
        val rating: Rating = Rating(),
        @Expose
        @SerializedName("seoInfo")
        val seoInfo: SeoInfo = SeoInfo(),
        @Expose
        @SerializedName("sku")
        val sku: String = "",
        @Expose
        @SerializedName("status")
        val status: Status = Status(),
        @Expose
        @SerializedName("stocks")
        val stocks: List<Any> = listOf(),
        @Expose
        @SerializedName("tags")
        val tags: List<Any> = listOf(),
        @Expose
        @SerializedName("taxOut")
        val taxOut: Double = 0.0,
        @Expose
        @SerializedName("totalAvailable")
        val totalAvailable: Double? = 0.0,
        @Expose
        @SerializedName("url")
        val url: String = "",
        @Expose
        @SerializedName("warranty")
        val warranty: Warranty = Warranty()
)

data class ProductLine(
        @Expose
        @SerializedName("code")
        val code: String = "",
        @Expose
        @SerializedName("name")
        val name: String = ""
)

data class AttributeSet(
        @Expose
        @SerializedName("id")
        val id: Int = 0,
        @Expose
        @SerializedName("name")
        val name: String = ""
)

data class Brand(
        @Expose
        @SerializedName("code")
        val code: String = "",
        @Expose
        @SerializedName("name")
        val name: String = ""
)

data class Attribute(
        @Expose
        @SerializedName("code")
        val code: String = "",
        @Expose
        @SerializedName("values")
        val values: List<String> = listOf()
)

data class Color(
        @Expose
        @SerializedName("code")
        val code: String? = "",
        @Expose
        @SerializedName("name")
        val name: String? = ""
)

data class Category(
        @Expose
        @SerializedName("code")
        val code: String = "",
        @Expose
        @SerializedName("id")
        val id: Int = 0,
        @Expose
        @SerializedName("level")
        val level: Int = 0,
        @Expose
        @SerializedName("name")
        val name: String = "",
        @Expose
        @SerializedName("parentId")
        val parentId: Int = 0
)

data class Price(
        @Expose
        @SerializedName("sellPrice")
        val sellPrice: Double = 0.0,
        @Expose
        @SerializedName("supplierSalePrice")
        val supplierSalePrice: Double = 0.0
)

data class Warranty(
        @Expose
        @SerializedName("description")
        val description: String = "",
        @Expose
        @SerializedName("months")
        val months: Int = 0
)

data class Status(
        @Expose
        @SerializedName("publish")
        val publish: Boolean = true,
        @Expose
        @SerializedName("sale")
        val sale: String = ""
)

data class AttributeGroup(
        @Expose
        @SerializedName("id")
        val id: Int? = null,
        @Expose
        @SerializedName("name")
        val name: String = "",
        @Expose
        @SerializedName("parentId")
        val parentId: Int = 0,
        @Expose
        @SerializedName("priority")
        val priority: Int = 0,
        @Expose
        @SerializedName("value")
        val value: String = ""
)

data class SeoInfo(
        @Expose
        @SerializedName("description")
        val description: String? = "",
        @Expose
        @SerializedName("metaDescription")
        val metaDescription: String? = "",
        @Expose
        @SerializedName("metaKeyword")
        val metaKeyword: String? = "",
        @Expose
        @SerializedName("metaTitle")
        val metaTitle: String? = "",
        @Expose
        @SerializedName("shortDescription")
        val shortDescription: String = ""
)

data class Rating(
        @Expose
        @SerializedName("averagePoint")
        val averagePoint: Double? = 0.0,
        @Expose
        @SerializedName("voteCount")
        val voteCount: Int = 0
)

data class Image(
        @Expose
        @SerializedName("priority")
        val priority: Int = 0,
        @Expose
        @SerializedName("url")
        val url: String = ""
)