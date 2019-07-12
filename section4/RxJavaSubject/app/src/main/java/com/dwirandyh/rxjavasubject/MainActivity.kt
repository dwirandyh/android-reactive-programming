package com.dwirandyh.rxjavasubject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

class MainActivity : AppCompatActivity() {

    private val TAG = "myApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        asyncSubjectDemo()
//        behaviorSubject()
//        publishSubject()
        replaySubject()
    }

    private fun asyncSubjectDemo() {
        val observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val asyncSubject = AsyncSubject.create<String>()
        observable.subscribe(asyncSubject)

        asyncSubject.subscribe(getFirstObserver())
        asyncSubject.subscribe(getSecondObserver())
        asyncSubject.subscribe(getThirdObserver())
    }


    private fun behaviorSubject() {

        val behaviorSubject = BehaviorSubject.create<String>()

        behaviorSubject.subscribe(getFirstObserver())

        behaviorSubject.onNext("JAVA")
        behaviorSubject.onNext("KOTLIN")
        behaviorSubject.onNext("XML")

        behaviorSubject.subscribe(getSecondObserver())

        behaviorSubject.onNext("JSOn")
        behaviorSubject.onComplete()

        behaviorSubject.subscribe(getThirdObserver())
    }

    private fun publishSubject() {

        val publishSubject = PublishSubject.create<String>()

        publishSubject.subscribe(getFirstObserver())

        publishSubject.onNext("JAVA")
        publishSubject.onNext("KOTLIN")
        publishSubject.onNext("XML")

        publishSubject.subscribe(getSecondObserver())

        publishSubject.onNext("Json")
        publishSubject.onComplete()

        publishSubject.subscribe(getThirdObserver())
    }

    private fun replaySubject() {

        val replaySubject = ReplaySubject.create<String>()

        replaySubject.subscribe(getFirstObserver())

        replaySubject.onNext("JAVA")
        replaySubject.onNext("KOTLIN")
        replaySubject.onNext("XML")

        replaySubject.subscribe(getSecondObserver())

        replaySubject.onNext("Json")
        replaySubject.onComplete()

        replaySubject.subscribe(getThirdObserver())
    }


    private fun getFirstObserver(): Observer<String> {

        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {


                Log.i(TAG, " First Observer onSubscribe ")
            }

            override fun onNext(s: String) {

                Log.i(TAG, " First Observer Received $s")

            }

            override fun onError(e: Throwable) {

                Log.i(TAG, " First Observer onError ")
            }

            override fun onComplete() {

                Log.i(TAG, " First Observer onComplete ")

            }
        }
    }

    private fun getSecondObserver(): Observer<String> {

        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {


                Log.i(TAG, " Second Observer onSubscribe ")
            }

            override fun onNext(s: String) {

                Log.i(TAG, " Second Observer Received $s")

            }

            override fun onError(e: Throwable) {

                Log.i(TAG, " Second Observer onError ")
            }

            override fun onComplete() {

                Log.i(TAG, " Second Observer onComplete ")

            }
        }
    }

    private fun getThirdObserver(): Observer<String> {

        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {


                Log.i(TAG, " Third Observer onSubscribe ")
            }

            override fun onNext(s: String) {

                Log.i(TAG, " Third Observer Received $s")

            }

            override fun onError(e: Throwable) {

                Log.i(TAG, " Third Observer onError ")
            }

            override fun onComplete() {

                Log.i(TAG, " Third Observer onComplete ")

            }
        }
    }
}
