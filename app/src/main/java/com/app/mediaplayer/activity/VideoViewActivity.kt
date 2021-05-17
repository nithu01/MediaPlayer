package com.app.mediaplayer.activity

import android.app.PictureInPictureParams
import android.content.pm.ActivityInfo
import android.graphics.Point
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.app.mediaplayer.R
import com.app.mediaplayer.activity.MainActivity.Companion.videofiles
import java.util.*


class VideoViewActivity : AppCompatActivity() ,View.OnClickListener{

    var arrayList = ArrayList(
        Arrays.asList(
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        )
    )

    var position: Int?=null
    var videoview: VideoView ?=null
    var linearLayout : RelativeLayout? =null
    var videilayout : RelativeLayout? =null
    lateinit var mediaController: MediaController
    lateinit var pip_button : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        findviewbyid()
        playvideo()
        touchlistener()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun findviewbyid(){
        pip_button= findViewById(R.id.pip_button)

        pip_button.setOnClickListener(this)
        videoview= findViewById(R.id.videoView)
        position = intent.getIntExtra("position",-1)
        Log.d("TAG","position"+position);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        supportActionBar?.hide()
        linearLayout = findViewById(R.id.linearlayout)
        mediaController = MediaController(this)
        val orientation = this.resources.configuration.orientation

    }

    fun touchlistener(){
        linearLayout?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
               mediaController.show()
                return true
            }
        })
    }

    fun playvideo(){

        mediaController.setAnchorView(videoview)
        
        videoview?.setMediaController(mediaController)
        videoview?.setVideoPath(videofiles?.get(position!!)?.path)
        Log.d("TAG","Path"+videofiles?.get(position!!)?.path);
        videoview?.requestFocus()

        videoview?.setOnPreparedListener{
//            it.setOnVideoSizeChangedListener(object : MediaPlayer.OnVideoSizeChangedListener {
//                override fun onVideoSizeChanged(
//                    mp: MediaPlayer?,
//                    width: Int,
//                    height: Int
//                ) {
//                    /*
//                 * add media controller
//                 */
//                    mediaController = MediaController(this@VideoViewActivity)
//                    videoview?.setMediaController(mediaController)
//                    /*
//                 * and set its position on screen
//                 */mediaController.setAnchorView(videoview)
//                }
//            })
            videoview?.start()
        }

        videoview?.setOnCompletionListener {
            videoview?.setVideoPath(videofiles?.get(position!!)?.path)
            videoview?.start()
        }

    }

    override fun onClick(v: View?) {
        if(v==pip_button){

            val d = windowManager
                    .defaultDisplay
            val p = Point()
            d.getSize(p)
            val width: Int = p.x
            val height: Int = p.y


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val ratio = Rational(width, height)
                val pip_Builder = PictureInPictureParams.Builder()
                pip_Builder.setAspectRatio(ratio).build()
                enterPictureInPictureMode(pip_Builder.build())
            }else{
                Toast.makeText(this@VideoViewActivity,"Please allow picture in picture feature",Toast.LENGTH_SHORT).show()
            }
        }
    }

}