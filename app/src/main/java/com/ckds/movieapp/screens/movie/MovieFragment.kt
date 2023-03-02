package com.ckds.movieapp.screens.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ckds.movieapp.R
import com.ckds.movieapp.databinding.FragmentMovieBinding
import com.ckds.movieapp.screens.adapters.ActorsAdapter
import com.ckds.movieapp.screens.adapters.GenresAdapter
import com.ckds.movieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_poster.view.*

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val mBinding get() = _binding!!
    private val bundleArgs: MovieFragmentArgs by navArgs()
    private val viewModel by viewModels<MovieViewModel>()
    lateinit var adapterGenres: GenresAdapter
    lateinit var adapterCast: ActorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bundleArgs.movie.let { movie ->
            mBinding.apply {
                tvTitle.text = movie.title
                movie.release_date.let { date ->
                    tvYear.text = date?.subSequence(0,4)
                }
                tvDescription.text = movie.overview
                Glide.with(view).load("${Constants.POSTER_BASE_URL}${movie.poster_path}")
                    .error(R.drawable.no_image_sample).into(imgPoster)
                imgPoster.clipToOutline = true
            }
            movie.id.let { movieId ->
                initDetails(movieId = movieId!!)
                initCastAdapter(movieId = movieId)
            }

        }
    }

    private fun initCastAdapter(movieId: Int) {
        viewModel.getCredits(movieId = movieId)
        adapterCast = ActorsAdapter()
        mBinding.rvActors.apply {
            adapter = adapterCast
        }
        viewModel.creditsLiveData.observe(viewLifecycleOwner) { resource ->
            adapterCast.differ.submitList(resource.data?.cast)
        }
    }

    private fun initDetails(movieId: Int) {
        viewModel.getDetails(movieId = movieId)
        adapterGenres = GenresAdapter()
        mBinding.rvGenre.apply {
            adapter = adapterGenres
        }
        viewModel.detailsLiveData.observe(viewLifecycleOwner) { resource ->
            resource.data?.apply {
                runtime.toString().let{ runtime ->
                    mBinding.tvDuration.text = "$runtime min"
                }
                adapterGenres.differ.submitList(genres)
            }
        }
    }
}