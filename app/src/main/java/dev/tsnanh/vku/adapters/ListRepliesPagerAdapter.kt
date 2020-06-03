package dev.tsnanh.vku.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.tsnanh.vku.views.replies.list_replies.ListRepliesFragment

class ListRepliesPagerAdapter(
    fragment: Fragment,
    private val threadId: String,
    private val totalPage: Int
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = totalPage

    override fun createFragment(position: Int) = ListRepliesFragment(threadId, position + 1)
}