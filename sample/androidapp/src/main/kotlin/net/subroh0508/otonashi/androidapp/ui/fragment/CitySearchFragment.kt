package net.subroh0508.otonashi.androidapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.listitem_city_search_result.view.*
import kotlinx.coroutines.launch
import net.subroh0508.otonashi.R
import net.subroh0508.otonashi.androidapp.KtorClient
import net.subroh0508.otonashi.androidapp.model.CityResult
import net.subroh0508.otonashi.androidapp.ui.Tag
import net.subroh0508.otonashi.androidapp.ui.view.FooterViewHolder
import net.subroh0508.otonashi.androidapp.ui.viewmodel.CityViewModel
import net.subroh0508.otonashi.androidapp.ui.viewmodel.EventViewModel

class CitySearchFragment : SearchFragment() {
    private val eventViewModel: EventViewModel by lazy {
        ViewModelProviders.of(requireActivity())[EventViewModel::class.java]
    }
    private val cityViewModel: CityViewModel by lazy {
        ViewModelProviders.of(this)[CityViewModel::class.java]
    }

    private val adapter: ResultAdapter by lazy { ResultAdapter(LayoutInflater.from(requireContext())) }
    private val layoutManager: LinearLayoutManager by lazy { LinearLayoutManager(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (results) {
            adapter = this@CitySearchFragment.adapter
            layoutManager = this@CitySearchFragment.layoutManager
        }

        eventViewModel.openDialogEvent.observe(this, Observer {
            if (it != Tag.CITY_KOTLINX_SERIALIZATION) return@Observer

            CitySearchConditionsDialog(this).show(requireFragmentManager(), "city_search")
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_CANCELED) return

        launch {
            val query = cityViewModel.buildQuery()
            Log.d("rawQuery", query.toString())
            Log.d("query", query.urlDecode())

            val response = KtorClient.get(query.toString(), CityResult::class)

            adapter.onFetchedResults(response.results())
        }
    }

    class ResultAdapter(
        private val inflater: LayoutInflater,
        private var results: List<CityResult> = listOf()
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        init {
            setHasStableIds(true)
        }

        fun onFetchedResults(results: List<CityResult>) {
            this.results = results
            notifyDataSetChanged()
        }

        override fun getItemCount() = results.size + 1
        override fun getItemId(position: Int): Long = when (position == results.size) {
            true -> 0L
            false -> results[position].hashCode().toLong()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
            FooterViewHolder.ITEM_TYPE -> FooterViewHolder(inflater.inflate(R.layout.listitem_footer, parent, false))
            else -> ResultViewHolder(inflater.inflate(R.layout.listitem_city_search_result, parent, false))
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

        fun bindItem(item: CityResult) {
            with (itemView) {
                cityName.text = item.cityName
                prefectureName.text = item.prefectureName
            }
        }
    }
}