package com.hui.testlayout.provider

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.hui.testlayout.R
import com.hui.testlayout.aidl.Book


class ProviderActivity : Activity() {

    private val TAG = "ProviderActivity"
    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)
        val bookUri: Uri = Uri.parse("content://com.hui.testlayout.provider/book")
        val values = ContentValues()
        values.put("_id", "6")
        values.put("name", "程序设计的艺术")
        contentResolver.insert(bookUri, values)
        // 查询book表所有数据
        val bookCursor = contentResolver.query(bookUri, arrayOf("_id", "name"), null, null, null)
        while (bookCursor!!.moveToNext()) {
            val book = Book()
            book.bookId = bookCursor.getInt(0)
            book.bookName = bookCursor.getString(1)
            Log.d(TAG, "query book:$book")
        }
        bookCursor.close()

        val userUri = Uri.parse("content://com.hui.testlayout.provider/user")
        val userCursor = contentResolver.query(userUri, arrayOf("_id", "name","sex"), null, null, null)
        while (userCursor!!.moveToNext()) {
            val user = User()
            val id = userCursor.getInt(0)
            val name = userCursor.getString(1)
            val sex = userCursor.getString(2)
            Log.d(TAG, "query user:id=$id,name=$name,sex=$sex")

            user.id = id
            user.name = name
            user.sex = sex
            Log.d(TAG, "query user:$user")
        }
        userCursor.close()
    }
}

class User {
    var id: Int = 0
    var name: String? = null
    var sex: String? = null

    override fun toString(): String {
        return "User(id=$id, name='$name', sex='$sex')"
    }
}
