package com.lambdaschool.sprint2_challenge.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.lambdaschool.sprint2_challenge.R
import com.lambdaschool.sprint2_challenge.ui.ItemDetailsActivity
import com.lambdaschool.sprint2_challenge.ui.MainActivity
import kotlinx.android.synthetic.main.recycler_cardview_layout.view.*

class ItemListAdapter(val imageList: IntArray, val itemNameList: Array<String>)
    : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    private var lastPosition = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.cardview
        val image: ImageView = view.iv_shopping_item
        val itemName: TextView = view.tv_shopping_item
        val toggle: Switch = view.switch_toggle_item
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.recycler_cardview_layout, parent, false)
        return ViewHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = getDrawable(holder.image.context, imageList[position])
        holder.image.setImageDrawable(image)
        holder.itemName.text = itemNameList[position]

        when (MainActivity.shoppingList.contains(itemNameList[position])) {
            true -> holder.toggle.isChecked = true
            false -> holder.toggle.isChecked = false
        }

        holder.toggle.setOnClickListener {
            when (holder.toggle.isChecked) {
                true -> MainActivity.shoppingList.add(itemNameList[position])
                false -> MainActivity.shoppingList.remove(itemNameList[position])
            }

        }

        holder.card.setOnClickListener { view ->
            val intent = Intent(view.context, ItemDetailsActivity::class.java)

            val optionsBundle: Bundle = ActivityOptions.makeSceneTransitionAnimation(view.context as Activity,
                    holder.image, "shared_image")
                    .toBundle()

            view.context.startActivity(intent, optionsBundle)
        }

        fun setEnterAnimation(viewToAnimate: View, position: Int) {
            if (position > lastPosition) {
                val animation: Animation = AnimationUtils.loadAnimation(viewToAnimate.context,
                        android.R.anim.slide_in_left)
                viewToAnimate.startAnimation(animation)
                lastPosition = position
            }
        }

        setEnterAnimation(holder.card, position)

    }


}