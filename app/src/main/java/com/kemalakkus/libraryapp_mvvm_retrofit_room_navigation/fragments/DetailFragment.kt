package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.R
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.FragmentDetailBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.Book
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookLocal
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.viewmodel.BookViewModelDao
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val args by navArgs <DetailFragmentArgs>()
    //val argsLocal by navArgs <DetailFragmentArgs>()
    private lateinit var viewModel: BookViewModelDao
    //private lateinit var bookId:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root


        //viewModel.upsert(bookId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val books = args.book
        //val locals = argsLocal.local

        //initLocalDetail(locals)
        initDetail(books)

        viewModel = ViewModelProvider(this)[BookViewModelDao::class.java]



        backOnClick()
        //onLibraryClick(view, books)

        val booksLocal = BookLocal(books.title!!,books.formats.imagejpeg!!,books.id!!)
        binding.btnAddToFav.setOnClickListener {
            viewModel.upsert(booksLocal)
            Snackbar.make(view,"Book Saved",Snackbar.LENGTH_LONG).show()
        }

    }

   /* private fun initLocalDetail(locals: BookLocal) {
        binding.apply {
            tvBookName.text = locals.title
            Glide.with(this@DetailFragment)
                .load(locals.imagejpeg)
                .into(imgDetail)
        }

    }*/

    /*private fun onLibraryClick(view: View, books:Book) {
        binding.btnAddToFav.setOnClickListener {
            bookx?.let {
                viewModel.upsert(bookx!!)
                //Toast.makeText(context,"Book saved", Toast.LENGTH_LONG).show()

            }
            Snackbar.make(view,"Book Saved",Snackbar.LENGTH_LONG).show()
        }
    }*/

    //private var bookx : BookLocal? = null


    private fun initDetail(books: Book){

        binding.apply {
            tvBookName.text = books.title
            tvAuthor.text = books.authors.get(0).name
            tvLanguage.text = books.languages[0]
            tvSubject.text = books.subjects[0]
            tvLanguageDetail.text = books.bookshelves[0]
            tvCountDetail.text = books.authors[0].death_year.toString()


            Glide.with(this@DetailFragment)
                .load(books.formats.imagejpeg)
                .into(imgDetail)
        }
    }

    private fun backOnClick(){
        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_home)
        }
    }


}
