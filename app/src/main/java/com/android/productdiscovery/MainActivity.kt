package com.android.productdiscovery

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShow.setOnClickListener {
            if (isShowing) {
                val params = container.layoutParams
                params.height = dpToPx(this, 200f)
                container.layoutParams = params
            } else {
                val params = container.layoutParams
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                container.layoutParams = params
            }

            isShowing = !isShowing
        }
    }

    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }
}
