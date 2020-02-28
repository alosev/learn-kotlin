package com.losev.myapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

import com.losev.myapp.R

class LogoutDialog : DialogFragment() {

    companion object{
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun create() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
            AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.logout_dialog_title)
                    .setMessage(R.string.logout_dialog_message)
                    .setPositiveButton(R.string.logout_dialog_ok){
                        dialog, which -> (activity as LogoutListener).onLogout()
                    }
                    .setNegativeButton(R.string.logout_dialog_cancel){
                        dialog, which -> dismiss()
                    }
                    .create()


    interface LogoutListener{
        fun onLogout()
    }

}