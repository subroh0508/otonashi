package net.subroh0508.sparkt.androidapp

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.util.Log.v
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.subroh0508.sparkt.R
import net.subroh0508.sparkt.core.vocabulary.common.CommonPrefix
import net.subroh0508.sparkt.core.SparqlQuery
import net.subroh0508.sparkt.core.SparqlQuery.Builder.endpoint
import net.subroh0508.sparkt.core.SparqlQuery.Builder.prefixes
import net.subroh0508.sparkt.core.extensions.get
import net.subroh0508.sparkt.core.operators.functions.contains
import net.subroh0508.sparkt.core.operators.functions.replace
import net.subroh0508.sparkt.core.operators.functions.str
import net.subroh0508.sparkt.core.vocabulary.IriVocabulary
import net.subroh0508.sparkt.core.vocabulary.common.Rdf
import net.subroh0508.sparkt.core.vocabulary.common.rdf
import net.subroh0508.sparkt.vocabraries.imasparql.*
import net.subroh0508.sparkt.vocabraries.schema.Schema
import net.subroh0508.sparkt.vocabraries.schema.schema
import java.net.URLDecoder
import java.util.Locale.filter

class MainActivity : AppCompatActivity() {
    private val client: SparqlQuery by lazy {
        SparqlQuery.Builder {
            endpoint("https://sparql.crssnky.xyz/spql/imas/query")
            prefixes(Rdf.Prefix, Schema.Prefix, ImasparqlPrefix.IMAS)
            install(Rdf, Schema, ImasparqlClass, ImasparqlProperty)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        sendRequest.setOnClickListener { sendRequest() }
    }

    private fun sendRequest() {
        val query = client.where {
            v("s") be {
                rdf.type to imasC.idol and
                schema.name to v("name") and
                imasP.title to v("title") and
                schema.memberOf to v("unit_url")
            }
            filter {
                contains(v("title"), "CinderellaGirls")
            }
            v("unit_url") be {
                rdf.type to imasC.unit and
                schema.name to v("unit_name")
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

        Log.d("query", URLDecoder.decode(query.toString(), "UTF-8"))
        GlobalScope.launch {
            val result = KtorClient.get(query.toString(), ImasResult::class)
            result.forEach {
                Log.d("result", Json.stringify(ImasResult.serializer(), it))
            }
        }
    }
}
