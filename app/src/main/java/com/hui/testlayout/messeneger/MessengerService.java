package com.hui.testlayout.messeneger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {
    private static final String TAG = "messenger_service";

    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.d(TAG, "receive msg from client:" + msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message relpyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVER);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "yes，I have received your message");
                    relpyMessage.setData(bundle);
                    try {
                        client.send(relpyMessage);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }



}
