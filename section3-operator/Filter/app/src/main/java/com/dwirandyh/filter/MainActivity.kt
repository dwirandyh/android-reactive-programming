package com.dwirandyh.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myObservable = Observable.range(1, 20)

        myObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter {
                return@filter it % 3 == 0
            }
            .subscribe(
                {
                    Log.i("MainActivity", " onNext invoked $it")
                },
                {
                    Log.e("MainActivity", " onError invoked")
                }
            )
    }
}
