package com.ccino.demo.sdk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ccino.demo.R
import com.ware.widget.recycler.MoreAdapter


class KeyboardFragment : Fragment() {

    @SuppressLint("ClickableViewAccessibility")
    private fun coordinatorAndAppBar() {

        initAdapter()

    }

    private fun initAdapter() {
        val adapter = MoreAdapter(requireContext(), false)
        val list = mutableListOf<String>()
        for (i in 1..20) {
            list.add(i.toString())
        }
        adapter.setData(list)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coordinatorAndAppBar()
    }
}