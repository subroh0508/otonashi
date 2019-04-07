package net.subroh0508.otonashi.androidapp.ui.view

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_footer.view.*
import net.subroh0508.otonashi.R

class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        const val ITEM_TYPE = -1
    }

    private val context: Context = itemView.context
    private val message: TextView = itemView.message

    init {
        message.text = context.getString(R.string.search_result_summary, 0)
    }

    fun updateSummary(n: Int) {
        message.text = context.getString(R.string.search_result_summary, n)
    }
}