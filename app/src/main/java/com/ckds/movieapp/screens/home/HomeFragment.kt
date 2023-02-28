package com.ckds.movieapp.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ckds.movieapp.databinding.FragmentHomeBinding
import com.ckds.movieapp.screens.adapters.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var adapterPopularMovies: MoviesAdapter

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
    }

    private fun initMoviesAdapter(view: View) {

        adapterPopularMovies = MoviesAdapter()
        mBinding.rvMovies.apply {
            adapter = adapterPopularMovies
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            adapterPopularMovies.differ.submitList(movies)
        }

    }
}