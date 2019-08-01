package com.android.productdiscovery.ui.widget

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.android.productdiscovery.R

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class ProgressDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(@StringRes msgId: Int): ProgressDialogFragment {
            val fragment = ProgressDialogFragment()
            val args = Bundle()
            args.putInt("msgId", msgId)

            fragment.arguments = args

            return fragment
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val msgId = arguments?.getInt("msgId")
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_progress, null)
        msgId?.let {
            view?.findViewById<TextView>(R.id.tvMessage)?.setText(it)
        }

        val builder = AlertDialog.Builder(requireContext()).apply {
            isCancelable = false
            setView(view)
        }
        return builder.create()
    }
}