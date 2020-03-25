package com.hank.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hank.guess.data.GameDatabase
import com.hank.guess.data.Record
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecordActivity : AppCompatActivity() ,CoroutineScope{
    private lateinit var job : Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        val count = intent.getIntExtra("COUNTER", -1)
        counter.setText(count.toString())
        job = Job()
        rec_save.setOnClickListener { view ->
            val nick = rec_name.text.toString()
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                .edit()
                .putInt("REC_COUNT",count)
                .putString("REC_NICKNAME",nick)
                .apply()
            val intent = Intent()
            intent.putExtra("NICKNAME",nick)
            setResult(Activity.RESULT_OK,intent)
            //Room Test
            var record = Record(nick,count)
            var database =GameDatabase.getInstance(this)
            launch{
                database?.recordDao()?.insert(record)
            }.start()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
