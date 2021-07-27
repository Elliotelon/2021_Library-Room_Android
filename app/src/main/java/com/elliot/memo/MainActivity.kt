package com.elliot.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), OnDeleteListener {

    private val button by lazy { findViewById<AppCompatButton>(R.id.button_add) }
    private val editText by lazy { findViewById<EditText>(R.id.edittext_memo) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("fail", "1")

        db = MemoDatabase.getInstance(this)!!
        Log.d("fail", "2")
        button.setOnClickListener {

            val memo = MemoEntity(null, editText.text.toString())
            editText.setText("")
            insertMemo(memo)
            recyclerView.scrollToPosition(memoList.size-1)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        getAllMemos()

    }

    //1. Insert Data
    //2. Get Data
    //3. Delete Data
    //4. Set RecyclerView

    fun insertMemo(memo : MemoEntity){
        //1. MainThread vs WorkerThread(Background Thread)
        runBlocking {
            CoroutineScope(Dispatchers.IO).async {
                db.memoDAO().insert(memo)
            }.await()
        }

        getAllMemos()

    }

    fun getAllMemos(){
        runBlocking {
            CoroutineScope(Dispatchers.IO).async {
               memoList = db.memoDAO().getAll()
            }.await()
        }

        setRecyclerView(memoList)
    }

    fun deleteMemo(memo : MemoEntity){
        runBlocking {
            CoroutineScope(Dispatchers.IO).async {
                db.memoDAO().delete(memo)
            }.await()
        }

        getAllMemos()
    }

    fun setRecyclerView(memoList : List<MemoEntity>){
        recyclerView.adapter = MyAdapter(this, memoList, this)
    }

    override fun OnDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }


}