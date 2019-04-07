package net.subroh0508.otonashi.androidapp.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.subroh0508.otonashi.R
import net.subroh0508.otonashi.androidapp.KtorClient
import net.subroh0508.otonashi.androidapp.model.ImasResult
import net.subroh0508.otonashi.androidapp.ui.fragment.ImasSearchFragment
import net.subroh0508.otonashi.androidapp.ui.view.TabLayoutMediator
import net.subroh0508.otonashi.androidapp.ui.viewmodel.EventViewModel
import net.subroh0508.otonashi.core.Kotori
import net.subroh0508.otonashi.core.Otonashi
import net.subroh0508.otonashi.core.operators.functions.contains
import net.subroh0508.otonashi.core.operators.functions.replace
import net.subroh0508.otonashi.core.operators.functions.str
import net.subroh0508.otonashi.core.vocabulary.common.rdfP
import net.subroh0508.otonashi.vocabularies.foaf.FoafPrefix
import net.subroh0508.otonashi.vocabularies.foaf.foafVocabularies
import net.subroh0508.otonashi.vocabularies.imasparql.ImasparqlPrefix
import net.subroh0508.otonashi.vocabularies.imasparql.imasC
import net.subroh0508.otonashi.vocabularies.imasparql.imasP
import net.subroh0508.otonashi.vocabularies.imasparql.imasparqlVocabularies
import net.subroh0508.otonashi.vocabularies.schema.SchemaPrefix
import net.subroh0508.otonashi.vocabularies.schema.schemaP
import net.subroh0508.otonashi.vocabularies.schema.schemaVocabularies

class MainActivity : AppCompatActivity() {
    private val kotori: Kotori
        get() = Otonashi.Study {
            destination("https://sparql.crssnky.xyz/spql/imas/query")
            reminds(SchemaPrefix.SCHEMA, FoafPrefix.FOAF, ImasparqlPrefix.IMAS)
            buildsUp(*schemaVocabularies, *foafVocabularies, *imasparqlVocabularies)
        }

    private val viewModel: EventViewModel by lazy {
        ViewModelProviders.of(this)[EventViewModel::class.java]
    }

    private val viewPagerAdapter: RecyclerView.Adapter<*> by lazy {
        object : FragmentStateAdapter(this) {
            override fun getItem(position: Int): Fragment = when (Tag.values()[position]) {
                Tag.IMAS_KOTLINX_SERIALIZATION -> ImasSearchFragment()
            }

            override fun getItemCount() = Tag.values().size
            override fun getItemId(position: Int): Long = Tag.values()[position].id
            override fun containsItem(itemId: Long): Boolean = Tag.values().find { it.id == itemId } != null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = viewPagerAdapter
        searchConditions.setOnClickListener {
            viewModel.openDialog()
        }

        TabLayoutMediator(tabs, viewPager, object : TabLayoutMediator.OnConfigureTabCallback {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = Tag.values()[position].displayName
            }
        }).attach()
    }

    private fun sendRequest() {
        val query = kotori.where {
            v("s") be {
                rdfP.type to imasC.idol and
                schemaP.name to v("name") and
                imasP.title to v("title") and
                schemaP.memberOf to v("unit_url")
            }
            filter {
                contains(v("title"), "CinderellaGirls")
            }
            v("unit_url") be {
                rdfP.type to imasC.unit and
                schemaP.name to v("unit_name")
            }
        }.select {
            replace(
                str(v("s")),
                """https://sparql.crssnky.xyz/imasrdf/RDFs/detail/""",
                ""
            ) `as` v("id")
            groupConcat(v("unit_name"), ",") `as` v("unit_names")

            + v("id") + v("name") + v("unit_names")
        }.groupBy { + v("s") + v("name") }.limit(100)

        Log.d("query", query.urlDecode())
        GlobalScope.launch {
            val results = KtorClient.get(query.toString(), ImasResult::class)
            results.forEach {
                Log.d("result", Json.stringify(ImasResult.serializer(), it))
            }
        }
    }

    private enum class Tag(val id: Long, val displayName: String) {
        IMAS_KOTLINX_SERIALIZATION(0L, "imas(kotlinx)")
    }
}
