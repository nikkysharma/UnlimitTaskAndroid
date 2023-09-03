package com.example.unlimittaskapp.ui.home.presentations.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unlimittaskapp.base.BaseFragment
import com.example.unlimittaskapp.base.MainThreadBus
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.databinding.FragmentJokesBinding
import com.example.unlimittaskapp.ui.home.presentations.adapter.JokesAdapter
import com.example.unlimittaskapp.ui.home.presentations.viewmodels.JokesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmQuery
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject


@AndroidEntryPoint
class JokesFragment : BaseFragment() {

    var binding: FragmentJokesBinding? = null
    private val viewModel: JokesViewModel by viewModels()
    @Inject
    lateinit var bus: MainThreadBus

    var adapter :JokesAdapter?= null
    @Inject
    lateinit var realm: Realm
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
        bus.register(this)
       // initiateObserver()
        callJokesApis()
        val query = realm.where(Jokes::class.java)
        setRecyclerViewFunction(query)
        callTimer()

    }
    private val timer = Timer()
    private fun callTimer(){
        val hourlyTask: TimerTask = object : TimerTask() {
            override fun run() {
                // your code here...
                callJokesApis()
            }
        }
        // schedule the task to run starting now and then every hour...

        // schedule the task to run starting now and then every hour...
        timer.schedule(hourlyTask, 0L, (100 * 60 * 60).toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        binding = null
    }
    private fun callJokesApis() {
        viewModel.getJokesList()
    }
//    private fun initiateObserver(){
//        viewModel.jokesListLiveData.observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is AppResponse.Loading -> showProgress()
//                is AppResponse.Success -> {
//                    dismissProgress()
//                    bus.post(UpdateUi())
//                }
//                is AppResponse.Error -> {
//                    dismissProgress()
//                    bus.post(UpdateUi())
//                }
//                else -> {
//                    dismissProgress()
//                }
//            }
//        }
//    }
//    @Subscribe
//    fun setViewTypeEvent(event: UpdateUi){
//        applyList()
//    }
//
//    private fun applyList() {
//        when(val data = viewModel.getJokesDataFromLocalDB()){
//            is AppResponse.Success -> {
//                val query = realm.where(Jokes::class.java)
//                setRecyclerViewFunction(query)
//            }
//            is AppResponse.Error -> {
//               // no dta
//                binding?.rvJokesList!!.hideNoRecordLayout()
//            }
//
//            else -> {
//
//            }
//        }
//
//    }

    /**
     *this function is used for view pager functionality
     * */
    private fun setRecyclerViewFunction(query: RealmQuery<Jokes>) {
        adapter = activity?.let {
            JokesAdapter(
               query
                   .limit(10)
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