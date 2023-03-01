package com.ckds.movieapp.screens.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ckds.movieapp.R
import com.ckds.movieapp.databinding.FragmentSeriesBinding
import com.ckds.movieapp.screens.adapters.ActorsAdapter
import com.ckds.movieapp.screens.adapters.GenresAdapter
import com.ckds.movieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

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
                initDetails(tvId = tvId)
                initCastAdapter(tvId = tvId)
            }

        }
    }

    private fun initCastAdapter(tvId: Int) {
        viewModel.getCast(tvId = tvId)
        adapterCast = ActorsAdapter()
        mBinding.rvActors.apply {
            adapter = adapterCast
        }
        viewModel.castLiveData.observe(viewLifecycleOwner) { cast ->
            adapterCast.differ.submitList(cast)
        }
    }

    private fun initDetails(tvId: Int) {
        viewModel.getDetails(tvId = tvId)
        adapterGenres = GenresAdapter()
        mBinding.rvGenre.apply {
            adapter = adapterGenres
        }
        viewModel.detailsLiveData.observe(viewLifecycleOwner) { details ->
            mBinding.apply {
                tvYears.text = "${details.first_air_date.subSequence(0,4)} -" +
                        " ${details.last_air_date.subSequence(0,4)}"
                tvSeasons.text = "${details.seasons.size} seasons"
            }
            adapterGenres.differ.submitList(details.genres)
        }
    }
}


