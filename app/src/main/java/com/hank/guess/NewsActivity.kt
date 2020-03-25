package com.hank.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val fragmentTransation = supportFragmentManager.beginTransaction()
        fragmentTransation.add(R.id.container,NewsFragment.instance)
        fragmentTransation.commit()

    }
}
