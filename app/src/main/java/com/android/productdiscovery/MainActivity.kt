package com.android.productdiscovery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.productdiscovery.utils.DisplayUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShow.setOnClickListener {
            if (isShowing) {
                val params = container.layoutParams
                params.height = DisplayUtils.dpToPx(this, 200f)
                container.layoutParams = params
            } else {
                val params = container.layoutParams
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                container.layoutParams = params
            }

            isShowing = !isShowing
        }
    }
}
