package com.filipkowicz.examples.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filipkowicz.examples.R
import com.filipkowicz.examples.util.Adapter
import com.filipkowicz.examples.util.HeaderItemDecoration
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var isUserScrolling = false

    private val adapter = Adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainFragment.adapter
            addItemDecoration(
                HeaderItemDecoration(
                    this
                ) { position ->
                    this@MainFragment.adapter.getItemViewType(position) == R.layout.header_layout
                })
        }

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) isUserScrolling = true
                else if (newState == RecyclerView.SCROLL_STATE_IDLE) isUserScrolling = false
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val position =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1
                /*
                Here is the simple logic, you should implement your own logic here to correctly enable
                syncing tabs with RecyclerView
                */
                when {
                    adapter.getItem(position).data.endsWith("30") ||
                            adapter.getItem(position).data.endsWith("31") ||
                            adapter.getItem(position).data.endsWith("32")
                    -> selectTheTab(3)
                    adapter.getItem(position).data.endsWith("10") ||
                            adapter.getItem(position).data.endsWith("11") -> selectTheTab(1)
                    adapter.getItem(position).data.endsWith("0") -> selectTheTab(0)
                    adapter.getItem(position).data.endsWith("2") -> selectTheTab(2)
                    else -> selectTheTab(4)
                }
            }
        })
    }

    private fun selectTheTab(index: Int) {
        tabLayout.getTabAt(index)?.select()
    }

    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Header 0"))
        tabLayout.addTab(tabLayout.newTab().setText("Header 1"))
        tabLayout.addTab(tabLayout.newTab().setText("Header 2"))
        tabLayout.addTab(tabLayout.newTab().setText("Header 3"))
        tabLayout.addTab(tabLayout.newTab().setText("Header 4"))
        tabLayout.addOnTabSelectedListener(tabSelectionListener)
    }

    private val tabSelectionListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}
        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (isUserScrolling) return
            val pos = adapter.calculateScrollPosition((tab?.text ?: "Header 0").toString())
            (recycler.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(pos, 0)
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.sampleInput.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }

}
