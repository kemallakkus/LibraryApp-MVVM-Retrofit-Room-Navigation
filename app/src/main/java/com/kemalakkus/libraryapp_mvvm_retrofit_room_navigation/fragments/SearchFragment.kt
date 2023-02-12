package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.R
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.adapters.BookAdapter
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.FragmentSearchBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.Book
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.util.MySuggestionProvider
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: BookAdapter
    private lateinit var viewModel: SearchViewModel
    lateinit var books: List<Book>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        setupSearchRv()

        binding.imgSearchBook.setOnClickListener { searchBooks() }

        observeSearchBooksLiveData()

        var searchJob : Job? = null

        binding.searchBox.addTextChangedListener{ searchQuery->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch{
                delay(500)
                viewModel.getSearchBook(searchQuery.toString())
            }

        }

        onBookClick()
        searchBackOnClick()

    }

    private fun observeSearchBooksLiveData() {
        viewModel.searchBook.observe(viewLifecycleOwner, Observer {
            it?.let {
                searchAdapter.differ.submitList(it.results)
            }
        })
    }

    private fun setupSearchRv() {
        searchAdapter = BookAdapter()
        binding.rvSearchBooks.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = searchAdapter
        }
    }

    private fun searchBackOnClick(){
        binding.imgSearchBack.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_home)
        }
    }

    private fun searchBooks() {
        val searchQuery = binding.searchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.getSearchBook(searchQuery)
        }
        binding.searchProgressBar.visibility = View.GONE

        viewModel.searchLoading.observe(viewLifecycleOwner, Observer {
            binding.searchProgressBar.visibility = View.VISIBLE
        })

    }


    private fun onBookClick() {
        searchAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putParcelable("book",it)
            }
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment,bundle)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.searchBox.showKeyboard()

        binding.searchBox.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                hideKeyboardFrom(requireContext(), requireView())
            }
        }
    }
    fun EditText.showKeyboard() {
        if (requestFocus()) {
            (context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(this, SHOW_IMPLICIT)
            setSelection(text.length)
        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}