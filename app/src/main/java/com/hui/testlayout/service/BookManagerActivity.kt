package com.hui.testlayout.service

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.RemoteException
import android.util.Log
import com.hui.testlayout.R
import com.hui.testlayout.aidl.Book
import com.hui.testlayout.aidl.IBookManager
import com.hui.testlayout.aidl.IOnNewBookArrivedListener


class BookManagerActivity : Activity() {
    private val TAG = BookManagerActivity::class.java.simpleName

    private val MESSAGE_NEW_BOOK_ARRIVED = 1

    private var mRemoteBookManager: IBookManager? = null

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_NEW_BOOK_ARRIVED -> {
                    Log.d(TAG, "receive new book :${msg.obj}")
                }

                else -> {
                    super.handleMessage(msg)
                }
            }
        }
    }

    private val mOnNewBookArrivedListener: IOnNewBookArrivedListener =
        object : IOnNewBookArrivedListener.Stub() {
            @Throws(RemoteException::class)
            override fun onNewBookArrived(newBook: Book) {
                mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget()
            }
        }


    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val bookManager = IBookManager.Stub.asInterface(service)
            mRemoteBookManager = bookManager
            val list = bookManager.getBookList()
            Log.d(TAG, "query book list,list type: ${list.javaClass.canonicalName}")
            Log.d(TAG, "query book list :$list")
            val book = Book(3, "android进阶")
            bookManager.addBook(book)
            Log.d(TAG, "new book added: $book")
            val newList = bookManager.getBookList()
            Log.d(TAG, "query new book list :$newList")
            bookManager.registerListener(mOnNewBookArrivedListener)
        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        val intent = Intent(this, BookManagerService::class.java)
        bindService(intent, mConnection, BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager?.asBinder()?.isBinderAlive == true) {
            try {
                Log.d(TAG, "unregister listener :${mOnNewBookArrivedListener}")
                mRemoteBookManager?.unregisterListener(mOnNewBookArrivedListener)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        unbindService(mConnection)
        super.onDestroy()
    }
}