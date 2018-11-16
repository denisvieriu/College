package com.lpstartup.dvieriu.lab2.submenu.idea

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lpstartup.dvieriu.lab2.R
import android.view.MenuItem

class TestAddActivity : AppCompatActivity(), TestAddActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_add)
        title = "Add test data"
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetachView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAttachView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}