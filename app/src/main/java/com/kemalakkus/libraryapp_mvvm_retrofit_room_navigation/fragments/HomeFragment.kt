package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.R
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.adapters.BookAdapter
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.FragmentHomeBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var bookAdapter: BookAdapter
    private lateinit var viewModel: BookViewModel
    //private lateinit var appBarLayout: AppBarLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]

        //viewModel.getAllBooks(page = "1")
        viewModel.getAllBooks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener{appBarLayout, verticalOffset ->
            //var isShow = false
            //var scrollRange = -1
            val toolbarHeight = binding.toolBar.measuredHeight
            val appBarHeight = binding.appBar.measuredHeight
            if (Math.abs(verticalOffset) >= (appBarHeight - toolbarHeight)){
                binding.secondSearch.visibility = View.VISIBLE
                binding.toolbarText.visibility = View.VISIBLE
            }
            else{
                binding.secondSearch.visibility = View.GONE
                binding.toolbarText.visibility = View.GONE
            }

        })



        setupRv()
        onBookClick()
        onSearchClick()
        onSearchClickTwo()
        observeLiveDataHome()

    }

    private fun onBookClick() {
        bookAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putParcelable("book",it)
            }
        findNavController().navigate(R.id.action_home_to_detailFragment,bundle)
        }
    }

    private fun onSearchClick(){
        binding.edSearch.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_searchFragment)
        }
    }

    private fun onSearchClickTwo(){
        binding.secondSearch.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_searchFragment)
        }
    }


    private fun setupRv(){
        bookAdapter = BookAdapter()

        binding.rvHome.apply {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter =bookAdapter
            setHasFixedSize(true)

        }



    }

    private fun observeLiveDataHome(){


        viewModel.allBooks.observe(viewLifecycleOwner, Observer { book ->
            book?.let {
                bookAdapter.differ.submitList(it.results)
            }
            binding.homeProgressBar.visibility = View.GONE


        })
        viewModel.bookLoading.observe(viewLifecycleOwner, Observer {
            binding.homeProgressBar.visibility = View.VISIBLE
        })

        /*viewModel.bookLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it){
                    binding.rvHome.visibility = View.GONE
                    binding.homeProgressBar.visibility = View.VISIBLE
                }else{
                    binding.rvHome.visibility = View.VISIBLE
                }
            }
        })*/
    }





}





