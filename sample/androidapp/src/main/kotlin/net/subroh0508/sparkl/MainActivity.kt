package net.subroh0508.sparkl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.subroh0508.core.SparqlQuery
import net.subroh0508.core.Var

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        sendRequest.setOnClickListener { sendRequest() }
    }

    private fun sendRequest() {
        SparqlQuery("hogehoge").where {
            triple(Var(""), Var(""), Var(""))
                +(Var("") to Var(""))

            triple(Var(""), Var(""), Var(""))
                +(Var("") to Var(""))
        }
    }
}
