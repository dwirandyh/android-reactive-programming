package com.dwirandyh.rxdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"
    val greeting: String = "Hello From RxKotlin"
    lateinit var observable: Observable<String>

    //lateinit var myObserver: Observer<String>
    // lateinit var disposable: Disposable

    lateinit var myObserver: DisposableObserver<String>
    lateinit var myObserver2: DisposableObserver<String>

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observable = Observable.just(greeting)

        // observable schedulers.io
        observable.subscribeOn(Schedulers.io())

        // observable schedulers mainThread/UIThread
        observable.observeOn(AndroidSchedulers.mainThread())

        //myObserver = getObserver()
        //observable.subscribe(myObserver)

        /*
        myObserver = object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.i(TAG, "onComplete Invoked")
            }

            override fun onNext(t: String) {
                Log.i(TAG, "onNext Invoked")
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, "onError Invoked")
            }

        }

        observable.subscribe(myObserver)

        myObserver2 = object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.i(TAG, "onComplete Invoked")
            }

            override fun onNext(t: String) {
                Log.i(TAG, "onNext Invoked")
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, "onError Invoked")
            }

        }

        observable.subscribe(myObserver2)


        compositeDisposable.addAll(myObserver2, myObserver)
        */
        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(myObserver)
        )

        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(myObserver2)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        //disposable.dispose()

        //myObserver.dispose()
        compositeDisposable.clear()
    }
}
