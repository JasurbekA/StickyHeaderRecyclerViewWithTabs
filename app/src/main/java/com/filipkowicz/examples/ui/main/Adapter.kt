package com.filipkowicz.examples.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filipkowicz.examples.BR
import com.filipkowicz.examples.R
import com.filipkowicz.examples.databinding.HeaderLayoutBinding
import com.filipkowicz.examples.databinding.ItemLayoutBinding
import java.util.*

class Adapter
    : ListAdapter<ListItem, DataBindingViewHolder>(object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.itemType == newItem.itemType
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder =
        when (viewType) {
            R.layout.item_layout -> ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            R.layout.header_layout -> HeaderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> throw IllegalArgumentException("no supported item id")
        }.let {
            DataBindingViewHolder(it)
        }


    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    public override fun getItem(position: Int): MainViewModel.Data {
        return currentList[position] as MainViewModel.Data
    }

    fun calculateScrollPosition(headerText: String): Int {
        currentList.forEachIndexed { index, listItem ->
            if ((listItem as MainViewModel.Data).data.toLowerCase(Locale.ROOT)
                == headerText.toLowerCase(Locale.ROOT)) return index
        }
        return 0
    }
}

class DataBindingViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MainViewModel.Data) {
        binding.setVariable(BR.data, item.data)
        binding.executePendingBindings()
    }

}