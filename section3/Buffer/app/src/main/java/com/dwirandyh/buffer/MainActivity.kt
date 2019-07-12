package com.dwirandyh.buffer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myObservable = Observable.range(1, 20)

        myObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(4)
            .subscribe(
                {
                    Log.i(TAG, " came to onNext")

                    for (intValue in it){
                        Log.i(TAG, " int value is $intValue")
                    }
                },
                {
                    Log.i(TAG, " On Error invoked ${it.message}")
                }
            )
    }
}
