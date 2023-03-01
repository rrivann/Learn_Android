package com.dicoding.myrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myrecyclerview.databinding.ItemRowHeroBinding

class ListHeroAdapter(private val listHero: ArrayList<Hero>, private val onClick: (Hero) -> Unit) :
    RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

//    private lateinit var onItemClickCallback: OnItemClickCallback

//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listHero[position]
//        holder.imgPhoto.setImageResource(photo)

        Glide.with(holder.itemView.context).load(photo).into(holder.binding.imgItemPhoto)
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDescription.text = description

//        holder.itemView.setOnClickListener {
//            Toast.makeText(
//                holder.itemView.context,
//                "Kamu memilih " + listHero[holder.adapterPosition].name,
//                Toast.LENGTH_SHORT
//            ).show()
//        }

//        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHero[holder.adapterPosition]) }

        holder.bind(listHero[position])
    }

    override fun getItemCount(): Int = listHero.size

    inner class ListViewHolder(var binding: ItemRowHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
//        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
//        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)

        fun bind(hero: Hero) {
            itemView.setOnClickListener {
                onClick(hero)
            }

        }
    }

//    interface OnItemClickCallback {
//        fun onItemClicked(data: Hero)
//    }

}