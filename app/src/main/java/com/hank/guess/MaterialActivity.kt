package com.hank.guess

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import androidx.room.Room
import com.hank.guess.data.GameDatabase
import com.hank.guess.data.Record

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD = 0
    val secretNumber = SecretNumber()
    val TAG = "MaterialActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            replay()
        }
        Log.d(TAG, "secret number: " + secretNumber.secretNumber)
        counter.setText(secretNumber.count.toString())

        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE).getString("REC_NICKNAME","Wrong Key")
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE).getInt("Rec_COUNT",-1)
        Log.d(TAG, "$nick: $count");

        //Room Read Test
//        AsyncTask.execute { val list = GameDatabase.getInstance(this)?.recordDao()?.getAll()
//            list?.forEach{
//                Log.d(TAG, "record: ${it.nickname} ${it.count} ${it.id} ")
//            }
//        }

    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alertTitle))
            .setMessage("你確定要重玩嗎?")
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                secretNumber.restart()
                counter.setText(secretNumber.count.toString())
                ed_number.setText("")
            })
            .setNeutralButton("取消", null)
            .show()
    }

    fun check(view : View){
        val n = ed_number.text.toString().toInt()
        Log.d(TAG, "number: $n")
        val diff = secretNumber.validate(n)
        var message = getString(R.string.you_got_it)
        if(diff < 0){
            message = getString(R.string.bigger)
        }else if (diff > 0){
            message = getString(R.string.smaller)
        }
//        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alertTitle))
            .setMessage(message)
            .setPositiveButton(R.string.ok, { dialog, which ->
                if(diff == 0) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER",secretNumber.count)
                    startActivityForResult(intent,REQUEST_RECORD)
//                    startActivity(intent)
                }
            })
            .show()
        counter.setText(secretNumber.count.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_RECORD && resultCode == Activity.RESULT_OK){
            val nick = data?.getStringExtra("NICKNAME")
            Log.d(TAG, "nickname: $nick ")
            replay()
        }
    }
}
