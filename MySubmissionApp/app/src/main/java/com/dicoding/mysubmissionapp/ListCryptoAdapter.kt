package com.dicoding.mysubmissionapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mysubmissionapp.databinding.ItemRowCryptoBinding
import com.bumptech.glide.Glide

class ListCryptoAdapter(
    private val listCrypto: ArrayList<Crypto>,
    private val onClick: (Crypto) -> Unit
) : RecyclerView.Adapter<ListCryptoAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listCrypto.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, _, description, photo) = listCrypto[position]
        Glide.with(holder.itemView.context).load(photo).into(holder.binding.imgItemPhoto)
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDesc.text = description
        holder.bind(listCrypto[position])
    }

    inner class ListViewHolder(var binding: ItemRowCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(crypto: Crypto) {
            itemView.setOnClickListener {
                onClick(crypto)
            }
        }
    }

}