package com.lpstartup.dvieriu.lab2.submenu.idea

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lpstartup.dvieriu.lab2.R

class TestEditActivity : AppCompatActivity(), TestEditActivityView {
    override fun updateData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateDataFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAttachView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDetachView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_edit)
        title = "Edit data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}