package com.dwirandyh.rxdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"
    val greeting: String = "Hello From RxKotlin"
    lateinit var observable: Observable<String>
    lateinit var myObserver: Observer<String>

    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observable = Observable.just(greeting)

        // observable schedulers.io
        observable.subscribeOn(Schedulers.io())

        // observable schedulers mainThread/UIThread
        observable.observeOn(AndroidSchedulers.mainThread())

        myObserver = getObserver()

        observable.subscribe(myObserver)
    }

    fun getObserver(): Observer<String> {
        val mySubscriber = object : Observer<String> {
            override fun onComplete() {
                Log.i(TAG, "onComplete Invoked")

            }

            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "onSubscribe Invoked")
                disposable = d
            }

            override fun onNext(t: String) {
                Log.i(TAG, "onNext Invoked")
                tvGreeting.text = t
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, "On error Invoked")
            }

        }
        return mySubscriber
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
