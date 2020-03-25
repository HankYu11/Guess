package com.hank.guess

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hank.guess.data.GameDatabase
import com.hank.guess.data.Record
import com.hank.guess.data.RecordDao
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecordListActivity : AppCompatActivity() , CoroutineScope{
    private lateinit var job : Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)

        job = Job()
        launch { val records = GameDatabase.getInstance(this@RecordListActivity)?.recordDao()?.getAll()
            records?.let {
                    record_recycler.layoutManager = LinearLayoutManager(this@RecordListActivity)
                    record_recycler.setHasFixedSize(true)
                    record_recycler.adapter = RecordAdapter(it)
                    }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
