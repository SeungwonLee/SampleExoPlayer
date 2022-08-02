package com.google.android.exoplayer2.demo

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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

        textureView = findViewById(R.id.textureView)
        exoPlayer = ExoPlayer.Builder(this)
                .build().also {
                    it.setVideoTextureView(textureView)
                }

        val targetSdkVersion = applicationContext.applicationInfo.targetSdkVersion
        val compileSdkVersion = applicationContext.applicationInfo.compileSdkVersion
        Log.d("ExamplePlayerActivity", "targetSdkVersion: $targetSdkVersion compileSdkVersion $compileSdkVersion")

        startActivityForResult(
                Intent(Intent.ACTION_GET_CONTENT)
                        .setType("video/*"),
                REQUEST_CODE_PICK
        )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_PICK -> {
                if (resultCode == RESULT_OK) {
                    val pathSegments = data?.data?.lastPathSegment?.split(":")?.toTypedArray()
                            ?: return
                    val isImageType = pathSegments[0] == "image"
                    val id = pathSegments[1]
                    val uri: Uri = getContentUri(id, isImageType)
                    
                    exoPlayer.setMediaItem(MediaItem.fromUri(uri))
                    exoPlayer.prepare()
                    exoPlayer.play()
                }
            }
        }
    }

    private fun getContentUri(id: String, isImage: Boolean): Uri {
        val contentUri = if (isImage) MediaStore.Images.Media.EXTERNAL_CONTENT_URI else MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        return ContentUris.withAppendedId(contentUri, id.toLong())
    }

    companion object {
        private const val REQUEST_CODE_PICK = 1
    }
}
