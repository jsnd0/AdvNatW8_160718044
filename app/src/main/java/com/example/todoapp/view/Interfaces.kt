package com.example.todoapp.view

import android.widget.CompoundButton
import com.example.todoapp.model.Todo

interface TodoCheckChangedListener {
    fun ohCheckChanged(cb: CompoundButton, isChecked: Boolean, obj: Todo)
}