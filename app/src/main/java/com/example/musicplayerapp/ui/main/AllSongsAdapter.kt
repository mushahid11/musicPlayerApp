package com.example.musicplayerapp.ui.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.databinding.SongsItemBinding

class AllSongsAdapter(
    private val list: List<AllSongsModel>, private var onItemClicked: (song: AllSongsModel) -> Unit
) :
    RecyclerView.Adapter<AllSongsAdapter.ViewHolder>() {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {


        val binding = SongsItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        return ViewHolder(binding)

    }



    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        with(holder) {
            with(list[position]) {


                binding.imgMusic.setImageResource(this.image)
                binding.tvSongName.text = this.songName
                binding.tvDuration.text = this.duration


                itemView.setOnClickListener {
                    onItemClicked(list[position])
                }
            }

        }


    }

    inner class ViewHolder(val binding: SongsItemBinding) : RecyclerView.ViewHolder(binding.root)

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size

}