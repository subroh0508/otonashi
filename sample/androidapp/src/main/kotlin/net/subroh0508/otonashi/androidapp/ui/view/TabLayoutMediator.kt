package net.subroh0508.otonashi.androidapp.ui.view

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.*
import com.google.android.material.tabs.TabLayout
import java.lang.ref.WeakReference
import java.lang.reflect.Method

class TabLayoutMediator(
    private val tabLayout: TabLayout,
    private val viewPager: ViewPager2,
    private val autoRefresh: Boolean,
    private val onConfigureTabCallback: OnConfigureTabCallback
) {
    constructor(
        tabLayout: TabLayout, viewPager: ViewPager2, onConfigureTabCallback: OnConfigureTabCallback
    ) : this(
        tabLayout, viewPager, true, onConfigureTabCallback
    )

    private var attached = false
    private lateinit var adapter: RecyclerView.Adapter<*>

    interface OnConfigureTabCallback {
        fun onConfigureTab(tab: TabLayout.Tab, position: Int)
    }

    fun attach() {
        if (attached) {
            throw IllegalStateException("TabLayoutMediator is already attached")
        }

        adapter = viewPager.adapter ?: throw IllegalStateException("TabLayoutMediator attached before ViewPager2 has an adapter")

        attached = true

        viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        tabLayout.addOnTabSelectedListener(onTabSelectedListener)

        if (autoRefresh) {
            adapter.registerAdapterDataObserver(pagerAdapterObserver)
        }

        populateTabsFromPagerAdapter()

        tabLayout.setScrollPosition(viewPager.currentItem, 0F, true)
    }

    fun detach() {
        adapter.unregisterAdapterDataObserver(pagerAdapterObserver)
        tabLayout.removeOnTabSelectedListener(onTabSelectedListener)
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    private fun populateTabsFromPagerAdapter() {
        tabLayout.removeAllTabs()

        val adapterCount = adapter.itemCount

        (0 until adapterCount).forEach { i ->
            val newTab = tabLayout.newTab()
            onConfigureTabCallback.onConfigureTab(newTab, i)
            tabLayout.addTab(newTab, false)
        }

        if (adapterCount <= 0) return

        val currentItem = viewPager.currentItem

        if (currentItem != tabLayout.selectedTabPosition) {
            tabLayout.getTabAt(currentItem)?.select()
        }
    }

    private val onPageChangeCallback: ViewPager2.OnPageChangeCallback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            private val tabLayoutRef: WeakReference<TabLayout> = WeakReference(tabLayout)
            private var previousScrollState = SCROLL_STATE_IDLE
            private var scrollState = SCROLL_STATE_IDLE

            override fun onPageScrollStateChanged(state: Int) {
                previousScrollState = scrollState
                scrollState = state
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val tabLayout = tabLayoutRef.get() ?: return

                val updateText = scrollState != SCROLL_STATE_SETTLING || previousScrollState == SCROLL_STATE_DRAGGING
                val updateIndicator = !(scrollState == SCROLL_STATE_SETTLING && previousScrollState == SCROLL_STATE_IDLE)

                setScrollPosition(tabLayout, position, positionOffset, updateText, updateIndicator)
            }

            override fun onPageSelected(position: Int) {
                val tabLayout = tabLayoutRef.get() ?: return
                val tab = tabLayout.getTabAt(position) ?: return

                if (tabLayout.selectedTabPosition == position || tabLayout.tabCount <= position) return

                val updateIndicator = scrollState == SCROLL_STATE_IDLE || (scrollState == SCROLL_STATE_SETTLING && previousScrollState == SCROLL_STATE_IDLE)

                selectTab(tabLayout, tab, updateIndicator)
            }

            fun reset() {
                previousScrollState = SCROLL_STATE_IDLE
                scrollState = SCROLL_STATE_IDLE
            }
        }
    }



    private val onTabSelectedListener: TabLayout.OnTabSelectedListener by lazy {
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) = tab?.let { viewPager.setCurrentItem(it.position, true) } ?: Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
        }
    }

    private val pagerAdapterObserver: RecyclerView.AdapterDataObserver by lazy {
        object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() = populateTabsFromPagerAdapter()
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) = populateTabsFromPagerAdapter()
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) = populateTabsFromPagerAdapter()
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = populateTabsFromPagerAdapter()
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = populateTabsFromPagerAdapter()
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) = populateTabsFromPagerAdapter()
        }
    }

    companion object {
        private fun setScrollPosition(
            tabLayout: TabLayout,
            position: Int, positionOffset: Float,
            updateSelectedText: Boolean, updateIndicatorPosition: Boolean
        ) {
            try {
                setScrollPositionMethod.invoke(tabLayout, position, positionOffset, updateSelectedText, updateIndicatorPosition)
            } catch (e: Throwable) {
                throw IllegalStateException("Couldn't invoke method $SET_SCROLL_POSITION_NAME")
            }
        }

        private fun selectTab(
            tabLayout: TabLayout,
            tab: TabLayout.Tab, updateIndicator: Boolean
        ) {
            try {
                selectTabMethod.invoke(tabLayout, tab, updateIndicator)
            } catch (e: Throwable) {
                throw IllegalStateException("Couldn't invoke method $SELECT_TAB_NAME")
            }
        }

        private val setScrollPositionMethod: Method = TabLayout::class.java.getDeclaredMethod(
            "setScrollPosition",
            Int::class.java, Float::class.java, Boolean::class.java, Boolean::class.java
        ).apply { isAccessible = true }

        private val selectTabMethod: Method = TabLayout::class.java.getDeclaredMethod(
            "selectTab",
            TabLayout.Tab::class.java, Boolean::class.java
        ).apply { isAccessible = true }

        private const val SET_SCROLL_POSITION_NAME = "TabLayout.setScrollPosition(int, float, boolean, boolean)"
        private const val SELECT_TAB_NAME = "TabLayout.selectTab(TabLayout.Tab, boolean)"
    }
}