package net.subroh0508.otonashi.androidapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.subroh0508.otonashi.R
import kotlin.coroutines.CoroutineContext

abstract class SearchFragment : Fragment(), CoroutineScope {
    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        job = Job()

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }
}