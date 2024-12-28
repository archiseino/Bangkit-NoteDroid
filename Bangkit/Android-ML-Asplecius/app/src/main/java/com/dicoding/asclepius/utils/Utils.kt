package com.dicoding.asclepius.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

object Snackbars {
    fun show(view: View, @StringRes message: Int) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAnchorView(view)
            .show()
    }
}

object Toaster {
    fun show(context: Context, message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
