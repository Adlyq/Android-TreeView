package com.adlyq.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.adlyq.test.atv.SimpleTreeAdapter
import com.adlyq.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binder: ActivityMainBinding

    private val mAdapter = SimpleTreeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
        binder.rv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }


        mAdapter.apply {

            val n01 = addNode(root, "n01", true)?:throw Exception()
            val n02 = addNode(root, "n02", true)?:throw Exception()
            val n03 = addNode(root, "n03", true)?:throw Exception()

            val n11 = addNode(n01, "n11", true)?:throw Exception()
            addNode(n01, "n12")?:throw Exception()

            addNode(n02, "n21")?:throw Exception()
            addNode(n02, "n22", true)?:throw Exception()

            addNode(n03, "n31")?:throw Exception()

            addNode(n11, "n111")?:throw Exception()

            removeNode(n03)

        }

        mAdapter.flash()
    }
}