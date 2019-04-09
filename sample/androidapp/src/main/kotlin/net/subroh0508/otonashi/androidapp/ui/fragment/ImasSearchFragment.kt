package net.subroh0508.otonashi.androidapp.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.listitem_imas_search_result.view.*
import kotlinx.coroutines.launch
import net.subroh0508.otonashi.R
import net.subroh0508.otonashi.androidapp.KtorClient
import net.subroh0508.otonashi.androidapp.model.ImasResult
import net.subroh0508.otonashi.androidapp.ui.view.FooterViewHolder
import net.subroh0508.otonashi.androidapp.ui.viewmodel.EventViewModel
import net.subroh0508.otonashi.androidapp.ui.viewmodel.ImasViewModel
import java.time.format.DateTimeFormatter

class ImasSearchFragment : SearchFragment() {
    private val eventViewModel: EventViewModel by lazy {
        ViewModelProviders.of(requireActivity())[EventViewModel::class.java]
    }
    private val imasViewModel: ImasViewModel by lazy {
        ViewModelProviders.of(this)[ImasViewModel::class.java]
    }

    private val adapter: ResultAdapter by lazy { ResultAdapter(LayoutInflater.from(requireContext())) }
    private val layoutManager: LinearLayoutManager by lazy { LinearLayoutManager(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (results) {
            adapter = this@ImasSearchFragment.adapter
            layoutManager = this@ImasSearchFragment.layoutManager
        }

        eventViewModel.openDialogEvent.observe(this, Observer {
            ImasSearchConditionsDialog(this).show(requireFragmentManager(), "imas_search")
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_CANCELED) return

        launch {
            val query = imasViewModel.buildQuery(requireContext())
            Log.d("query", query.urlDecode())

            val response = KtorClient.get(query.toString(), ImasResult::class)

            adapter.onFetchedResults(response.results())
        }
    }

    class ResultAdapter(
        private val inflater: LayoutInflater,
        private var results: List<ImasResult> = listOf()
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        init {
            setHasStableIds(true)
        }

        fun onFetchedResults(results: List<ImasResult>) {
            this.results = results
            notifyDataSetChanged()
        }

        override fun getItemCount() = results.size + 1
        override fun getItemId(position: Int): Long = when (position == results.size) {
            true -> 0L
            false -> results[position].id.hashCode().toLong()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
            FooterViewHolder.ITEM_TYPE -> FooterViewHolder(inflater.inflate(R.layout.listitem_footer, parent, false))
            else -> ResultViewHolder(inflater.inflate(R.layout.listitem_imas_search_result, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is ResultViewHolder -> holder.bindItem(results[position])
                is FooterViewHolder -> holder.updateSummary(results.size)
            }
        }

        override fun getItemViewType(position: Int) = when(position == results.size) {
            true -> FooterViewHolder.ITEM_TYPE
            false -> ResultViewHolder.ITEM_TYPE
        }
    }

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            const val ITEM_TYPE = 1
        }

        fun bindItem(item: ImasResult) {
            with (itemView) {
                idolId.text = item.id
                idolName.text = item.name

                val birthDate = item.birthDate.format(DateTimeFormatter.ofPattern("M月d日"))
                val handedness = when (item.handedness) {
                    "right" -> "右"
                    "left" -> "左"
                    else -> "両"
                }
                profile.text = listOf(
                    "${birthDate}生", "${item.age}歳", item.birthPlace,
                    "${item.bloodType}型", handedness, item.threeSize
                ).joinToString(" / ")
                color.setBackgroundColor(item.color?.let { Color.parseColor(it) } ?: Color.LTGRAY)

                unitNames.removeAllViews()
                item.unitNames.split(",").sorted().forEachIndexed { i, unitName ->
                    val chip = Chip(context, null, R.style.Widget_MaterialComponents_Chip_Choice).apply {
                        text = unitName
                        setTextColor(ContextCompat.getColor(context, R.color.black))
                    }

                    unitNames.addView(chip)
                }
            }
        }
    }
}