package com.ckds.movieapp.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ckds.movieapp.R
import com.ckds.movieapp.databinding.FragmentHomeBinding
import com.ckds.movieapp.screens.adapters.MoviesAdapter
import com.ckds.movieapp.screens.adapters.SeriesAdapter
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var adapterPopularMovies: MoviesAdapter
    lateinit var adapterPopularSeries: SeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoviesAdapter(view)
        initSeriesAdapter(view)
    }

    private fun initMoviesAdapter(view: View) {

        mBinding.apply {
            adapterPopularMovies = MoviesAdapter()
            rvMovies.apply {
                adapter = adapterPopularMovies
            }

            viewModel.popularMovies.observe(viewLifecycleOwner) { resource ->
                adapterPopularMovies.differ.submitList(resource.data)
                progressBarMovies.isVisible = resource is Resource.Loading && resource.data.isNullOrEmpty()
                tvErrorMovies.apply {
                    isVisible = resource is Resource.Error && resource.data.isNullOrEmpty()
                    text = resource.error.toString()
                }
            }

            adapterPopularMovies.setOnItemClickListener {
                val bundle = bundleOf("movie" to it)
                view.findNavController().navigate(R.id.action_homeFragment_to_movieFragment, bundle)
            }
        }

    }

    private fun initSeriesAdapter(view: View) {

        adapterPopularSeries = SeriesAdapter()
        mBinding.rvSeries.apply {
            adapter = adapterPopularSeries
        }

        viewModel.popularSeries.observe(viewLifecycleOwner) { series ->
            adapterPopularSeries.differ.submitList(series)
        }

        adapterPopularSeries.setOnItemClickListener {
            val bundle = bundleOf("series" to it)
            view.findNavController().navigate(R.id.action_homeFragment_to_seriesFragment, bundle)
        }

    }
}