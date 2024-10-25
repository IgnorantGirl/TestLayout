package com.hui.testlayout.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.Random

class TCPServerService: Service() {
    private var mIsServiceDestroyed = false
    private var mDefinedMessage = arrayOf("你好啊，哈哈", "请问你叫什么名字呀?",
    "今天北京天气不错啊，shy", "你知道吗?我可是可以和多个人同时聊天的哦", "给你讲个笑话吧:据说爱笑的人运气不会太差，不知道真假。")

    override fun onCreate() {
        Thread(TcpServer()).start()
        super.onCreate()
    }

    override fun onDestroy() {
        mIsServiceDestroyed = true
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private inner class TcpServer : Runnable {
        override fun run() {
            var serverSocket: ServerSocket? = null
            try {
                serverSocket = ServerSocket(8688)
            } catch (e: Exception) {
                System.err.println("establish tcp server failed,port:8688")
                e.printStackTrace()
                return
            }
            while(!mIsServiceDestroyed) {
                try {
                    // accept()方法会一直阻塞，直到有客户端来连接
                    val client = serverSocket.accept()
                    println("accept");
                    Thread {
                        responseClient(client)
                    }.start()
                } catch (e: Exception) {
                    System.err.println("accept client failed")
                    e.printStackTrace()
                }
            }
        }
    }

    private fun responseClient(client: Socket) {
        // 用于接收客户端信息
        val bufferedReader = BufferedReader(InputStreamReader(client.getInputStream()))
        // 用户向客户端发送消息
        val out = PrintWriter(BufferedWriter(OutputStreamWriter(client.getOutputStream())), true)
        out.println("欢迎来到聊天室！")
        while(!mIsServiceDestroyed){
            val str = bufferedReader.readLine()
            println("msg from client: $str")
            if(str == null) break // 客户端断开连接
            val i = Random().nextInt(mDefinedMessage.size)
           val msg = mDefinedMessage[i]
            out.println(msg)
            println("msg to client: $msg")
        }
        println("client disconnect")
        // 关闭流
        try {
            bufferedReader.close()
            out.close()
        } catch (e: Exception) {
            System.err.println("close bufferedReader failed")
            e.printStackTrace()
        } finally {
            client.close()
        }
    }
}



