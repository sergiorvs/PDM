package com.example.testmoves

import android.content.Context
import android.graphics.Canvas
import android.view.View
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class ImgMove(context: Context) : View(context){

    private val move: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.down)



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawBitmap(move,0.0f,0.0f,null)


    }
}