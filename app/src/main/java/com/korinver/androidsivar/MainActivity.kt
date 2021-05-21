package com.korinver.androidsivar

import com.korinver.androidsivar.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.korinver.androidsivar.adapters.NavigationRVAdapter
//import com.korinver.androidsivar.databinding.ActivityMainBinding
import com.korinver.androidsivar.models.NavigationItemModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
      //  setTheme(R.style.splashTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationDrawer()
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController: NavController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration =
            AppBarConfiguration.Builder(R.id.demoFragment, R.id.listFragment)
                .setDrawerLayout(drawerLayout)
                .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.navView)
            .setupWithNavController(navController)


    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
    }

    override fun onSupportNavigateUp(): Boolean {

        return findNavController(R.id.myNavHostFragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Comprobación del recuento de fragmentos en la cola
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Ir al fragmento anterior
                supportFragmentManager.popBackStack()
            } else {
                // Salir de la app
                super.onBackPressed()
            }
        }
    }
}