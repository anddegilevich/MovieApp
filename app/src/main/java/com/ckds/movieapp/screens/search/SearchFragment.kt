package com.ckds.movieapp.screens.search

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.ckds.movieapp.R
import com.ckds.movieapp.databinding.FragmentSearchBinding
import com.ckds.movieapp.screens.adapters.AdapterViewModel
import com.ckds.movieapp.screens.adapters.SearchMoviesAdapter
import com.ckds.movieapp.screens.adapters.SearchSeriesAdapter
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()
    private val adapterViewModel by viewModels<AdapterViewModel>()
    lateinit var adapterSearchMovies: SearchMoviesAdapter
    lateinit var adapterSearchSeries: SearchSeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMoviesAdapter(view = view)
        initSeriesAdapter(view = view)

        var job: Job? = null
        mBinding.editSearch.addTextChangedListener{ text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    mBinding.layoutResults.isVisible = if (it.toString().isNotEmpty()) {
                        viewModel.searchMovies(query = it.toString())
                        viewModel.searchSeries(query = it.toString())
                        true
                    } else false
                }
            }
        }
    }

    private fun initMoviesAdapter(view: View) {

        mBinding.apply {
            adapterSearchMovies = SearchMoviesAdapter(adapterViewModel)
            rvSearchMovies.apply {
                adapter = adapterSearchMovies
            }

            viewModel.searchedMovies.observe(viewLifecycleOwner) { resource ->
                adapterSearchMovies.differ.submitList(resource.data)

                adapterSearchMovies.setOnItemClickListener {
                    val bundle = bundleOf("movie" to it)
                    view.findNavController()
                        .navigate(R.id.action_searchFragment_to_movieFragment, bundle)
                }
            }

        }
    }

    private fun initSeriesAdapter(view: View) {

        mBinding.apply {
            adapterSearchSeries = SearchSeriesAdapter(adapterViewModel)
            rvSearchSeries.apply {
                adapter = adapterSearchSeries
            }

            viewModel.searchedSeries.observe(viewLifecycleOwner) { resource ->
                adapterSearchSeries.differ.submitList(resource.data)
            }

            adapterSearchSeries.setOnItemClickListener {
                val bundle = bundleOf("series" to it)
                view.findNavController().navigate(R.id.action_searchFragment_to_seriesFragment, bundle)
            }
        }

    }
}