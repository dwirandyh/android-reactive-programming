package com.dwirandyh.fromarraydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = "MYAPP"
    private lateinit var myObservable: Observable<String>
    private lateinit var myObserver: DisposableObserver<String>

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var greetings = arrayOf("Hello A", "Hello B", "Hello C")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // The * operator is known as the Spread Operator in Kotlin.
        // for passing array info function
        myObservable = Observable.fromArray(*greetings)

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<String> {

        myObserver = object:  DisposableObserver<String>(){
            override fun onComplete() {
                Log.i(TAG, " onComplete Invoked")
            }

            override fun onNext(t: String) {
                Log.i(TAG, " onNext Invoked $t")
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, " onError Invoked ${e.message}")
            }

        }

        return myObserver
    }
}
