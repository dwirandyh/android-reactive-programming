package com.dwirandyh.distinct

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

        var numberObservable = Observable.just(1, 2, 3, 4, 5, 5, 5, 6, 5)

        numberObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .distinct()
            .subscribe(
                {
                    Log.i("MainActivity", " come to onNext $it")
                },
                {
                    Log.i("MainActivity", " onError Invoked")
                }
            )
    }
}
