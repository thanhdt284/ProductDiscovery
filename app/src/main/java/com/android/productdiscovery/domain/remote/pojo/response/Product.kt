package com.android.productdiscovery.domain.remote.pojo.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
@Entity(tableName = Product.TABLE_NAME)
data class Product(
        @Ignore
        @Expose
        @SerializedName("allActiveFlashSales")
        var allActiveFlashSales: List<Any> = listOf(),

        @Expose
        @SerializedName("attributeGroups")
        @ColumnInfo(name = ATTRIBUTE_GROUPS)
        var attributeGroups: List<AttributeGroup> = listOf(),

        @Ignore
        @Expose
        @SerializedName("attributeSet")
        var attributeSet: AttributeSet = AttributeSet(),

        @Ignore
        @Expose
        @SerializedName("attributes")
        var attributes: List<Attribute> = listOf(),

        @Ignore
        @Expose
        @SerializedName("brand")
        var brand: Brand = Brand(),

        @Ignore
        @Expose
        @SerializedName("categories")
        var categories: List<Category> = listOf(),

        @Ignore
        @Expose
        @SerializedName("color")
        var color: Color = Color(),

        @Ignore
        @Expose
        @SerializedName("createdAt")
        var createdAt: String = "",

        @Expose
        @SerializedName("displayName")
        @ColumnInfo(name = DISPLAY_NAME)
        var displayName: String = "",

        @Ignore
        @Expose
        @SerializedName("flashSales")
        var flashSales: List<Any> = listOf(),

        @Expose
        @SerializedName("images")
        @ColumnInfo(name = IMAGES)
        var images: List<Image> = listOf(),

        @Ignore
        @Expose
        @SerializedName("magentoId")
        var magentoId: Int = 0,

        @Expose
        @SerializedName("name")
        @ColumnInfo(name = NAME)
        var name: String = "",

        @Expose
        @SerializedName("price")
        @ColumnInfo(name = PRICE)
        var price: Price = Price(),

        @Ignore
        @Expose
        @SerializedName("productLine")
        var productLine: ProductLine = ProductLine(),

        @Ignore
        @Expose
        @SerializedName("promotionPrices")
        var promotionPrices: List<Any> = listOf(),

        @Ignore
        @Expose
        @SerializedName("promotions")
        var promotions: List<Any> = listOf(),

        @Ignore
        @Expose
        @SerializedName("rating")
        var rating: Rating = Rating(),

        @Expose
        @SerializedName("seoInfo")
        @ColumnInfo(name = SEO_INFO)
        var seoInfo: SeoInfo = SeoInfo(),

        @PrimaryKey
        @Expose
        @SerializedName("sku")
        @ColumnInfo(name = SKU)
        var sku: String = "",

        @Expose
        @SerializedName("status")
        @ColumnInfo(name = STATUS)
        var status: Status = Status(),

        @Ignore
        @Expose
        @SerializedName("stocks")
        var stocks: List<Any> = listOf(),

        @Ignore
        @Expose
        @SerializedName("tags")
        var tags: List<Any> = listOf(),

        @Ignore
        @Expose
        @SerializedName("taxOut")
        var taxOut: Double = 0.0,

        @Ignore
        @Expose
        @SerializedName("totalAvailable")
        var totalAvailable: Double? = 0.0,

        @Ignore
        @Expose
        @SerializedName("url")
        var url: String = "",

        @Ignore
        @Expose
        @SerializedName("warranty")
        var warranty: Warranty = Warranty()
) {
        companion object {
                const val TABLE_NAME = "product"
                const val SKU = "sku"
                const val ATTRIBUTE_GROUPS = "attributeGroups"
                const val PRICE = "price"
                const val STATUS = "status"
                const val SEO_INFO = "seoInfo"
                const val NAME = "name"
                const val DISPLAY_NAME = "displayName"
                const val IMAGES = "images"
        }
}

data class ProductLine(
        @Expose
        @SerializedName("code")
        var code: String = "",
        @Expose
        @SerializedName("name")
        var name: String = ""
)

data class AttributeSet(
        @Expose
        @SerializedName("id")
        var id: Int = 0,
        @Expose
        @SerializedName("name")
        var name: String = ""
)

data class Brand(
        @Expose
        @SerializedName("code")
        var code: String = "",
        @Expose
        @SerializedName("name")
        var name: String = ""
)

data class Attribute(
        @Expose
        @SerializedName("code")
        var code: String = "",
        @Expose
        @SerializedName("values")
        var values: List<Value> = listOf()
)

data class Value(
        @SerializedName("optionId")
        var optionId: Any,
        @SerializedName("value")
        var value: String
)

data class Color(
        @Expose
        @SerializedName("code")
        var code: String? = "",
        @Expose
        @SerializedName("name")
        var name: String? = ""
)

data class Category(
        @Expose
        @SerializedName("code")
        var code: String = "",
        @Expose
        @SerializedName("id")
        var id: Int = 0,
        @Expose
        @SerializedName("level")
        var level: Int = 0,
        @Expose
        @SerializedName("name")
        var name: String = "",
        @Expose
        @SerializedName("parentId")
        var parentId: Int = 0
)

data class Price(
        @Expose
        @SerializedName("sellPrice")
        var sellPrice: Double = 0.0,
        @Expose
        @SerializedName("supplierSalePrice")
        var supplierSalePrice: Double = 0.0
)

data class Warranty(
        @Expose
        @SerializedName("description")
        var description: String = "",
        @Expose
        @SerializedName("months")
        var months: Int = 0
)

data class Status(
        @Expose
        @SerializedName("publish")
        var publish: Boolean = true,
        @Expose
        @SerializedName("sale")
        var sale: String = ""
)

data class AttributeGroup(
        @Expose
        @SerializedName("id")
        var id: Int? = null,
        @Expose
        @SerializedName("name")
        var name: String = "",
        @Expose
        @SerializedName("parentId")
        var parentId: Int = 0,
        @Expose
        @SerializedName("priority")
        var priority: Int = 0,
        @Expose
        @SerializedName("value")
        var value: String = ""
)

data class SeoInfo(
        @Expose
        @SerializedName("description")
        var description: String? = "",
        @Expose
        @SerializedName("metaDescription")
        var metaDescription: String? = "",
        @Expose
        @SerializedName("metaKeyword")
        var metaKeyword: String? = "",
        @Expose
        @SerializedName("metaTitle")
        var metaTitle: String? = "",
        @Expose
        @SerializedName("shortDescription")
        var shortDescription: String = ""
)

data class Rating(
        @Expose
        @SerializedName("averagePoint")
        var averagePoint: Double? = 0.0,
        @Expose
        @SerializedName("voteCount")
        var voteCount: Int = 0
)

data class Image(
        @Expose
        @SerializedName("priority")
        var priority: Int = 0,
        @Expose
        @SerializedName("url")
        var url: String = ""
)