package net.subroh0508.sparkt.androidapp

import android.os.Bundle
import android.util.Log
import android.util.Log.v
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.subroh0508.sparkt.R
import net.subroh0508.sparkt.core.CommonPrefix
import net.subroh0508.sparkt.core.SparqlQuery
import net.subroh0508.sparkt.core.extensions.get
import net.subroh0508.sparkt.core.operators.functions.contains
import net.subroh0508.sparkt.core.operators.functions.replace
import net.subroh0508.sparkt.core.operators.functions.str
import net.subroh0508.sparkt.core.vocabulary.IriVocabulary
import net.subroh0508.sparkt.vocabraries.imasparql.*
import java.net.URLDecoder

class MainActivity : AppCompatActivity() {
    class RdfImpl : IriVocabulary(
        "rdf", "type"
    ) {
        val type by store
    }

    class SchemaRdfImpl : IriVocabulary(
        "schema", "name", "memberOf"
    ) {
        val name by store
        val memberOf by store
    }

    val Set<IriVocabulary>.schema get() = this.get(SchemaRdfImpl::class)
    val Set<IriVocabulary>.rdf get() = this.get(RdfImpl::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        sendRequest.setOnClickListener { sendRequest() }
    }

    private fun sendRequest() {
        val query = SparqlQuery(
            "https://sparql.crssnky.xyz/spql/imas/query",
            listOf(*CommonPrefix.values(), *ImasparqlPrefix.values()),
            RdfImpl(), SchemaRdfImpl(), ImasparqlClass, ImasparqlProperty
        ).where {
            v("s") be {
                iri.rdf.type to imasC.idol and
                iri.schema.name to v("name") and
                imasP.title to v("title") and
                iri.schema.memberOf to v("unit_url")
            }
            filter {
                contains(v("title"), "CinderellaGirls")
            }
            v("unit_url") be {
                iri.rdf.type to imasC.unit and
                iri.schema.name to v("unit_name")
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
