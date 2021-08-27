package com.example.cats.activities

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.CatsApplication
import com.example.cats.data.Hours
import com.example.cats.data.Minutes
import com.example.cats.R
import com.example.cats.data.TimeData
import com.example.cats.databinding.SettingsActivityBinding
import com.example.cats.viewModels.SharedViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding

    private val hPagerHelper = LinearSnapHelper()
    private val mPagerHelper = LinearSnapHelper()

    private var hTime: Hours? = null
    private var mTime: Minutes? = null

    private lateinit var hRecycler: RecyclerView
    private lateinit var mRecycler: RecyclerView

//    private val sharedViewModel: SharedViewModel by viewModels {
//        SharedViewModelFactory((application as CatsApplication).repository)
//    }

    @Inject
    lateinit var sharedViewModel: SharedViewModel


    private var listHours = getHoursList()
    private var listMinutes = getMinutesList()

    private val hLinearLayoutManager = LinearLayoutManager(this)
    private val mLinearLayoutManager = LinearLayoutManager(this)


    @SuppressLint("RestrictedApi")
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SettingsActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        (applicationContext as CatsApplication).appComponent.injectSet(this)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE


        hRecycler = binding.hoursRcv
        mRecycler = binding.minutesRcv

        val hoursAdapter = HoursAdapter(listHours)
        val minutesAdapter = MinutesAdapter(listMinutes)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDefaultDisplayHomeAsUpEnabled(true)


        hRecycler.adapter = hoursAdapter
        hRecycler.layoutManager = hLinearLayoutManager
        hRecycler.setItemViewCacheSize(listHours.size)
        hRecycler.setHasFixedSize(true)

        mRecycler.adapter = minutesAdapter
        mRecycler.layoutManager = mLinearLayoutManager
        mRecycler.setItemViewCacheSize(listMinutes.size)
        mRecycler.setHasFixedSize(true)



        hPagerHelper.attachToRecyclerView(hRecycler)
        mPagerHelper.attachToRecyclerView(mRecycler)
        val btn = binding.timeSet
        btn.visibility = View.INVISIBLE

        var hoursPos = 0
        var minPos = 0

        hTime = listHours[hoursPos]
        mTime = listMinutes[minPos]


        btn.setOnClickListener {
            sharedViewModel.deleteTimeTable()
            val time = TimeData(hTime!!, mTime!!)
            sharedViewModel.insertTime(time)
        }
        binding.tvTimerInfo.visibility = View.VISIBLE

        hRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val snapView =
                    hPagerHelper.findSnapView(hRecycler.layoutManager as LinearLayoutManager)

                if (snapView != null) {
                    hoursPos = hLinearLayoutManager.getPosition(snapView)
                }
                hTime = listHours.get(hoursPos)
                val text = getString(
                    R.string.timer_string,
                    hTime?.hours.toString(),
                    mTime?.minutes.toString()
                )
                binding.tvTimerInfo.text = text

                if (!btn.isVisible) {
                    btn.visibility = View.VISIBLE
                }

            }
        })

        mRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val snapView =
                    mPagerHelper.findSnapView(mRecycler.layoutManager as LinearLayoutManager)

                if (snapView != null) {
                    minPos = mLinearLayoutManager.getPosition(snapView)
                }
                mTime = listMinutes.get(minPos)
                val text = getString(
                    R.string.timer_string,
                    hTime?.hours.toString(),
                    mTime?.minutes.toString()
                )
                binding.tvTimerInfo.text = text

                if (!btn.isVisible) {
                    btn.visibility = View.VISIBLE
                }
            }
        })

        sharedViewModel.timerTime.observe(this, {
            if (it != null) {
                val text = getString(
                    R.string.current_timer,
                    it.dHours.hours.toString(),
                    it.dMinutes.minutes.toString()
                )
                binding.currentTimer.text = text
                binding.currentTimer.visibility = View.VISIBLE
            }
        })
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getHoursList(): ArrayList<Hours> {

        val list = ArrayList<Hours>()
        for (i in 0..24) {
            val item = Hours(i)
            list.add(item)
        }

        return list
    }

    private fun getMinutesList(): ArrayList<Minutes> {
        val list = ArrayList<Minutes>()
        for (i in 0..60) {
            val item = Minutes(i)
            list.add(item)
        }
        return list
    }

}

