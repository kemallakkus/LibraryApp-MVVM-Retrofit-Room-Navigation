package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.R
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.adapters.BookAdapter
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.adapters.LibraryAdapter
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.FragmentLibraryBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookLocal
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel.BookViewModel
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel.BookViewModelDao
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private lateinit var libraryAdapter: LibraryAdapter
    private lateinit var viewModel: BookViewModelDao
    //private var show: Boolean = false
    private lateinit var bookSelect : BookLocal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[BookViewModelDao::class.java]


        observerBookLocalData()
        setupRv()
        //onBookClick()
        onBackClick()

        /*libraryAdapter.onItemClick={

        }*/

        showDelete(view)


    }

    private fun showDelete(view: View){
        binding.delete.setOnClickListener {
            libraryAdapter.itemSelectedList.forEach {
                bookSelect = libraryAdapter.differ.currentList[it]
                viewModel.delete(bookSelect)
                libraryAdapter.itemSelectedList
                showIconDelete(false)
                //showTextItemNumber(false)
            }
            libraryAdapter.itemSelectedList.clear()
            libraryAdapter.isEnable = false

            Snackbar.make(view,"Delete Successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
    private fun showIconDelete(show:Boolean){
        binding.delete.isVisible = show
    }

    /*private fun showTextItemNumber(show: Boolean){
        binding.tvItemNumber.isVisible = show
    }*/

    private fun setupRv() {
        showIconDelete(false)
        //showTextItemNumber(false)
        libraryAdapter = LibraryAdapter(){showIcon -> showIconDelete(showIcon)}
        binding.rvLibrary.apply {

            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            //layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = libraryAdapter
            setHasFixedSize(true)

        }

    }

    private fun onBackClick(){
        binding.imgLibraryBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_home)
        }
    }

    private fun observerBookLocalData(){
        viewModel.getAllBook.observe(viewLifecycleOwner, Observer { local->
            local?.let {
                libraryAdapter.differ.submitList(it)
            }

        })
    }

   /* private fun onBookClick() {
        libraryAdapter.onItemClick = {
            findNavController().navigate(R.id.action_libraryFragment_to_detailFragment)

        }
    }*/

}