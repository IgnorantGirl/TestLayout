// IBookManager.aidl
package com.hui.testlayout.aidl;
import com.hui.testlayout.aidl.Book;

// Declare any non-default types here with import statements
interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}