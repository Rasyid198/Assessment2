package org.d3if4075.Persegiku.histori

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if4075.Persegiku.R
import org.d3if4075.Persegiku.databinding.FragmentHistoriBinding
import org.d3if4075.Persegiku.db.PersegiDb
import org.d3if4075.Persegiku.db.PersegiEntity

class HistoriFragment : Fragment() {

    private val viewModel: HistoriViewModel by lazy {
        val db = PersegiDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HistoriViewModel::class.java)
    }

    private lateinit var binding: FragmentHistoriBinding
    private lateinit var myAdapter: HistoriAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoriBinding.inflate(layoutInflater, container, false)

        myAdapter = HistoriAdapter()
        with(binding.recyclerView){
            addItemDecoration(DividerItemDecoration(context,
            RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }

        getData()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, {
            //Log.d("HistoriFragment", "Jumlah data: ${it.size}")
            binding.emptyView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.updateData(it as List<PersegiEntity>)
        })
    }

    private fun getData(){
        myAdapter.setOnItemClickListener(object : HistoriAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(R.string.konfirmasi_hapus)
                    .setPositiveButton(getString(R.string.hapus)){ _, _ ->
                        viewModel.deleteData(myAdapter.getContent(position))
                    }
                    .setNegativeButton(getString(R.string.batal)){ dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
                //Toast.makeText(requireActivity(), "Anda klik item no, $position",Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.histori_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_hapus -> {
                hapusData()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusData(){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.hapus)){ _, _ ->
                viewModel.deleteAll()
            }
            .setNegativeButton(getString(R.string.batal)){ dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}