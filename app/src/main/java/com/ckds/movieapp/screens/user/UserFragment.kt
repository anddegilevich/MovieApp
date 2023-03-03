package com.ckds.movieapp.screens.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ckds.movieapp.databinding.FragmentUserBinding
import com.ckds.movieapp.screens.adapters.MoviesAdapter
import com.ckds.movieapp.screens.adapters.SeriesAdapter
import com.ckds.movieapp.screens.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var adapterFavoriteMovies: MoviesAdapter
    lateinit var adapterFavoriteSeries: SeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }
}