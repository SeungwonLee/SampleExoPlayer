package com.google.android.exoplayer2.demo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.SurfaceView
import android.view.TextureView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class ExamplePlayerActivity : AppCompatActivity() {
    private lateinit var textureView: TextureView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_player)

        exoPlayer = ExoPlayer.Builder(this)
                .build()

        textureView = findViewById(R.id.textureView)
        exoPlayer.setVideoTextureView(textureView)
        // TODO Set content uri.
        
        exoPlayer.prepare()
        exoPlayer.play()

        val targetSdkVersion = applicationContext.applicationInfo.targetSdkVersion
        val compileSdkVersion = applicationContext.applicationInfo.compileSdkVersion
        Log.d("ExamplePlayerActivity", "targetSdkVersion: $targetSdkVersion compileSdkVersion $compileSdkVersion")
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
//        textureView.onPause()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.play()
//        textureView.onResume()
    }
}
