package com.lambdaschool.sprint2_challenge.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Window
import com.lambdaschool.sprint2_challenge.R

class ItemDetailsActivity : AppCompatActivity() {

    companion object{
        const val ITEM_NAME_KEY = "ITEM_NAME"
        const val ITEM_DRAWABLE_KEY = "ITEM_DRAWABLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.enterTransition = Slide()
        window.exitTransition = Slide()

        setContentView(R.layout.activity_item_details)


    }
}
