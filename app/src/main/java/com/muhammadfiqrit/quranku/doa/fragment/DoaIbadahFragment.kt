package com.muhammadfiqrit.quranku.doa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muhammadfiqrit.quranku.R
import com.muhammadfiqrit.quranku.databinding.FragmentDoaIbadahBinding
import com.muhammadfiqrit.quranku.doa.DoaAdapter
import com.muhammadfiqrit.quranku.doa.DoaViewModel
import com.muhammadfiqrit.quranku.utils.Utilities
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class DoaIbadahFragment : Fragment() {
    private var _binding: FragmentDoaIbadahBinding? = null
    private val binding get() = _binding!!
    private val doaViewModel: DoaViewModel by viewModel()
    private val doaAdapter: DoaAdapter by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDoaIbadahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Utilities.populateData(
            "ibadah",
            doaViewModel,
            viewLifecycleOwner,
            binding.rvDoaIbadah,
            doaAdapter,
            requireContext()
        )
    }

}