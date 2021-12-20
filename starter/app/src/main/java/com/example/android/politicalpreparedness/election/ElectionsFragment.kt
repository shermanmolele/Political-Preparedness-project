package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener


class ElectionsFragment: Fragment() {
    private val viewModel: ElectionsViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ElectionsViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory)
            .get(ElectionsViewModel::class.java)

    }

    private lateinit var upcomingElectoinAdapter: ElectionAdapter
    private lateinit var savedElectionAdapter: ElectionAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val binding: FragmentElectionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_election,
            container,
            false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        upcomingElectoinAdapter = ElectionAdapter(ElectionListener {
            findNavController().navigate(ElectionsFragmentDirections.actionElectionsToVoterInfo(it)
            )
        })

        binding.upcomingElectionsRecyclerView.adapter = upcomingElectoinAdapter

        savedElectionAdapter = ElectionAdapter(ElectionListener {
            findNavController().navigate(
                ElectionsFragmentDirections.actionElectionsToVoterInfo(it)
            )
        })

        binding.savedElectionsRecyclerView.adapter = savedElectionAdapter


        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer { elections ->
            elections.apply {
                upcomingElectoinAdapter.elections = elections
            }
        })

        viewModel.savedElections.observe(viewLifecycleOwner, Observer { elections ->
            elections.apply {
                savedElectionAdapter.elections = elections
            }
        })

        return binding.root


    }



}