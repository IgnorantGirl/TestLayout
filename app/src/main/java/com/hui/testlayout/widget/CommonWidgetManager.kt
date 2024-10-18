package com.hui.testlayout.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class CommonWidgetManager {

    companion object {
        val TAG = "CommonWidgetManager"

        /**
         * 是否已经安装对应的桌面组件
         * @param Class 组件类
         * @return true/false
         */
        @JvmStatic
        fun isExitAppWidget(cls: Class<*>, context: Context): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                return false
            }
            try {
                val appWidgetManager =
                    context.getSystemService(AppWidgetManager::class.java) as AppWidgetManager
                val appWidgetProvider =
                    ComponentName(context, cls)
                val appIds = appWidgetManager.getAppWidgetIds(appWidgetProvider)
                val isExitAppId = appIds.size > 0
                return isExitAppId
            } catch (e: Throwable) {
                return false
            }
        }

        /**
         * 添加组件
         * @param class 组件类
         */
        @JvmStatic
        fun addWidget(cls: Class<*>, listener: IRequestWidgetListener?, context: Context) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                return
            }
            try {
                val appWidgetManager =
                    context.getSystemService(AppWidgetManager::class.java)
                val appWidgetProvider =
                    ComponentName(context, cls)
                if (appWidgetManager?.isRequestPinAppWidgetSupported == true && !isExitAppWidget(cls, context)) {
                    requestPinAppwidget(context,
                        appWidgetManager, appWidgetProvider, cls, listener)
                }
            } catch (e: Exception) {

            }
        }

        /**
         * 请求系统生成桌面组件(Android8.0及以上系统)
         *
         * @param context          上下文
         * @param appWidgetManager AppWidgetManager
         * @param provider         ComponentName
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        private fun requestPinAppwidget(context: Context,
                                        appWidgetManager: AppWidgetManager,
                                        provider: ComponentName,
                                        cls: Class<*>,
                                        listener: IRequestWidgetListener?) {
            try {
                var flags = PendingIntent.FLAG_UPDATE_CURRENT
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    flags = flags or PendingIntent.FLAG_IMMUTABLE
                }
                val pinnedWidgetCallbackIntent = Intent(context, cls)
                val successCallback = PendingIntent.getBroadcast(
                    context,
                    10001,
                    pinnedWidgetCallbackIntent, flags
                )
                val isSupportInstalled = appWidgetManager.requestPinAppWidget(provider, null, successCallback)
                Log.d(TAG, "系统是否支持安装桌面组件----$isSupportInstalled")
                listener?.requestCallback(isSupportInstalled)
            } catch (e: IllegalStateException) {

            }
        }


        /**
         *  小米和vivo 不支持小组件、暂不添加
         */
        @JvmStatic
        fun isSupportDevice(): Boolean {
            val manufacture = Build.MANUFACTURER.toLowerCase()
            if (manufacture.contains("vivo")
                || manufacture.contains("xiaomi")) {
                return false
            }
            return true
        }
    }

}