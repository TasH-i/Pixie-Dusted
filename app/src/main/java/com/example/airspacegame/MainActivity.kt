package com.example.airspacegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout :LinearLayout
    lateinit var startBtn :Button
    lateinit var mGameView :GameView
    lateinit var score:TextView
    lateinit var gameName:TextView
    lateinit var highScore:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameName = findViewById(R.id.gameName)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScore = findViewById((R.id.highScore))
        mGameView = GameView(this,this)

        startBtn.setOnClickListener{
            mGameView.setBackgroundResource(R.drawable.space3)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            gameName.visibility = View.GONE
        }
    }

    override fun closeGame(mScore: Int) {
        score.text = "Score : $mScore"
        rootLayout.removeView(mGameView)
        gameName.visibility = View.VISIBLE
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        highScore.visibility = View.VISIBLE
    }
}
