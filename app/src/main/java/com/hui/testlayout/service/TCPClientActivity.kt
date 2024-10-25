package com.hui.testlayout.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.hui.testlayout.R
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.Date


class TCPClientActivity : Activity(), OnClickListener {


    private var mSendButton: Button? = null
    private var mMessageTextView: TextView? = null
    private var mMessageEditText: EditText? = null
    private var mPrintWriter: PrintWriter? = null
    private var mClientSocket: Socket? = null


    private var mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MESSAGE_RECEIVE_NEW_MSG -> {
                    mMessageTextView?.text =
                        mMessageTextView?.text.toString() + (msg.obj as? String ?: "")
                }

                MESSAGE_SOCKET_CONNECTED -> {
                    mSendButton?.isEnabled = true
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tcpclient)
        mMessageTextView = findViewById(R.id.msg_container)
        mSendButton = findViewById(R.id.send)
        mSendButton?.setOnClickListener(this)
        mMessageEditText = findViewById(R.id.msg)
        // 启动tcp服务
        val service = Intent(this, TCPServerService::class.java)
        startService(service)
        // 开启连接服务端的线程
        Thread { connectTCPServer() }.start()
    }


    override fun onClick(v: View?) {
        if (v == mSendButton) {
            val msg = mMessageEditText?.text.toString()
            if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
                mPrintWriter?.println(msg)
                mMessageEditText?.setText("")
                val time = formatDateTime(System.currentTimeMillis())
                val showedMsg = "self $time:$msg\n"
                mMessageTextView?.text = mMessageTextView?.text.toString() + showedMsg
            }
        }
    }

    override fun onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket?.shutdownInput()
                mClientSocket?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        super.onDestroy()
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDateTime(time: Long): String {
        return SimpleDateFormat("(HH:mm:ss)").format(Date(time))
    }

    private fun connectTCPServer() {
        var socket: Socket? = null
        while (socket == null) {
            try {
                socket = Socket("localhost", 8688)
                mClientSocket = socket
                mPrintWriter =
                    PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED)
                println("connect server success.")
            } catch (e: Exception) {
                SystemClock.sleep(1000)
                println("connect tcp server failed,retrying...")
                e.printStackTrace()
            }
        }

        // 接收服务端的消息
        try {
            val br = BufferedReader(InputStreamReader(socket.getInputStream()))
            while (!this.isFinishing) {
                val msg = br.readLine()
                println("receive: $msg")
                if (msg != null) {
                    val time = formatDateTime(System.currentTimeMillis())
                    val showedMsg = "server $time:$msg\n"
                    // 发送消息
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget()
                }
            }
            println("quit...")
            mPrintWriter?.close()
            br.close()
        } catch (e: IOException) {
            e.printStackTrace();
        }
    }

    companion object {
        private const val MESSAGE_SOCKET_CONNECTED = 2
        private const val MESSAGE_RECEIVE_NEW_MSG = 1
    }

}