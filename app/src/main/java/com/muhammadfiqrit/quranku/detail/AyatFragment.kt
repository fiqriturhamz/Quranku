package com.muhammadfiqrit.quranku.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammadfiqrit.quranku.core.data.Resource
import com.muhammadfiqrit.quranku.core.ui.AyatAdapter
import com.muhammadfiqrit.quranku.databinding.FragmentAyatBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AyatFragment : Fragment() {
    private var _binding: FragmentAyatBinding? = null
    private val binding get() = _binding!!
    private val detailSuratViewModel: DetailSuratViewModel by viewModel()

    companion object {
        var suratNomor: Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAyatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateRecyclerView(suratNomor)
    }

    private fun populateRecyclerView(suratNomor: Int) {
        detailSuratViewModel.setId(suratNomor)
        detailSuratViewModel.suratDetail.observe(viewLifecycleOwner) {
            val result = it
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBarAyat.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBarAyat.visibility = View.INVISIBLE

                        result.data?.let {
                            binding.rvAyat.adapter = AyatAdapter(it.ayat)
                            binding.rvAyat.layoutManager = LinearLayoutManager(requireActivity())
                            binding.rvAyat.setHasFixedSize(true)

                        }

                    }

                    is Resource.Error -> {
                        binding.progressBarAyat.visibility = View.INVISIBLE
                        Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}