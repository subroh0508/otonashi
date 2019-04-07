package net.subroh0508.otonashi.androidapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {
    val openDialogEvent: LiveData<Unit>
        get() = event
    private val event: MutableLiveData<Unit> = MutableLiveData()

    fun openDialog() {
        event.postValue(Unit)
    }
}