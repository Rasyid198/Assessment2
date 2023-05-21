package org.d3if4075.Persegiku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if4075.Persegiku.databinding.FragmentListBangunDatarBinding
import org.d3if4075.Persegiku.network.ApiStatus

class ListBangunDatarFragment: Fragment() {
    private lateinit var binding: FragmentListBangunDatarBinding
    private lateinit var myAdapter: ListBangunDatarAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBangunDatarBinding.inflate(layoutInflater, container, false)
        myAdapter = ListBangunDatarAdapter()
        with(binding.recyclerView){
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }

        return binding.root
    }

    private val viewModel: ListBangunDatarViewModel by lazy {
        ViewModelProvider(this).get(ListBangunDatarViewModel::class.java)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, {
            myAdapter.updateData(it)
        })

        viewModel.getStatus().observe(viewLifecycleOwner, {
            updateProgress(it)
        })
    }

    private fun updateProgress(status: ApiStatus){
        when(status){
            ApiStatus.LOADING ->{
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS ->{
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED ->{
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}