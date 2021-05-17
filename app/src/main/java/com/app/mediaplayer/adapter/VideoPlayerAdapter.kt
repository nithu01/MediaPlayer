package com.app.mediaplayer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.mediaplayer.R
import com.app.mediaplayer.activity.VideoViewActivity
import com.app.mediaplayer.databinding.VideoDetailsList
import com.app.mediaplayer.model.VideoFiles
import java.io.File

class VideoPlayerAdapter(var requireContext : Context, var videoArrayList: ArrayList<VideoFiles>?) : RecyclerView.Adapter<VideoPlayerAdapter.MyHolder>() ,Filterable {

    var countryFilterList = java.util.ArrayList<VideoFiles>()
    var context : Context ? =null

    init {
        this.context=requireContext
        countryFilterList = videoArrayList as java.util.ArrayList<VideoFiles>
    }

    class MyHolder(view : VideoDetailsList) : RecyclerView.ViewHolder(view.root){

        lateinit var view: VideoDetailsList
        init {
            this.view = view
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
//        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_layout,parent,false)
//        return MyHolder(view)
        val videodetailslist : VideoDetailsList = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_video_layout,parent,false)
        return MyHolder(videodetailslist)
    }

    override fun getItemCount(): Int {
        return countryFilterList.size;
    }

    override fun onBindViewHolder(holder: VideoPlayerAdapter.MyHolder, position: Int) {
        var videoFiles = countryFilterList.get(position)
        holder.view.videodetils= videoFiles
        holder.view.root.setOnClickListener{
            requireContext.startActivity(Intent(requireContext,VideoViewActivity::class.java).putExtra("position",holder.adapterPosition).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = videoArrayList as java.util.ArrayList<VideoFiles>
                } else {
                    val resultList = java.util.ArrayList<VideoFiles>()
                    for (row in videoArrayList!!) {
                        if (row.fileName.contains(constraint.toString().toLowerCase()) || row.fileName.contains(constraint.toString())){
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as java.util.ArrayList<VideoFiles>
                notifyDataSetChanged()
            }
        }
    }

}