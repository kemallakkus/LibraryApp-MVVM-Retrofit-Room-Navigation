package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.BookItemBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.FragmentSearchBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.LibraryItemBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments.LibraryFragmentDirections
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.Book
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.model.BookLocal
import javax.inject.Inject

class LibraryAdapter @Inject constructor(val showIcon:(Boolean) -> Unit /*, val showText:(Boolean)->Unit*/
    ):RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    var isEnable = false
    val itemSelectedList = mutableListOf<Int>()
    private lateinit var bookItem : Book


    inner class LibraryViewHolder(val binding: LibraryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<BookLocal>() {
        override fun areItemsTheSame(oldItem: BookLocal, newItem: BookLocal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookLocal, newItem: BookLocal): Boolean {
            return oldItem == newItem

        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = LibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val booksLocal = differ.currentList[position]
        //bookItem = differ.currentList[position]
        //val item = bookItem[position]
        holder.binding.apply {
            this.tvBookNameLibray.text = booksLocal.title
            //imgSelect.visibility = View.GONE
            holder.itemView.apply {

                Glide.with(holder.itemView)
                    .load(booksLocal.imagejpeg)
                    .into(imgBookLibrary)
            }



            /*holder.itemView.setOnClickListener {
                onItemClick?.let {
                    it.invoke(booksLocal)
                }
            }*/

            holder.itemView.setOnClickListener { mView ->
                val direction = LibraryFragmentDirections
                    .actionLibraryFragmentToDetailFragment(bookItem)
                mView.findNavController().navigate(direction)
            }

            holder.binding.bookInfo.setOnLongClickListener {


                selectItem(holder, booksLocal, position)


                if (booksLocal.selected == true){
                    imgSelect.visibility = View.VISIBLE
                    bookInfo.setBackgroundColor(Color.parseColor("#5ADF5F"))
                }else{
                    imgSelect.visibility = View.GONE
                    bookInfo.setBackgroundColor(Color.parseColor("#DFE3E8"))
                }

                true
            }

            holder.binding.bookInfo.setOnClickListener {
                if (itemSelectedList.contains(position)) {
                    itemSelectedList.remove(position)
                    differ.currentList[position].selected = false
                    //holder.binding.imgSelect.visibility = View.GONE
                    //booksLocal.selected = false
                    if (itemSelectedList.isEmpty()){
                        showIcon(false)
                        //showText(false)
                        isEnable = false
                        booksLocal.selected = false //differ.currentList.forEach { it.selected=false }
                        notifyDataSetChanged()
                    }
                }else if(isEnable==true){
                    selectItem(holder, booksLocal, position)
                }
                if (booksLocal.selected==true){
                    imgSelect.visibility = View.VISIBLE
                    bookInfo.setBackgroundColor(Color.parseColor("#5ADF5F"))

                }else{
                    imgSelect.visibility = View.GONE
                    bookInfo.setBackgroundColor(Color.parseColor("#DFE3E8"))

                }
            }
        }
    }

    private fun selectItem(holder: LibraryAdapter.LibraryViewHolder, booksLocal: BookLocal, position: Int) {

        isEnable = true
        itemSelectedList.add(position)
        booksLocal.selected = true
        showIcon(true)
        //showText(true)

    }

    //var onItemClick: ((BookLocal) -> Unit)? = null
    //var onLongItemClick : ((BookLocal) -> Unit)? = null


    override fun getItemCount(): Int {
        return differ.currentList.size

    }

}