package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.R
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.adapters.BookAdapter
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.FragmentCategoryBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel.BookViewModel
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter : BookAdapter
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = resources.getStringArray(R.array.Categories)
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
            binding?.spinner?.adapter = adapter

            binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    viewModel.getBooksByCategory(categories[position])
                    //Toast.makeText(context,"Seçilen tür:"+categories[position],Toast.LENGTH_SHORT).show()
                    //binding.tvCategory.text = categories[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        onCategoryBackClick()
        setupRv()
        onBookClick()
    }

    private fun onCategoryBackClick(){
        binding.imgCategoryBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_categories_to_home)
        }
    }

    private fun onBookClick() {
        categoryAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putParcelable("book",it)
            }
            findNavController().navigate(R.id.action_categories_to_detailFragment,bundle)
        }
    }

    private fun setupRv(){
        categoryAdapter = BookAdapter()
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
            setHasFixedSize(true)
        }

        viewModel.allBooks.observe(viewLifecycleOwner, Observer { book ->
            book?.let {
                categoryAdapter?.differ?.submitList(it.results)
            }


        })



    }
}