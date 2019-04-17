package net.subroh0508.otonashi.androidapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.subroh0508.otonashi.androidapp.ui.Tag

class EventViewModel : ViewModel() {
    val openDialogEvent: LiveData<Tag>
        get() = event
    private val event: MutableLiveData<Tag> = MutableLiveData()

    fun openDialog(tag: Tag) {
        event.postValue(tag)
    }
}