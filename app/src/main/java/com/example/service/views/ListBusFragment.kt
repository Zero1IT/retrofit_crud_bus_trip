package com.example.service.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.service.adapters.BusesAdapter
import com.example.service.adapters.SwipeToDeleteCallback
import com.example.service.databinding.BusListFragmentBinding
import com.example.service.viewmodels.ListBusViewModel
import com.example.service.viewmodels.ViewModelsFactory

class ListBusFragment : Fragment() {

    private val viewModel: ListBusViewModel by viewModels {
        ViewModelsFactory(this.requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bind = BusListFragmentBinding.inflate(layoutInflater)
        val adapter = BusesAdapter { viewModel.onBusItemClick(it) }
        bind.lifecycleOwner = viewLifecycleOwner
        bind.busList.layoutManager = LinearLayoutManager(context)
        bind.busList.adapter = adapter
        bind.viewModel = viewModel
        val swiper = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val trip = adapter.removeAt(viewHolder.adapterPosition)
                viewModel.deleteItem(trip)
            }
        }
        ItemTouchHelper(swiper).attachToRecyclerView(bind.busList)
        observe(adapter)
        return bind.root
    }

    private fun observe(adapter: BusesAdapter) {
        viewModel.busTrips.observe(viewLifecycleOwner, Observer {
            it?.also {
                adapter.submitList(it)
            }
        })
        viewModel.navigateToBusTrip.observe(viewLifecycleOwner, Observer {
            it?.let {
                val toCreateBusFragment = ListBusFragmentDirections.toCreateBusFragment()
                toCreateBusFragment.busTrip = it
                findNavController().navigate(toCreateBusFragment)
                viewModel.onBusTripNavigated()
            }
        })
    }
}