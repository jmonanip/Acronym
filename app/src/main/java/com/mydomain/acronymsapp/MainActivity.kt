package com.mydomain.acronymsapp

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mydomain.acronymsapp.lookup.LookupAcronymFragment
import com.mydomain.acronymsapp.lookup.LookupAcronymViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private var progressBar: ProgressBar? = null
    private lateinit var lookupAcronymFragment: LookupAcronymFragment
    private lateinit var lookupAcronymViewModel: LookupAcronymViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView = findViewById(R.id.search_view)
        progressBar = findViewById(R.id.progressBar)
        lookupAcronymViewModel = ViewModelProvider(this).get(LookupAcronymViewModel::class.java)
        initializeLookupScreen(savedInstanceState)
        initializeSearchView()
    }

    private fun initializeLookupScreen(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            return
        }

        lookupAcronymFragment = LookupAcronymFragment().getInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, lookupAcronymFragment)
            .commit()
        title = TITLE
    }

    private fun initializeSearchView() {
        searchView.queryHint = getString(R.string.search_acronym_hint)
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    lookupAcronymViewModel.clearAcronymData()
                    lookupAcronymViewModel.lookupAcronym(query)
                    searchView.clearFocus()
                    lookupAcronymFragment.showProgress(true)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    lookupAcronymViewModel.clearAcronymData()
                }
                return false
            }
        })
        searchView.setOnCloseListener {
            lookupAcronymViewModel.clearAcronymData()
            false
        }

        lookupAcronymViewModel.getErrorStatus().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Log.e(TAG, "Error: $it")
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }
    companion object {
        const val TAG = "MainActivity"
        const val TITLE = "Lookup Acronym"
    }
}