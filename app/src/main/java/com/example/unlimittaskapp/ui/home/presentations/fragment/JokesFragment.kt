package com.example.unlimittaskapp.ui.home.presentations.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unlimittaskapp.CommonUtils
import com.example.unlimittaskapp.constant.AppConstants
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.databinding.FragmentJokesBinding
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.ui.home.presentations.adapter.JokesAdapter
import com.example.unlimittaskapp.ui.home.presentations.viewmodels.JokesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.Sort
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class JokesFragment : Fragment() {

    var binding: FragmentJokesBinding? = null
    private val viewModel: JokesViewModel by viewModels()

    var adapter :JokesAdapter?= null

    @Inject
    lateinit var realm: Realm

    lateinit var  repeatFun :Job
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJokesBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = realm.where(Jokes::class.java)
        setRecyclerViewFunction(query)
        repeatFun = repeat()
        initiateObserver()

    }
    private fun repeat(): Job {
        return lifecycleScope.launch {
            while(isActive) {
                    //do your network request here
                delay(1000 * 60)
                callJokesApis()
                Log.d("isCall","real")

            }
        }
    }
    /**
     *this function is used for observer the api calling data and loading data
     * */
    private fun initiateObserver() {
        viewModel.jokesListLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is AppResponse.Success -> {
                    validateListData()
                }
                is AppResponse.Error -> {
                    validateListData()
                }
                else -> {
                    validateListData()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        repeatFun.cancel()
    }
    private fun callJokesApis() {
        if (CommonUtils.isNetworkAvailable(requireContext())) {
            viewModel.getJokes()
        }
    }

    /**
     *this function is used for view pager functionality
     * */
    private fun setRecyclerViewFunction(query: RealmQuery<Jokes>) {
        adapter = activity?.let {
            JokesAdapter(
               query
                   .sort(AppConstants.DATE_SORT,Sort.DESCENDING)
                   .limit(20)
                   .findAll(),
                it
            )
        }!!
        binding?.rvJokesList?.let {
            it.setItemAnimator(DefaultItemAnimator())
            it.setLayoutManager(LinearLayoutManager(requireActivity()))
            it.setAdapter(adapter!!)
            it.enableNoRecordValidations()
            it.showNoRecordLayout()
        }
        validateListData()

    }
    /**
     *
     *this function is called  for check data is empty or not
     *  if data is empty show no record view
     * */
    private fun validateListData() {
        adapter?.let {
            it.data?.let { data ->
                if (data.size > 0)
                    binding?.rvJokesList!!.hideNoRecordLayout()
                else binding?.rvJokesList!!.showNoRecordLayout()

            }
        }
    }
}