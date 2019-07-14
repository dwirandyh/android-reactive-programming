package com.dwirandyh.skip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var numberObservable = Observable.just(1, 2, 3, 4, 5, 6, 7)

        numberObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .skip(2)
            .subscribe {
                Log.i("MainActivity", "on Next Skip Invoked $it")
            }


        numberObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .skipLast(3)
            .subscribe {
                Log.i("MainActivity", "on Next Skip Last Invoked $it")
            }
    }
}
