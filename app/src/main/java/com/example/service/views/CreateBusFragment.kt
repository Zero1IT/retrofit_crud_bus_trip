package com.example.service.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.service.R
import com.example.service.databinding.CreateBusFragmentBinding
import com.example.service.viewmodels.CreateBusViewModel
import com.google.android.material.snackbar.Snackbar

class CreateBusFragment : Fragment() {

    private val mViewModel: CreateBusViewModel by viewModels {
        val busTrip = CreateBusFragmentArgs.fromBundle(requireArguments()).busTrip
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return when (modelClass) {
                    CreateBusViewModel::class.java -> CreateBusViewModel(busTrip, requireActivity().application) as T
                    else -> throw IllegalArgumentException("Invalid viewModels $modelClass")
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bind = CreateBusFragmentBinding.inflate(inflater)
        bind.viewModel = mViewModel
        bind.lifecycleOwner = viewLifecycleOwner
        setActions()
        return bind.root
    }

    private fun setActions() {
        mViewModel.createSuccessful.observe(viewLifecycleOwner, Observer {
            if (it) toListFragment()
            else Snackbar.make(requireView(), getString(R.string.incorrect_data), Snackbar.LENGTH_SHORT).show()
        })
        mViewModel.updateSuccessful.observe(viewLifecycleOwner, Observer {
            if (it) toListFragment()
            else Snackbar.make(requireView(), getString(R.string.incorrect_data), Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun toListFragment() {
        findNavController().navigate(CreateBusFragmentDirections.toListBusFragment())
    }
}