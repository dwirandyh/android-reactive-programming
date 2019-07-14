package com.dwirandyh.rxbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var disposable: Disposable = editName.textChanges()
            .subscribe {
                lbl.text = it
            }

        var disposable2: Disposable = btnTest.clicks()
            .subscribe {
                editName.setText("")
                lbl.text = ""
            }

    }
}
