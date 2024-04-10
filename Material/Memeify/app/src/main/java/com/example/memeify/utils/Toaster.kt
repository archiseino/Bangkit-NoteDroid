package com.example.memeify.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object Toaster {

  fun show(context: Context, @StringRes message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
  }

}
