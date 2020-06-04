/*
 *    Copyright 2020 Yin Lok Ho
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package yinlokh.tab_recycler_mediator

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class TabRecyclerMediator (
    private val recycler : RecyclerView,
    private val tabLayout : TabLayout) : TabLayout.OnTabSelectedListener, RecyclerView.OnScrollListener() {

    var listener : Listener? = null

    private var linearLayoutManager : LinearLayoutManager? = null
    private var previousScrollState = RecyclerView.SCROLL_STATE_IDLE
    private var ignoreTabSelection = false
    private var currentTab = 0
    private val smoothScroller : LinearSmoothScroller
    private var smoothScrolling = false

    init {
        linearLayoutManager = recycler.layoutManager as? LinearLayoutManager
        smoothScroller = object : LinearSmoothScroller(recycler.context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }

        tabLayout.addOnTabSelectedListener(this)
        recycler.addOnScrollListener(this)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) { }

    override fun onTabUnselected(tab: TabLayout.Tab?) { }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null && !smoothScrolling && !ignoreTabSelection) {
            var recyclerPosition =  listener?.getRecyclerPositionForTab(tab.position)
            if (recyclerPosition != null) {
                smoothScroller.targetPosition = recyclerPosition
                linearLayoutManager?.startSmoothScroll(smoothScroller)
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_SETTLING ->
                smoothScrolling = previousScrollState == RecyclerView.SCROLL_STATE_IDLE
            RecyclerView.SCROLL_STATE_DRAGGING ->  smoothScrolling = false
            RecyclerView.SCROLL_STATE_IDLE -> smoothScrolling = false
        }

        previousScrollState = newState
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var newTab = listener?.getTabForRecyclerPosition(
            linearLayoutManager?.findFirstVisibleItemPosition()?:0)
        if (newTab != null && currentTab != newTab) {
            currentTab = newTab

            // tablayout synchronously dispatches tab selection to listener
            if (!smoothScrolling) {
                ignoreTabSelection = true
                tabLayout.selectTab(tabLayout.getTabAt(newTab))
                ignoreTabSelection = false
            }
        }
    }

    interface Listener {

        fun getTabForRecyclerPosition(pos : Int) : Int

        fun getRecyclerPositionForTab(tab : Int) : Int
    }
}