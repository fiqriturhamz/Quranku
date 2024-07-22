package com.muhammadfiqrit.quranku.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.muhammadfiqrit.quranku.core.databinding.ItemListTafsirBinding
import com.muhammadfiqrit.quranku.core.domain.model.tafsir.Tafsir

class TafsirAdapter(private val listTafsir: List<Tafsir>) :
    RecyclerView.Adapter<TafsirAdapter.TafsirViewHolder>() {


    inner class TafsirViewHolder(var binding: ItemListTafsirBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TafsirViewHolder {
        return TafsirViewHolder(
            ItemListTafsirBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TafsirViewHolder, position: Int) {
        val tafsir = listTafsir[position]
        holder.apply {
            binding.tvTafsir.text = tafsir.teks
            binding.nomorAyat.text = tafsir.ayat.toString()
        }
    }

    override fun getItemCount(): Int = listTafsir.size
}