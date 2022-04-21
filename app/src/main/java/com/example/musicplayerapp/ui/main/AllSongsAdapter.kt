package com.example.musicplayerapp.ui.main


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.constant.AllSongsModel

class AllSongsAdapter  ( val context:Context,
                        private var onItemClicked: ((song: AllSongsModel) -> Unit)) :
    RecyclerView.Adapter<AllSongsAdapter.ViewHolder>() {

    //on below line we are creating a variable for our all notes list.
    private val dataSet = ArrayList<AllSongsModel>()
   // private val dataSet: List<AllSongsModel>

    //below method is use to update our list of notes.
    fun updateList(newList: List<AllSongsModel>) {
        //on below line we are clearing our notes array list/
        dataSet.clear()
        //on below line we are adding a new list to our all notes list.
        dataSet.addAll(newList)
        //on below line we are calling notify data change method to notify our adapter.
        notifyDataSetChanged()
    }

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
        viewHolder.imgSong.setImageResource(dataSet[position].image)
        viewHolder.tvSongName.text = dataSet[position].songName
        viewHolder.tvDuration.text = dataSet[position].duration


        viewHolder.itemView.setOnClickListener {

            onItemClicked(dataSet[position])

            /*  val intent = Intent(context,Player::class.java)
              intent.putExtra("path",dataSet[position].path)
              intent.putExtra("name",dataSet[position].songName)
              intent.putExtra("duration",dataSet[position].duration)
              intent.putExtra("LIST", dataSet as Serializable?)
              context.startActivity(intent)*/

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}