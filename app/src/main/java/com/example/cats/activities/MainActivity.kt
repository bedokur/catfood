package com.example.cats.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.CatsApplication
import com.example.cats.R
import com.example.cats.adapters.FoodItemAdapter
import com.example.cats.adapters.TestAdapter
import com.example.cats.data.FedData
import com.example.cats.data.FoodItem
import com.example.cats.databinding.ActivityMainBinding
import com.example.cats.repository.Telegram
import com.example.cats.viewModels.SharedViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), FoodItemAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    var sendCounter = 0
    private var timer: Long? = null

//    @DelicateCoroutinesApi
//    private val viewModel: SharedViewModel by viewModels {
//        SharedViewModelFactory((application as CatsApplication).repository)
//    }

    @Inject
    lateinit var viewModel: SharedViewModel

    @Inject
    lateinit var telebot: Telegram

    private var namesList: List<FoodItem> = listOf()
    private var fedList: List<FedData>? = null

    private val buttonAdapter = FoodItemAdapter(this)

    private val foodAdapter = TestAdapter()

    private lateinit var tPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var diff = 0L
    private var chronoDiff = 0L
    private var lastDate = 0L
    private var lastChrono = 0L

    private lateinit var chrono: Chronometer
    private lateinit var foodRecycler: RecyclerView


    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        setContentView(view)

        (applicationContext as CatsApplication).appComponent?.injectAct(this@MainActivity)

        tPreference = getSharedPreferences("appCat", Context.MODE_PRIVATE)


        Log.d("TAG", "${tPreference.all}")

        foodRecycler = binding.recyclerViewFood
        foodRecycler.adapter = foodAdapter
        foodRecycler.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )


        lastDate = tPreference.getLong("lastDate", 0L)
        lastChrono = tPreference.getLong("lastChrono", 0L)


        chrono = binding.chrono
        chrono.base = SystemClock.elapsedRealtime()
        chrono.start()

        if (lastDate != 0L) {
            diff = Date().time - lastDate
            Log.d("TIME TAG", "diff: $diff")
            if (lastChrono != 0L) {
                chrono.base = lastChrono - chronoDiff
            }
//            Toast.makeText(this, "$lasttime", Toast.LENGTH_SHORT).show()
        }


        val buttonsRecycler = binding.buttonRv
        buttonsRecycler.adapter = buttonAdapter
        buttonsRecycler.layoutManager = GridLayoutManager(this, 3)


        viewModel.timerTime.observe(this, {
            if (it != null) {
                timer =
                    (it.dHours.hours.toLong() * 3600000) + (it.dMinutes.minutes.toLong() * 60000)

            }
        })


        chrono.onChronometerTickListener = OnChronometerTickListener {
            if (timer != null) {
                if (SystemClock.elapsedRealtime() - it.base >= timer!! && sendCounter == 0
                    && timer != 0L
                ) {
                    if (viewModel.timerTime.value?.dHours?.hours != 0) {
                        telebot.sendMessage(
                            "Покорми кошку! Ты не кормил её уже " +
                                    "${viewModel.timerTime.value?.dHours?.hours} часов " +
                                    "${viewModel.timerTime.value?.dMinutes?.minutes} минут!"
                        )
                    } else {
                        telebot.sendMessage(
                            "Покорми кошку! Ты не кормил её уже " +
                                    "${viewModel.timerTime.value?.dMinutes?.minutes} минут"
                        )
                    }
                    sendCounter = +1
                }
            }

        }


        binding.plusHour.setOnClickListener {
            var now = chrono.base
            now -= 3600000L
            chrono.base = now
            Toast.makeText(this, "$now", Toast.LENGTH_SHORT).show()
        }

        binding.minusHour.setOnClickListener {
            val now = chrono.base
            val then = now + 3600000L
            chrono.base = then
            if (chrono.text[0] == '−' || chrono.text.contains('-')) {
                chrono.base = now
            } else {
                chrono.base = then
            }

        }


        binding.foodEditText.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submitFood()
                binding.foodEditText.hideKeyboard()
                handled = true
            }
            handled
        }
        binding.textInputLayout.setEndIconOnClickListener {
            if (binding.foodEditText.text != null && binding.foodEditText.text.toString() != "") {
                val foodName: String = binding.foodEditText.text.toString()
                viewModel.insertFoodName(FoodItem(foodName))
                binding.foodEditText.hideKeyboard()
                binding.foodEditText.text?.clear()

            }
        }


        viewModel.foodNames.observe(this, {
            namesList = it
            buttonAdapter.submitList(namesList)

        })


        viewModel.fedDataList.observe(this, {
            fedList = it.reversed()
            foodAdapter.submitList(fedList)
            lifecycleScope.launch {
                delay(500L)
                launch {
                    foodRecycler.smoothScrollToPosition(0)
                }
            }
        })


    }

    override fun onPause() {
        super.onPause()
        editor = tPreference.edit()
        val time = Date().time
        val lastChrono = chrono.base
        editor.putLong("lastDate", time).apply()
        editor.putLong("lastChrono", lastChrono).apply()
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG RESUME", "${tPreference.all}")
        if (lastDate != 0L) {
            diff = Date().time - lastDate
            Log.d("TIME TAG", "diff: $diff")
            if (lastChrono != 0L) {
                chrono.base = lastChrono - chronoDiff
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings1 -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun submitFood() {
        if (binding.foodEditText.text != null && binding.foodEditText.text.toString() != "") {
            val foodName: String = binding.foodEditText.text.toString()
            viewModel.insertFoodName(FoodItem(foodName))
            binding.foodEditText.text?.clear()

        }
    }


    override fun onItemClick(position: Int) {
//        Toast.makeText(this, namesList?.get(position)?.name, Toast.LENGTH_SHORT).show()
        val item = FedData("${chrono.text}", namesList[position].name)
        viewModel.insertFedData(item)
        chrono.base = SystemClock.elapsedRealtime()

        if (sendCounter == 1) {
            sendCounter = 0
        }
    }

    override fun onLongItemClick(position: Int) {
        val alert = AlertDialog.Builder(this)
        alert.apply {
            setTitle("Удалить ${namesList[position].name}?")
            setPositiveButton("Да") { dialog, _ ->
                viewModel.deleteFoodNames(namesList[position])
                dialog.cancel()
            }
            setNegativeButton("Нет") { dialog, _ ->
                dialog.cancel()
            }
            setIcon(R.drawable.ic_baseline_delete_24)
        }.show()

    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}





