package com.dwirandyh.rxdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"
    val greeting: String = "Hello From RxKotlin"
    lateinit var observable: Observable<String>
    lateinit var myObserver: Observer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observable = Observable.just(greeting)
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
}
