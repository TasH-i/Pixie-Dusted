package com.example.airspacegame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(var c :Context, var gameTask: GameTask):View(c)
{
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var tinkPosition = 0
    private val astroid = ArrayList<HashMap<String,Any>>()

    var viewWith = 0
    var viewHeight = 0
    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWith = this.measuredWidth
        viewHeight = this.measuredHeight

        if(time % 700 < 10 + speed){
            val map = HashMap<String,Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            astroid.add(map)
        }
        time = time + 10 + speed
        val tinkWidth = viewWith / 5
        val tinkHeight = tinkWidth + 10
        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.plane,null)

        d.setBounds(
            tinkPosition * viewWith / 3 + viewWith / 15 + 25,
            viewHeight-2 - tinkHeight,
            tinkPosition * viewWith / 3 + viewWith / 15 + tinkWidth - 25 ,
            viewHeight - 2
        )

        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for (i in astroid.indices){
            try {
                val carX = astroid[i]["lane"] as Int * viewWith / 3 + viewWith / 15
                var carY = time - astroid[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.astroid1,null)
                val d3 = resources.getDrawable(R.drawable.astroid2,null)

                d2.setBounds(
                    carX + 25 , carY - tinkHeight , carX + tinkWidth - 25 , carY
                )
                d3.setBounds(
                    carX + 25 , carY - tinkHeight , carX + tinkWidth - 25 , carY
                )

                d2.draw(canvas!!)
                d3.draw(canvas!!)
                if(astroid[i]["lane"] as Int == tinkPosition){
                    if (carY > viewHeight - 2 - tinkHeight
                        && carY < viewHeight - 2){

                        gameTask.closeGame(score)
                    }

                }
                if(carY > viewHeight + tinkHeight){
                    astroid.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if(score > highScore){
                        highScore = score
                        val pref = context.getSharedPreferences("MyPref", 0)
                        val editor = pref.edit()
                        editor.putInt("scoreSp", highScore)
                        editor.apply()
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x1 = event.x
                if(x1 < viewWith / 2){
                    if (tinkPosition> 0){
                        tinkPosition--
                    }
                }
                if(x1 > viewWith / 2){
                    if(tinkPosition < 2){
                        tinkPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP ->{}
        }
        return true
    }
}