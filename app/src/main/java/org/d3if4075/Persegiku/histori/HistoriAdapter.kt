package org.d3if4075.Persegiku.histori

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if4075.Persegiku.R
import org.d3if4075.Persegiku.data.HitungPersegi
import org.d3if4075.Persegiku.databinding.ItemHistoriBinding
import org.d3if4075.Persegiku.db.PersegiEntity
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter : RecyclerView.Adapter<HistoriAdapter.ViewHolder>() {
    private val data = mutableListOf<PersegiEntity>()

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    fun updateData(newData: List<PersegiEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, mListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }

    fun getContent(position: Int): PersegiEntity{
        return data.get(position)
    }



    class ViewHolder(
        private val binding: ItemHistoriBinding,
        listener:onItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: PersegiEntity) = with(binding) {
            tanggalTextView.text = dateFormatter.format(Date(item.tgl))

            val hasilPersegi = HitungPersegi.hitung(item)

            sisiTextView.text = hasilPersegi.sisi.toString()

            kelilingTextView.text = root.context.getString(R.string.keliling_x, hasilPersegi.keliling)
            luasTextView.text = root.context.getString(R.string.luas_x, hasilPersegi.luas)

        }

//        init {
//            itemView.deleteButton.setOnClickListener{
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }
}