package org.d3if4075.Persegiku

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if4075.Persegiku.data.BangunDatar
import org.d3if4075.Persegiku.databinding.ItemListBangunDatarBinding
import org.d3if4075.Persegiku.network.BangunDatarApi

class ListBangunDatarAdapter : RecyclerView.Adapter<ListBangunDatarAdapter.ViewHolder>() {
    private val data = mutableListOf<BangunDatar>()

    fun updateData(newData: List<BangunDatar>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemListBangunDatarBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(bangunR: BangunDatar) = with(binding){
            namaTextView.text = bangunR.nama
            volumeTextView.text = root.context.getString(R.string.rumus_luas, bangunR.luas)
            Glide.with(imageView.context).load(BangunDatarApi.getBangunDatarUrl(bangunR.imageId)).error(
                R.drawable.ic_baseline_broken_image_24).into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBangunDatarBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}