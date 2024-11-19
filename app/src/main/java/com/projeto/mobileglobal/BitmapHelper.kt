package com.projeto.mobileglobal

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object BitmapHelper {

    fun vectorToBitmap(
        context: Context,
        @DrawableRes id: Int,
        @ColorInt color: Int
    ): BitmapDescriptor {
        // Obtém o recurso do drawable
        val vectorDrawable = ContextCompat.getDrawable(context, id)
            ?: return BitmapDescriptorFactory.defaultMarker()

        // Cria um Bitmap com a largura e altura do drawable
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)

        // Define a cor (tint) do drawable
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)

        // Retorna o BitmapDescriptor para ser usado no marcador
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
