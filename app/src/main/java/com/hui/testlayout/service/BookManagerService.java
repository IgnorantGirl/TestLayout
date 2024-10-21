package com.hui.testlayout.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hui.testlayout.aidl.Book;
import com.hui.testlayout.aidl.IBookManager;
import com.hui.testlayout.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<IOnNewBookArrivedListener>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (!mListenerList.contains(listener)) {
                mListenerList.add(listener);
            } else {
                Log.d(TAG, "already exists.");
            }
            Log.d(TAG, "registerListener,size:" + mListenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (mListenerList.contains(listener)) {
                mListenerList.remove(listener);
                Log.d(TAG, "unregister listener succeed.");
            } else {
                Log.d(TAG, "not found,can not unregister.");
            }
            Log.d(TAG, "unregisterListener,current size:" + mListenerList.size());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "IOS"));
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        Log.d(TAG, "onNewBookArrived,notify listeners:" + mListenerList.size());
        for (IOnNewBookArrivedListener listener : mListenerList) {
            Log.d(TAG, "onNewBookArrived,notify listener:" + listener);
            listener.onNewBookArrived(book);
        }
    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            // do background work here
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new Book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
