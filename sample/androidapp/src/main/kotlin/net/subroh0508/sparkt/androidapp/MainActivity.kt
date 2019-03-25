package net.subroh0508.sparkt.androidapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.subroh0508.sparkt.R
import net.subroh0508.sparkt.core.Prefix
import net.subroh0508.sparkt.core.SparqlQuery
import net.subroh0508.sparkt.core.extensions.get
import net.subroh0508.sparkt.core.operators.functions.contains
import net.subroh0508.sparkt.core.operators.functions.replace
import net.subroh0508.sparkt.core.operators.functions.str
import net.subroh0508.sparkt.core.vocabulary.IriVocabulary
import net.subroh0508.sparkt.core.triples.get
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

    class ImasRdfImpl : IriVocabulary(
        "imas", "Idol", "Title", "Unit"
    ) {
        val idol by store
        val title by store
        val unit by store
    }

    val Set<IriVocabulary>.imas get() = this.get(ImasRdfImpl::class)
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
            listOf(
                Prefix("schema", "<http://schema.org/>"),
                Prefix("rdf", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"),
                Prefix("imas", "<https://sparql.crssnky.xyz/imasrdf/URIs/imas-schema.ttl#>"),
                Prefix("imasrdf", "<https://sparql.crssnky.xyz/imasrdf/RDFs/detail/>"),
                Prefix("foaf", "<http://xmlns.com/foaf/0.1/>"),
                Prefix("math", "<http://www.w3.org/2005/xpath-functions/math#>"),
                Prefix("xsd", "<https://www.w3.org/TR/xmlschema11-2/#>"),
                Prefix("rdfs", "<http://www.w3.org/2000/01/rdf-schema#>")
            ),
            RdfImpl(), ImasRdfImpl(), SchemaRdfImpl()
        ).where {
            v("s") be {
                iri.rdf.type to iri.imas.idol and
                iri.schema.name to v("name") and
                iri.imas.title to v("title") and
                iri.schema.memberOf to v("unit_url")
            }
            filter {
                contains(v("title"), "CinderellaGirls")
            }
            v("unit_url") be {
                iri.rdf.type to iri.imas.unit and
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
