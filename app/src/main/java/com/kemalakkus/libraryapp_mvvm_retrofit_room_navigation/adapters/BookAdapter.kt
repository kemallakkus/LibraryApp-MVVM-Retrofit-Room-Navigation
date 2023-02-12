package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.adapters

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.BookItemBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.*
import javax.inject.Inject


class BookAdapter @Inject constructor(): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Book>() {

        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    /*var onItemClick : ((Book) -> Unit)? = null
    //private var bookList = ArrayList<Book>()

    var books: List<Book>

        get() = differ.currentList

        set(value) {
            differ.submitList(value)
        }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        //val currentBook = books[position]
        val books = differ.currentList[position]
        holder.binding.apply {
            this.tvBookName.text = books.title
            this.tvAuthor.text = books.authors[0].name
            this.tvLanguage.text = books.languages[0]
            this.tvSubject.text = books.subjects[0]

            holder.itemView.apply {

                Glide.with(holder.itemView)
                    .load(books.formats.imagejpeg)
                    .into(imgBookRv)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick?.let {
                it.invoke(books)
            }
        }
        }
    var onItemClick : ((Book) -> Unit)? = null


    override fun getItemCount() : Int{
        return differ.currentList.size
    }



}


