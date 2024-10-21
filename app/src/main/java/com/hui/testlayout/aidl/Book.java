package com.hui.testlayout.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * 图书信息类
 */
public class Book implements Parcelable {


    public int bookId;
    public String bookName;


    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    @NonNull
    @Override
    public String toString() {
        return "Book{" + "bookId=" + bookId + ", bookName='" + bookName + '\'' + '}';
    }
}

