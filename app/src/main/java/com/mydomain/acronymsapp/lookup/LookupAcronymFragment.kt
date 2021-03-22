package com.mydomain.acronymsapp.lookup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mydomain.acronymsapp.R
import com.mydomain.acronymsapp.model.AcronymsList
import com.mydomain.acronymsapp.ui.LookupAcronymAdapter

class LookupAcronymFragment : Fragment() {

    private lateinit var lookupAcronymViewModel: LookupAcronymViewModel
    private lateinit var recyclerView: RecyclerView
    private var progressBar: ProgressBar? = null

    fun getInstance() : LookupAcronymFragment {
        return LookupAcronymFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.lookup_fragment, container, false)
        initializeView(view)
        return view
    }

    private fun initializeView(view: View) {
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        // init adapter
        val lookupAdapter = LookupAcronymAdapter()
        recyclerView.adapter = lookupAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lookupAcronymViewModel = ViewModelProvider(requireActivity()).get(LookupAcronymViewModel::class.java)
        setupObserver()
    }

    private fun setupObserver() {
        lookupAcronymViewModel.getAcronymList().observe(viewLifecycleOwner,
            Observer<AcronymsList> { acronymsList ->
                val adapter = recyclerView.adapter as LookupAcronymAdapter
                if (acronymsList == null || acronymsList.getLfList().isNullOrEmpty()) {
                    adapter.clearList()
                } else {
                    adapter.addItems(acronymsList.getLfList())
                }
                showProgress(false)
            })
    }

    fun showProgress(show: Boolean) {
        if (show) progressBar?.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (!show) progressBar?.visibility = View.GONE
            }
            , 500)
    }
}