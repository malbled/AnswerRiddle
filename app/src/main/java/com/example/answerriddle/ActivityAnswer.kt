@file:Suppress("DEPRECATION")

package com.example.answerriddle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup

var m_answer: String = ""

class ActivityAnswer : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        val list = intent.getStringArrayListExtra("pageMain")
        list!!.shuffle()
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val group = findViewById<RadioGroup>(R.id.RadioGroup)
        group.setOnCheckedChangeListener(this)
        for ( i in 0 until list.size){
            val radioButton = RadioButton(this)
            radioButton.apply {
                text = list[i]
                id = i
                radioButton.setTextAppearance(context, android.R.style.TextAppearance_Holo_Large)
            }
            group.addView(radioButton)
        }
        btnCheck.setOnClickListener {
            val intent = Intent(this, MainActivity:: class.java)
            intent.putExtra("pagetwo", m_answer)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
     override fun onCheckedChanged(group: RadioGroup?, checkId: Int) {
         val btnCheck = findViewById<Button>(R.id.btnCheck)
         val checkedRadioButton = group?.findViewById(group.checkedRadioButtonId) as? RadioButton
         checkedRadioButton?.let {
            if (checkedRadioButton.isChecked){
                btnCheck.isEnabled = true
                m_answer = checkedRadioButton.text.toString()
            }
        }
    }
}