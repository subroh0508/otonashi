package net.subroh0508.otonashi.androidapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.subroh0508.otonashi.R
import net.subroh0508.otonashi.core.SparqlQuery
import net.subroh0508.otonashi.core.operators.functions.contains
import net.subroh0508.otonashi.core.operators.functions.replace
import net.subroh0508.otonashi.core.operators.functions.str
import net.subroh0508.otonashi.core.vocabulary.common.Rdf
import net.subroh0508.otonashi.core.vocabulary.common.rdf
import net.subroh0508.otonashi.vocabularies.imasparql.*
import net.subroh0508.otonashi.vocabularies.schema.SchemaClass
import net.subroh0508.otonashi.vocabularies.schema.SchemaPrefix
import net.subroh0508.otonashi.vocabularies.schema.SchemaProperty
import net.subroh0508.otonashi.vocabularies.schema.schemaP
import java.net.URLDecoder

class MainActivity : AppCompatActivity() {
    private val client: SparqlQuery by lazy {
        SparqlQuery.Builder {
            endpoint("https://sparql.crssnky.xyz/spql/imas/query")
            prefixes(Rdf.Prefix, SchemaPrefix.SCHEMA, ImasparqlPrefix.IMAS)
            install(Rdf, SchemaClass, SchemaProperty, ImasparqlClass, ImasparqlProperty)
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
                schemaP.name to v("name") and
                imasP.title to v("title") and
                schemaP.memberOf to v("unit_url")
            }
            filter {
                contains(v("title"), "CinderellaGirls")
            }
            v("unit_url") be {
                rdf.type to imasC.unit and
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

        Log.d("query", URLDecoder.decode(query.toString(), "UTF-8"))
        GlobalScope.launch {
            val result = KtorClient.get(query.toString(), ImasResult::class)
            result.forEach {
                Log.d("result", Json.stringify(ImasResult.serializer(), it))
            }
        }
    }
}
