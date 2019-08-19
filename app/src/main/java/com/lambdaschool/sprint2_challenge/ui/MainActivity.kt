package com.lambdaschool.sprint2_challenge.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.lambdaschool.sprint2_challenge.R
import com.lambdaschool.sprint2_challenge.ShoppingItemConstants
import com.lambdaschool.sprint2_challenge.adapter.ItemListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var shoppingList = mutableListOf<String>()
        const val NOTIFICATION_ID = 99
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_layout.setHasFixedSize(true)
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = ItemListAdapter(ShoppingItemConstants.ICON_IDS, ShoppingItemConstants.ITEM_NAMES_RAW)
        rv_layout.layoutManager = manager
        rv_layout.adapter = adapter


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "$packageName.deranchannel"


        btn_send_list.setOnClickListener {
            // send notification saying Order has been placed
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Shopping List Notification"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val description = "Notification showing verification that shopping list has been shared."

                val channel = NotificationChannel(channelId, name, importance)
                channel.description = description
            }

            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setPriority(NotificationManager.IMPORTANCE_LOW)
                    .setContentTitle("Shopping List")
                    .setContentText("Your shopping list has been shared!")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setColor(Color.BLUE)

            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

            //convert shoppingList to string and remove first and last character -- the [ and ]
            val shoppingString = "Shopping List: " + shoppingList.toString().drop(1).dropLast(1)

            //debug log to make sure string works properly
//            Log.d("SHOPPINGLIST", shoppingString)

            // send implicit intent with list of items

            val shareTextIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shoppingString)
                type = "text/plain"
            }
            startActivity(shareTextIntent)
        }

    }
}
