package com.ckds.movieapp.screens.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ckds.movieapp.R
import com.ckds.movieapp.databinding.FragmentSeriesBinding
import com.ckds.movieapp.screens.adapters.ActorsAdapter
import com.ckds.movieapp.screens.adapters.GenresAdapter
import com.ckds.movieapp.utils.Constants
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class SeriesFragment : Fragment() {

    private var _binding: FragmentSeriesBinding? = null
    private val mBinding get() = _binding!!
    private val bundleArgs: SeriesFragmentArgs by navArgs()
    private val viewModel by viewModels<SeriesViewModel>()
    lateinit var adapterGenres: GenresAdapter
    lateinit var adapterCast: ActorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSeriesBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundleArgs.series.let { series ->
            mBinding.apply {
                tvTitle.text = series.name
                tvDescription.text = series.overview
                Glide.with(view).load("${Constants.POSTER_BASE_URL}${series.poster_path}")
                    .error(R.drawable.no_image_sample).into(imgPoster)
                imgPoster.clipToOutline = true
            }
            series.id.let { tvId ->
                initDetails(tvId = tvId!!)
                initCastAdapter(tvId = tvId)
            }
        }
    }

    private fun initCastAdapter(tvId: Int) {
        viewModel.getCredits(tvId = tvId)
        adapterCast = ActorsAdapter()
        mBinding.apply {
            rvActors.apply {
                adapter = adapterCast
            }
            viewModel.creditsLiveData.observe(viewLifecycleOwner) { resource ->
                adapterCast.differ.submitList(resource.data?.cast)
                tvError.apply {
                    isVisible = resource is Resource.Error
                    text = when (resource.error) {
                        is UnknownHostException -> getString(R.string.unknown_host_exception)
                        is ConnectException -> getString(R.string.connect_exception)
                        is SocketTimeoutException -> getString(R.string.socket_timeout_exception)
                        else -> resource.error.toString()
                    }
                }
            }
        }
    }

    private fun initDetails(tvId: Int) {
        viewModel.getDetails(tvId = tvId)
        adapterGenres = GenresAdapter()
        mBinding.rvGenre.apply {
            adapter = adapterGenres
        }
        viewModel.detailsLiveData.observe(viewLifecycleOwner) { resource ->
            mBinding.apply {
                resource.data?.let { details ->
                    tvYears.text = "${details.first_air_date.subSequence(0,4)} -" +
                            " ${details.last_air_date.subSequence(0,4)}"
                    tvSeasons.text = "${details.seasons.size} seasons"
                    adapterGenres.differ.submitList(details.genres)
                }
            }
        }
    }
}


