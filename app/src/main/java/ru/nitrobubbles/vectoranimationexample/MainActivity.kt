package ru.nitrobubbles.vectoranimationexample;

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var logoView: ImageView
    var compositeSubscription = CompositeSubscription()
    val animTime = 8400L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        logoView = findViewById(R.id.vector_image) as ImageView;

    }

    override fun onResume() {
        super.onResume()
        compositeSubscription.add(Observable.interval(animTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { restartAnimation() }
                .doOnNext { restartAnimation() }
                .subscribe())
    }

    override fun onPause() {
        super.onPause()
        compositeSubscription.clear()
    }

    private fun restartAnimation() {
        var logo = logoView.drawable as Animatable;
        if (logo.isRunning)
            logo.stop()
        logo.start()
    }
}
