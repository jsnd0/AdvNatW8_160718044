package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import com.example.todoapp.util.MIGRATION_1_2
import com.example.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel (application: Application):AndroidViewModel(application),CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDb(getApplication())
            todoLD.value = db.todoDao().selectAllTodo()

        }
    }

    fun clearTask(todo: Todo) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().deleteTodo(todo)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun updateIsDone(is_done: Int, uuid: Int) {
        launch {
            val db = buildDb(getApplication())
            if (is_done == 0) db.todoDao().updateIsDone(1, uuid)
            else db.todoDao().updateIsDone(0, uuid)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }
}