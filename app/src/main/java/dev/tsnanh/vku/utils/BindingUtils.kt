/*
 * Copyright (c) 2020 My VKU by tsnAnh
 */

package dev.tsnanh.vku.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.flexbox.FlexboxLayout
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.chip.Chip
import dev.tsnanh.vku.R
import dev.tsnanh.vku.domain.entities.Forum
import dev.tsnanh.vku.domain.entities.ForumThread
import dev.tsnanh.vku.domain.entities.Reply
import dev.tsnanh.vku.domain.network.BASE_URL
import dev.tsnanh.vku.views.my_vku.replies.RepliesFragmentDirections
import timber.log.Timber

fun progressBar(context: Context) = CircularProgressDrawable(context).apply {
    setColorSchemeColors(ContextCompat.getColor(context, R.color.secondaryColor))
    strokeWidth = 4F
    centerRadius = 20F
}

@BindingAdapter("categoryNews")
fun TextView.setCategory(category: String?) {
    category?.let {
        text = when (category) {
            "thong-bao-chung" -> this.context.getString(R.string.text_thong_bao_chung)
            "thong-bao-ctsv" -> this.context.getString(R.string.text_thong_bao_ctsv)
            "thong-bao-khtc" -> this.context.getString(R.string.text_thong_bao_khtc)
            else -> this.context.getString(R.string.text_uncategorized)
        }
    }
}

@BindingAdapter("userAvatar")
fun ImageView.setAvatar(user: GoogleSignInAccount?) {
    user?.let {
        Glide
            .with(this.context)
            .load(user.photoUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(progressBar(this.context))
            .circleCrop()
            .into(this)
    }
}

@BindingAdapter("forumImage")
fun ImageView.setForumImage(forum: Forum?) {
    forum?.let {
        Glide
            .with(this.context)
            .load(forum.image)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(progressBar(this.context))
            .into(this)
    }
}

@BindingAdapter("imageChooser")
fun ImageView.setChooserImage(uri: Uri?) {
    uri?.let {
        Glide
            .with(this.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(progressBar(this.context))
            .into(this)
    }
}

@BindingAdapter("pickerHasImage")
fun Button.setButtonVisible(visibility: Boolean) {
    this.visibility = when (visibility) {
        true -> View.GONE
        false -> View.VISIBLE
    }
}

@BindingAdapter("hasImage")
fun RecyclerView.setHasImage(visibility: Boolean) {
    this.visibility = when (visibility) {
        true -> View.VISIBLE
        false -> View.INVISIBLE
    }
}

@BindingAdapter("itemThreadAvatar")
fun ImageView.setItemThreadAvatar(thread: ForumThread) {
    Glide
        .with(this.context)
        .load(thread.userAvatar)
        .placeholder(progressBar(this.context))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .circleCrop()
        .into(this)
}

@BindingAdapter("postAvatar")
fun ImageView.setPostAvatar(reply: Reply?) {
    reply?.let {
        Glide
            .with(this.context)
            .load(reply.userAvatar)
            .placeholder(progressBar(this.context))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .circleCrop()
            .into(this)
    }
}

@BindingAdapter("postImages")
fun FlexboxLayout.setImages(reply: Reply?) {
    this.removeAllViews()
    reply?.let {
        val imageCount = it.images.size
        val transitionName = this.context.getString(R.string.image_transition_name)
        it.images.mapIndexed { i, image ->
            val imageView = AppCompatImageView(this.context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            val metrics = DisplayMetrics()
            (this.context as Activity).windowManager
                .defaultDisplay
                .getMetrics(metrics)
            val params = FlexboxLayout.LayoutParams(
                when (imageCount) {
                    1, 2 -> metrics.widthPixels / imageCount
                    else -> metrics.widthPixels / 3
                },
                when (imageCount) {
                    1, 2 -> metrics.widthPixels / imageCount
                    else -> metrics.widthPixels / 3
                }
            )
//            params.flexBasisPercent = 30F
            params.order = 1
            params.flexShrink = 1F
            imageView.layoutParams = params
            Glide
                .with(imageView)
                .load("$BASE_URL/$image")
                .placeholder(progressBar(this.context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
            imageView.transitionName = transitionName
            imageView.setOnClickListener {
                val extras =
                    FragmentNavigatorExtras(imageView to transitionName)
                this.findNavController().navigate(
                    RepliesFragmentDirections.actionNavigationRepliesToNavigationImageViewer(
                        reply.images.toTypedArray(),
                        i
                    ),
                    extras
                )
            }
            this.addView(imageView, params)
        }
    }
}

@BindingAdapter("image")
fun ImageView.setImageByURL(url: String?) {
    url?.let {
        Timber.d("$BASE_URL/$url")
        Glide
            .with(this.context)
            .load("$BASE_URL/$url")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(progressBar(this.context))
            .into(this)
    }
}

@BindingAdapter("dayOfWeek")
fun TextView.setDayOfWeek(string: String) {
    text = when (string) {
        Constants.MONDAY -> context.getString(R.string.text_monday)
        Constants.TUESDAY -> context.getString(R.string.text_tuesday)
        Constants.WEDNESDAY -> context.getString(R.string.text_wednesday)
        Constants.THURSDAY -> context.getString(R.string.text_thursday)
        Constants.FRIDAY -> context.getString(R.string.text_friday)
        Constants.SATURDAY -> context.getString(R.string.text_saturday)
        else -> context.getString(R.string.text_not_available)
    }
}

@BindingAdapter("weekChip")
fun Chip.setWeekChip(week: String) {
    text = when (week.trim()) {
        "", "_" -> "-"
        else -> "${context.getString(R.string.week)} $week"
    }
}

@BindingAdapter("roomChip")
fun Chip.setRoomChip(room: String) {
    text = when (room.trim()) {
        "", "_" -> "-"
        else -> "${context.getString(R.string.room)} $room"
    }
}

@BindingAdapter("lessonChip")
fun Chip.setLessonChip(lesson: String) {
    text = when (lesson.trim()) {
        "", "_" -> "-"
        else -> "${context.getString(R.string.lesson)} $lesson"
    }
}