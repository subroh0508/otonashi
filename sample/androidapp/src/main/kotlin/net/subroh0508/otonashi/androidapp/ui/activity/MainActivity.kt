package net.subroh0508.otonashi.androidapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import net.subroh0508.otonashi.R
import net.subroh0508.otonashi.androidapp.ui.fragment.ImasSearchFragment
import net.subroh0508.otonashi.androidapp.ui.view.TabLayoutMediator
import net.subroh0508.otonashi.androidapp.ui.viewmodel.EventViewModel
import net.subroh0508.otonashi.core.Kotori
import net.subroh0508.otonashi.core.Otonashi
import net.subroh0508.otonashi.vocabularies.foaf.FoafPrefix
import net.subroh0508.otonashi.vocabularies.foaf.foafVocabularies
import net.subroh0508.otonashi.vocabularies.imasparql.ImasparqlPrefix
import net.subroh0508.otonashi.vocabularies.imasparql.imasparqlVocabularies
import net.subroh0508.otonashi.vocabularies.schema.SchemaPrefix
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

    private enum class Tag(val id: Long, val displayName: String) {
        IMAS_KOTLINX_SERIALIZATION(0L, "imas(kotlinx)")
    }
}
