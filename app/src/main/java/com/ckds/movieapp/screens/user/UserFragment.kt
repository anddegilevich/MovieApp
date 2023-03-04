package com.ckds.movieapp.screens.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ckds.movieapp.R
import com.ckds.movieapp.databinding.FragmentUserBinding
import com.ckds.movieapp.screens.adapters.MoviesAdapter
import com.ckds.movieapp.screens.adapters.SeriesAdapter
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel by viewModels<UserViewModel>()
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

        if (viewModel.checkSessionState()) {
            initDetails()
        }
        else {
            initAuth()
        }
    }

    private fun initAuth() {
        mBinding.apply {
            vfUser.displayedChild = 0
            auth.apply {
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
                                else -> success.error.toString()
                            }
                            success.data?.let { if (it) initDetails() }
                        }
                    }
                }
            }
        }
    }

    private fun initDetails() {
        mBinding.apply {
            vfUser.displayedChild = 1
            details.apply {

            }
        }
    }
}