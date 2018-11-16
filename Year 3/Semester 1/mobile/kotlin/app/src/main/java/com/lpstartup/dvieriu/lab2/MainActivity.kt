package com.lpstartup.dvieriu.lab2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.lpstartup.dvieriu.lab2.submenu.idea.TestAddActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.main_listview)
//        val redColor = Color.parseColor("#FF0000")
//        listView.setBackgroundColor(redColor)

        listView.adapter = MyCustomAdapter(this)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // safe null check
        when (item?.itemId) {
            R.id.menu_main_add_data -> {
                println("H0")
                startActivity(Intent(this, TestAddActivity::class.java))
            }
            R.id.menu_main_delete_all_data -> {
                // todo: delete all data
                val builderAlertDialog = AlertDialog.Builder(this)
                builderAlertDialog.setTitle("Delete data")
                builderAlertDialog.setMessage("Are you sure you want to delete all data?")
                builderAlertDialog.setPositiveButton(
                    "Yes"
                ) { dialogInterface, _ -> dialogInterface.dismiss() }
                builderAlertDialog.setNegativeButton(
                    "No"
                ) { dialogInterface, _ -> dialogInterface.dismiss() }
                builderAlertDialog.create().show()
            }
            else -> {
                println("H1")
            }
        }

        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private class MyCustomAdapter(context: Context) : BaseAdapter() {

        private val mContext: Context

        private val names = arrayListOf<String>(
            "Resume maker", "App builder", "Tst 3"
        )

        init {
            this.mContext = context
        }

        // responsible for how many rows in my list
        override fun getCount(): Int {
            return names.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_main, parent, false)

            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textView)
            nameTextView.text = names.get(position)

            val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)
            positionTextView.text = "Row number: $position"

            return rowMain
//            val textView = TextView(mContext)
//            textView.text = "HERE is my ROW for my LISTVIEW"
//            return textView
        }

    }
}
