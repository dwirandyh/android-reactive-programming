package com.dwirandyh.rxjavaoperator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val greeting = "Hello from RxJava"
    private lateinit var myObservable: Observable<String>
    private lateinit var myObserver: DisposableObserver<String>

    private val arrValues: Array<String> = arrayOf("Hello A", "Hello B", "Hello C")
    private lateinit var myObservableArray: Observable<Array<String>>
    private lateinit var myObserverArray: DisposableObserver<Array<String>>

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        myObservable = Observable.just(greeting)

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )


        myObservableArray = Observable.just(arrValues)

        compositeDisposable.add(
            myObservableArray
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserverArray())
        )
    }


    private fun getObserver(): DisposableObserver<String> {
        myObserver = object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.i(TAG, " onComplete invoked")
            }

            override fun onNext(t: String) {
                Log.i(TAG, " on Error invoked $t")
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, " onError invoked")
            }

        }

        return myObserver
    }

    private fun getObserverArray(): DisposableObserver<Array<String>> {
        myObserverArray = object : DisposableObserver<Array<String>>() {
            override fun onComplete() {
                Log.i(TAG, " onComplete Array Invoked")
            }

            override fun onNext(t: Array<String>) {
                Log.i(TAG, " onComplete Array Invoked $t")
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, " onError Array Invoked")
            }
        }

        return myObserverArray
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
