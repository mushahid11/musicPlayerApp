package com.example.musicplayerapp.ui.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.constant.AllSongsModel

class AllSongsAdapter(
    private val list: List<AllSongsModel>, private var onItemClicked: (song: AllSongsModel) -> Unit
) :
    RecyclerView.Adapter<AllSongsAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgSong: ImageView = view.findViewById(R.id.imgMusic)
        val tvSongName: TextView = view.findViewById(R.id.tvSongName)
        val tvDuration: TextView = view.findViewById(R.id.tvDuration)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.songs_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.apply {
            imgSong.setImageResource(list[position].image)
            tvSongName.text = list[position].songName
            tvDuration.text = list[position].duration
        }



        viewHolder.itemView.setOnClickListener {
            onItemClicked(list[position])
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size

}