package com.filipkowicz.examples.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.filipkowicz.examples.R
import com.filipkowicz.examples.data.ListItem
import com.filipkowicz.examples.data.items

class MainViewModel : ViewModel() {
    val sampleInput = items
}
