package dev.tsnanh.vku.adapters.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.tsnanh.vku.adapters.ThreadClickListener
import dev.tsnanh.vku.databinding.ItemThreadBinding
import dev.tsnanh.vku.domain.ForumThread
import dev.tsnanh.vku.utils.convertJsTimeToJavaString

class ThreadViewHolder(
    private val binding: ItemThreadBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup) =
            ThreadViewHolder(
                ItemThreadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    fun bind(thread: ForumThread, listener: ThreadClickListener) {
        binding.thread = thread.apply {
            createAt = convertJsTimeToJavaString(createAt)
            lastUpdateOn = convertJsTimeToJavaString(createAt)
        }
        binding.listener = listener
        binding.executePendingBindings()
    }
}