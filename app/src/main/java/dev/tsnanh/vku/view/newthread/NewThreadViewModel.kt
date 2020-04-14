package dev.tsnanh.vku.view.newthread

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.tsnanh.vku.repository.VKURepository
import org.koin.java.KoinJavaComponent.inject

class NewThreadViewModel(private val state: SavedStateHandle) :
    ViewModel() {
    private val repository by inject(VKURepository::class.java)

    val forums = repository.getAllForums()

    private val _pickerHasImage = MutableLiveData(false)
    val pickerHasImage: LiveData<Boolean>
        get() = _pickerHasImage

    fun onPickerHasImage() {
        _pickerHasImage.value = true
    }

    fun onPickerHasNoImage() {
        _pickerHasImage.value = false
    }
}