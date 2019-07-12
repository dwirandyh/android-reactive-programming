package com.dwirandyh.mapop

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
    private lateinit var myObservable: Observable<Student>
    private lateinit var myObserver: DisposableObserver<Student>


    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        myObservable = Observable.create { emitter ->
            var studentArrayList = getStudents()

            for (student in studentArrayList) {
                emitter.onNext(student)
            }

            emitter.onComplete()
        }



        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.name = it.name.toUpperCase()
                    return@map it
                }
                .subscribeWith(getObserver())
        )

    }


    private fun getObserver(): DisposableObserver<Student> {
        myObserver = object : DisposableObserver<Student>() {
            override fun onComplete() {
                Log.i(TAG, " onComplete invoked")
            }

            override fun onNext(t: Student) {
                Log.i(TAG, " on Error invoked ${t.name}")
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, " onError invoked")
            }

        }

        return myObserver
    }


    private fun getStudents(): ArrayList<Student> {
        var students = ArrayList<Student>()

        var student1 = Student("Student 1", "student1@gmail.com", 28, "2019-09-03")
        var student2 = Student("Student 2", "student2@gmail.com", 28, "2019-09-04")
        var student3 = Student("Student 3", "student3@gmail.com", 28, "2019-09-05")

        students.add(student1)
        students.add(student2)
        students.add(student3)

        return students
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
