package com.example.answerriddle

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.system.exitProcess

val arrayRiddle = arrayOf(
    "Целое лето он прыгает серый, а к зиме меняет шубку на белую",
    "Говорить и петь не умеет, но хозяину про гостей весточку дает",
    "Этот зверь очень высок, в потолок достать он смог, он похож на кран подъемный. Кто это?",
    "У нее очень добрые глаза, она пушистая, мягкая, но для мышей — опасная",
    "Она не прядет, она не ткет, но для людей на одежду дает",
    "Серовато, зубовато, по полю рыщет, телят, ягнят ищет. На луну воет не дает никому покоя",
    "У него мордочка усатая, а шубка ее полосатая",
    "Голодная — мычит, сытая — жует, всем ребятам молоко дает",
    "Тропический кабанчик с хоботком. Наверное, слыхал ты о таком?",
    "В Америке Южной, в лесу, на деревьях, на весу изумрудный зверь живет, вечно сонный ест и пьет",
    "Большая кошка с Тибета. Что ты знаешь про это?",
    "Грызун, планировать способный, аэроплану он подобный",
    "Любит спать почти что год. Как его зовет народ?",
    "Кто на себе лес носит?",
    "Он в берлоге спит зимой. Под большущею сосной, а когда придет весна, просыпается от сна"
)
val arrayAnswer = arrayOf(
    "заяц",
    "собака",
    "жираф",
    "кошка",
    "овца",
    "волк",
    "котенок",
    "корова",
    "тапир",
    "ленивец",
    "ирбис",
    "белка-летяга",
    "сурок",
    "олень",
    "медведь"
)
class MainActivity : AppCompatActivity() {
    private var answer_array: ArrayList<String> = ArrayList()
    var random_array_riddle = arrayOf("0")
    @SuppressLint("SetTextI18n")
    var countRight:Int = 0
    private var count: Int = 0
    var count2: Int = 0
    private var index_arr:Int =0
    private var index_mas:Int = 0

    private var launcher: ActivityResultLauncher<Intent>? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutButtons = findViewById<LinearLayout>(R.id.LayoutButtons)
        val layoutAnswer = findViewById<LinearLayout>(R.id.LayoutAnswer)
        val layoutButtonsBase = findViewById<LinearLayout>(R.id.LayoutButtonBase)

        val txtRiddle = findViewById<TextView>(R.id.txtRiddle)
        val txtCount = findViewById<TextView>(R.id.txtCount)
        val txtAnswer = findViewById<TextView>(R.id.txtAnswer)

        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnClose = findViewById<Button>(R.id.btnClose)
        val btnRiddle = findViewById<Button>(R.id.btnRiddle)
        val btnAnswer = findViewById<Button>(R.id.btnAnswer)
        val btnStat = findViewById<Button>(R.id.btnStat)

        fun findIndex(arr: Array<String>, item: String): Int {
            return arr.indexOf(item)
        }//функция нахождения индекса по значению из массива

        launcher = 
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
                if (result.resultCode == RESULT_OK){
                    val dopa = result.data?.getStringExtra("pagetwo").toString()
                    txtAnswer.text = dopa
                    index_mas = findIndex(arrayAnswer,txtAnswer.text.toString())
                    if(index_arr != index_mas){
                        txtAnswer.setBackgroundColor(Color.argb(255,240,102,22))
                    }
                    else{
                        txtAnswer.setBackgroundColor(Color.argb(255,94,240,22))
                        countRight += 1
                    }
                }
            }

        btnStat.setOnClickListener {
            val intent = Intent(this, ActivityStat:: class.java)
            intent.putExtra("pageStat", countRight.toString())
            startActivity(intent)
            txtRiddle.text =""
            layoutAnswer.visibility = View.INVISIBLE
            txtCount.visibility = View.INVISIBLE
        }

        btnStart.setOnClickListener {
            layoutAnswer.visibility = View.VISIBLE
            layoutButtons.visibility = View.VISIBLE
            layoutButtonsBase.visibility = View.INVISIBLE
            txtCount.visibility = View.VISIBLE
            count = 0
            count2 = 0
            btnRiddle.isEnabled = true
            btnStat.isEnabled = false
            txtCount.text = "$count/10"
            txtAnswer.setBackgroundColor(0xE6FFE6)
            txtAnswer.text = ""
            countRight = 0

            val s: MutableSet<String> = mutableSetOf()
            while (s.size < 10) { s.add(arrayRiddle.random()) }
            val array = s.toList()
            random_array_riddle = array.toTypedArray() //рандомируем не повторяющиеся массив загадок

            val u:MutableSet<Int> = mutableSetOf()
            for(i in random_array_riddle) {
                u.add(findIndex(arrayRiddle,i))
            }//получаем индексы в основном массиве из сформированного рандомного массива загадок

            val t: MutableSet<String> = mutableSetOf()
            for (i in u){
                t.add(arrayAnswer[i])
            }//получаем значения по этим элементам
            answer_array =
                t.drop(0) as ArrayList<String>//удаляем первый элемент в итоге получаем массив ОТВЕТОВ
        }
        btnClose.setOnClickListener {
            exitProcess(-1)
        }

        btnRiddle.setOnClickListener {

            txtRiddle.text = random_array_riddle[count]
            btnAnswer.isEnabled = true
            btnRiddle.isEnabled = false
            txtAnswer.text = ""
            count += 1
            txtCount.text = "$count/10"
            txtAnswer.setBackgroundColor(0xE6FFE6)
            index_arr = findIndex(arrayRiddle,txtRiddle.text.toString())
        }

        btnAnswer.setOnClickListener {
            val intent = Intent(this, ActivityAnswer:: class.java)
            intent.putExtra("pageMain", answer_array)
            launcher?.launch(intent)
            btnAnswer.isEnabled = false
            btnRiddle.isEnabled = true
            count2 += 1
            if(count2>= 10){
                btnRiddle.isEnabled = false
                btnAnswer.isEnabled = false
                btnStat.isEnabled = true
                layoutButtonsBase.visibility = View.VISIBLE
            }
        }
    }
}