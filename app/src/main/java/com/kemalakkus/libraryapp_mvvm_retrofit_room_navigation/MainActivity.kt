package com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.databinding.ActivityMainBinding
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments.CategoryFragment
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments.HomeFragment
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments.LibraryFragment
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.fragments.SearchFragment
import com.kemalakkus.libraryapp_mvvm_retrofit_room_navigation.util.MySuggestionProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LibraryAppMVVMRetrofitRoomNavigation)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        drawerLayout = binding.drawerLayout
        val imgMenu = binding.imgMenu
        val navView = findViewById<NavigationView>(R.id.navDrwr)

        navView.itemIconTintList = null

        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navController = Navigation.findNavController(this,R.id.fragmentContainerView)

        NavigationUI.setupWithNavController(navView, navController)

        //val textTitle = binding.title
        /*navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            //textTitle.text = destination.label
            if (destination.id == R.id.home) {
                imgMenu.visibility = View.VISIBLE
            } else {
                imgMenu.visibility = View.GONE
            }

        }*/

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.home ->binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
                else -> binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
            }
            when(destination.id){
                R.id.home->binding.imgMenu.visibility=View.VISIBLE
                else ->binding.imgMenu.visibility=View.GONE
            }
        }

        navView.menu.findItem(R.id.exit).setOnMenuItemClickListener {
            //it.isChecked = true
            when(it.itemId){
                R.id.exit ->{
                    val alertDialog: androidx.appcompat.app.AlertDialog = androidx.appcompat.app.AlertDialog.Builder(this).create()
                    alertDialog.setTitle("Exit Library")
                    alertDialog.setMessage("Do you want exit?")

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes"){
                        dialog,which->finish()
                        dialog.dismiss()
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No"){
                        dialog,which->
                        dialog.dismiss()
                    }
                    alertDialog.show()
                }

                /*R.id.home -> replaceFragment(HomeFragment(),it.title.toString())
                R.id.libraryFragment -> replaceFragment(LibraryFragment(),it.title.toString())
                R.id.categories -> replaceFragment(CategoryFragment(),it.title.toString())
                R.id.searchFragment -> replaceFragment(SearchFragment(),it.title.toString())
*/
                //else -> NavigationUI.setupWithNavController(navView,navController)
            }
            true
        }


        /*setSupportActionBar(binding.toolbar)

        binding.apply {

            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_home ->{
                        Toast.makeText(this@MainActivity, "First Item", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_category ->{
                        Toast.makeText(this@MainActivity, "Second Item", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_library ->{
                        Toast.makeText(this@MainActivity, "Third Item", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            true
        }

        return super.onOptionsItemSelected(item)
    }

}*/

    }

   /* private fun replaceFragment(fragment: Fragment,title:String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }*/


}
