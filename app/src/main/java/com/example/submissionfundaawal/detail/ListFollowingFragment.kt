package com.example.submissionfundaawal.detail

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundaawal.adapter.FollowAdapter
import com.example.submissionfundaawal.databinding.FragmentListFollowingBinding
import com.example.submissionfundaawal.detail.favorite.FavFactory
import com.example.submissionfundaawal.model.Follows
import com.example.submissionfundaawal.model.MainViewModel

class ListFollowingFragment : Fragment() {

    private  var _binding: FragmentListFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataValue = arguments?.getString("dataValue")

        mainViewModel = ViewModelProvider( this,FavFactory.getInstance(activity?.applicationContext as Application))[MainViewModel::class.java]
        mainViewModel.findUserFollowing(dataValue!!).observe(viewLifecycleOwner) { userDatas ->
            getUserFollowing(userDatas)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
    }

    private fun getUserFollowing(userDatas: List<Follows>) {
        binding.apply {
            rvUserFollowing.layoutManager = LinearLayoutManager(context)

            rvUserFollowing.adapter = FollowAdapter(userDatas as ArrayList<Follows>)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}