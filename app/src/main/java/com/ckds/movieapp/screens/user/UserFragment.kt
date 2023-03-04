package com.ckds.movieapp.screens.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ckds.movieapp.R
import com.ckds.movieapp.databinding.FragmentUserBinding
import com.ckds.movieapp.screens.adapters.AdapterViewModel
import com.ckds.movieapp.screens.adapters.MoviesAdapter
import com.ckds.movieapp.screens.adapters.SeriesAdapter
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel by viewModels<UserViewModel>()
    private val adapterViewModel by viewModels<AdapterViewModel>()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAuth()
        initDetails()
        mBinding.vfUser.apply {
            displayedChild = if (viewModel.checkSessionState()) {
                1
            } else {
                0
            }
        }

    }

    private fun initAuth() {
        mBinding.auth.apply {
            btnLogIn.setOnClickListener {
                val login = etLogin.text.toString()
                val password = etPassword.text.toString()
                MainScope().launch {
                    val resource = MainScope().async {
                        viewModel.authenticate(login = login, password = password)
                    }
                    resource.await().let { success ->
                        tvError.text = when (success.error) {
                            is UnknownHostException -> getString(R.string.unknown_host_exception)
                            is ConnectException -> getString(R.string.connect_exception)
                            is SocketTimeoutException -> getString(R.string.socket_timeout_exception)
                            is HttpException -> getString(R.string.http_exception)
                            else -> success.error.toString()
                        }
                        success.data?.let { if (it) {
                                mBinding.vfUser.displayedChild = 1
                                initMoviesAdapter()
                                initSeriesAdapter()
                                initAccountDetails()
                            }
                        }
                    }
                }
            }

            tvSignIn.setOnClickListener {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.themoviedb.org/signup")
                )
                context?.startActivity(browserIntent)
            }
        }
    }

    private fun initDetails() {
        mBinding.details.apply {
            btnLogOut.setOnClickListener {
                viewModel.logOut()
                mBinding.vfUser.displayedChild = 0
                mBinding.auth.tvError.text = ""
            }

            if (viewModel.checkSessionState()) {
                initMoviesAdapter()
                initSeriesAdapter()
                initAccountDetails()
            }
        }
    }

    private fun initMoviesAdapter() {

        mBinding.details.apply {
            adapterFavoriteMovies = MoviesAdapter(adapterViewModel)
            rvFavoriteMovies.apply {
                adapter = adapterFavoriteMovies
            }

            viewModel.getFavoriteMovies()
            viewModel.favoriteMovies.observe(viewLifecycleOwner) { resource ->
                adapterFavoriteMovies.differ.submitList(resource.data)
                progressBarMovies.isVisible = resource is Resource.Loading && resource.data.isNullOrEmpty()
                tvErrorMovies.apply {
                    isVisible = resource is Resource.Error && resource.data.isNullOrEmpty()
                    text = when (resource.error) {
                        is UnknownHostException -> getString(R.string.unknown_host_exception)
                        is ConnectException -> getString(R.string.connect_exception)
                        is SocketTimeoutException -> getString(R.string.socket_timeout_exception)
                        else -> resource.error.toString()
                    }
                }
            }

            adapterFavoriteMovies.setOnItemClickListener {
                val bundle = bundleOf("movie" to it)
                view?.findNavController()
                    ?.navigate(R.id.action_userFragment_to_movieFragment, bundle)
            }
        }

    }

    private fun initSeriesAdapter() {

        mBinding.details.apply {
            adapterFavoriteSeries = SeriesAdapter(adapterViewModel)
            rvFavoriteSeries.apply {
                adapter = adapterFavoriteSeries
            }

            viewModel.getFavoriteSeries()
            viewModel.favoriteSeries.observe(viewLifecycleOwner) { resource ->
                adapterFavoriteSeries.differ.submitList(resource.data)
                progressBarSeries.isVisible = resource is Resource.Loading && resource.data.isNullOrEmpty()
                tvErrorSeries.apply {
                    isVisible = resource is Resource.Error && resource.data.isNullOrEmpty()
                    text = when (resource.error) {
                        is UnknownHostException -> getString(R.string.unknown_host_exception)
                        is ConnectException -> getString(R.string.connect_exception)
                        is SocketTimeoutException -> getString(R.string.socket_timeout_exception)
                        else -> resource.error.toString()
                    }
                }
            }

            adapterFavoriteSeries.setOnItemClickListener {
                val bundle = bundleOf("series" to it)
                view?.findNavController()
                    ?.navigate(R.id.action_userFragment_to_seriesFragment, bundle)
            }
        }

    }

    private fun initAccountDetails() {
        viewModel.getDetails()
        viewModel.detailsLiveData.observe(viewLifecycleOwner) { resource ->
            resource.data?.let { details ->
                mBinding.details.apply {
                    tv_login.text = details.username
                    view?.let {
                        Glide.with(it).load(details.avatar.gravatar.hash)
                            .error(R.drawable.no_image_sample).into(imgAvatar)
                    }
                }
            }
        }
    }

}