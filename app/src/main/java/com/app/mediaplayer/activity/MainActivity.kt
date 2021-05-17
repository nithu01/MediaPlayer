package com.app.mediaplayer.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mediaplayer.Fragment.HomeFrgament
import com.app.mediaplayer.R
import com.app.mediaplayer.adapter.VideoPlayerAdapter
import com.app.mediaplayer.model.VideoFiles
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.khizar1556.mkvideoplayer.MKPlayerActivity
import net.alhazmy13.mediapicker.Video.VideoPicker
import java.io.File


class MainActivity : AppCompatActivity() {

    var bottomnavigation  : BottomNavigationView ?=null
    var videoPlayerAdapter : VideoPlayerAdapter? = null
    companion object{
        var videofiles : ArrayList<VideoFiles> ? = ArrayList();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById();
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.option_menu, menu)
//        val mSearch: MenuItem = menu!!.findItem(R.id.appSearchBar)
//        val mSearchView: SearchView = mSearch.getActionView() as SearchView
//        mSearchView.setQueryHint("Search")
//        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                videoPlayerAdapter?.getFilter()?.filter(query)
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                videoPlayerAdapter?.getFilter()?.filter(newText)
//                return false
//            }
//        })
//
//        return super.onCreateOptionsMenu(menu)
//    }

    fun findViewById(){
        bottomnavigation = findViewById(R.id.bottomnav)
//        videoPlayerAdapter = VideoPlayerAdapter(this@MainActivity, MainActivity.videofiles)
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.nav_music, R.id.nav_tv,R.id.nav_fav
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomnavigation?.setupWithNavController(navController)
    }

}