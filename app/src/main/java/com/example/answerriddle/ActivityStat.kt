package com.example.answerriddle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ActivityStat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val txtRight = findViewById<TextView>(R.id.txtRightRiddle)
        val txtWrong = findViewById<TextView>(R.id.txtWrongRiddle)
        val dopa = (intent.getStringExtra("pageStat")).toString()
        txtRight.text = dopa
        txtWrong.text = (10 - dopa.toInt()).toString()

        btnBack.setOnClickListener {
            finish()
        }
    }
}