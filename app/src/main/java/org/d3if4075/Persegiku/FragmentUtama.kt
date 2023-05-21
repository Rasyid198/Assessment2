package org.d3if4075.Persegiku

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if4075.Persegiku.databinding.FragmentUtamaBinding
import org.d3if4075.Persegiku.db.PersegiDb
import org.d3if6030.PenghitungPersegi.ViewModelUtama

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentUtama.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentUtama : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: ViewModelUtama by lazy{
        val db = PersegiDb.getInstance(requireContext())
        val factory = ViewModelFactoryUtama(db.dao)
        ViewModelProvider(this, factory).get(ViewModelUtama::class.java)
    }
    private lateinit var binding: FragmentUtamaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        binding = FragmentUtamaBinding.inflate(layoutInflater,container,false)
        binding.button.setOnClickListener{
            hitung()
        }

        binding.buttonHistori.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentUtama_to_historiFragment)
        }

        binding.buttonListBangunDatar.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentUtama_to_listBangunDatarFragment)
        }

        binding.buttonShare.setOnClickListener{
            bagikan()
        }

        binding.textKeliling.visibility = View.GONE
        binding.keliling.visibility = View.GONE
        binding.textLuas.visibility = View.GONE
        binding.luas.visibility = View.GONE
        binding.buttonShare.visibility = View.GONE

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHasilPersegi().observe(viewLifecycleOwner, {
            if(it==null) return@observe
            binding.textKeliling.visibility = View.VISIBLE
            binding.keliling.visibility = View.VISIBLE
            binding.keliling.text = it.keliling.toString()
            binding.textLuas.visibility = View.VISIBLE
            binding.luas.visibility = View.VISIBLE
            binding.luas.text = it.luas.toString()
            binding.buttonShare.visibility = View.VISIBLE
        })

//        viewModel.data.observe(viewLifecycleOwner, {
//            if(it==null) return@observe
//            Log.d("FragmentUtama", "Data tersimpan. ID = ${it.id}")
//        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_fragmentUtama_to_historiFragment)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun hitung (){

        val sisi = binding.sisi.text.toString()

        if (TextUtils.isEmpty(sisi)){
            Toast.makeText(context, "Panjang sisi Harus Diisi", Toast.LENGTH_LONG).show()
            return
        }
        viewModel.hitung(sisi)
//        val hasilKeliling = sisi.toDouble()*4
//        val hasilLuas = sisi.toDouble()*sisi.toDouble()
//        binding.textKeliling.visibility = View.VISIBLE
//        binding.keliling.visibility = View.VISIBLE
//        binding.keliling.text = hasilKeliling.toString()
//        binding.textLuas.visibility = View.VISIBLE
//        binding.luas.visibility = View.VISIBLE
//        binding.luas.text = hasilLuas.toString()

    }

    private fun bagikan(){
        val pesan = "Sisi: "+binding.sisi.text.toString()+", Keliling: "+binding.keliling.text.toString()+", Luas: "+binding.luas.text.toString()+"."

        val bagikanIntent = Intent(Intent.ACTION_SEND)
        bagikanIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, pesan)
        if (bagikanIntent.resolveActivity(requireActivity().packageManager) != null){
            startActivity(bagikanIntent)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentUtama.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentUtama().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}