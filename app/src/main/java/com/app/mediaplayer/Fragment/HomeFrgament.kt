package com.app.mediaplayer.Fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mediaplayer.R
import com.app.mediaplayer.activity.MainActivity
import com.app.mediaplayer.activity.MainActivity.Companion.videofiles
import com.app.mediaplayer.adapter.VideoPlayerAdapter
import com.app.mediaplayer.model.VideoFiles
import com.khizar1556.mkvideoplayer.MKPlayerActivity
import net.alhazmy13.mediapicker.Video.VideoPicker
import java.io.File

class HomeFrgament : Fragment() {

    var recyclerView : RecyclerView? = null
    var videoPlayerAdapter : VideoPlayerAdapter? = null
    var directory : File? =null
    var boolean_permission:Boolean ?=null
    var perms = arrayOf("android.permission.FINE_LOCATION", "android.permission.CAMERA")
    lateinit var progressDialog : ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view  = inflater.inflate(R.layout.fragment_home_frgament, container, false)

        findViewById(view);
        setadapter()
        return view
    }

    fun findViewById(view:View){
        progressDialog = ProgressDialog(context)
        recyclerView = view.findViewById(R.id.recyclerview)
        directory = File("/storage/");
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        permissionforVideo()
    }

    fun setadapter(){
        videoPlayerAdapter = VideoPlayerAdapter(requireContext(), videofiles)
        recyclerView?.adapter = videoPlayerAdapter
    }

    fun permissionforVideo(){
        if((ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)){

            if((ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,android.Manifest.permission.READ_EXTERNAL_STORAGE))){

            }else{

                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

            }
        }else{

            videofiles =getAllVideos(requireContext())
            videoPlayerAdapter?.notifyDataSetChanged()

        }
    }

    fun getAllVideos(context : Context) : ArrayList<VideoFiles>{

        var tempVideoFiles : ArrayList<VideoFiles> = ArrayList();
        var uri : Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.DATE_ADDED,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.DISPLAY_NAME)

        var cursor : Cursor? = context.contentResolver.query(uri,projection,null,null,null)
        if(cursor!=null){
            while (cursor.moveToNext()){
                var id : String = cursor.getString(0)
                var path : String = cursor.getString(1)
                var title : String = cursor.getString(2)
                var size : String = cursor.getString(3)
                var dataadded = cursor.getString(4)
                var duration : String = cursor.getString(5)
                var fileName = cursor.getString(6)

                var videoFiles = VideoFiles(id,path,title,fileName,size,dataadded,duration)
                Log.d("PathPath",path+"\n\n"+duration)
                tempVideoFiles.add(videoFiles)
            }
            cursor.close()

        }
        return tempVideoFiles;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() &&  grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    progressDialog.setMessage("Please wait")
                    progressDialog.show()
                    videofiles =getAllVideos(requireContext())
                    videoPlayerAdapter = VideoPlayerAdapter(requireContext(), videofiles)
                    recyclerView?.adapter = videoPlayerAdapter
//                    Toast.makeText(context,"size"+videofiles?.size,Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                } else {

                }
                return
            }

            else -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){
            val mPaths: List<String> = data?.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH)!!

            MKPlayerActivity.configPlayer(context as Activity?).play(mPaths.get(0))

        }
    }


}