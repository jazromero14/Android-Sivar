package com.Korinver.androidsivar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.Korinver.androidsivar.adapters.NavigationRVAdapter
import com.Korinver.androidsivar.listeners.ClickListener
import com.Korinver.androidsivar.listeners.RecyclerTouchListerner
import com.Korinver.androidsivar.models.NavigationItemModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter

    private var items = arrayListOf(
        NavigationItemModel(R.drawable.ic_home, "Inicio"),
        NavigationItemModel(R.drawable.ic_list, "Mis Publicaciones")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.splashTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)

        // Se configura el diseño de Recyclerview
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)

        // Se agrega el oyente táctil de cada elemento del Drawer
        navigation_rv.addOnItemTouchListener(RecyclerTouchListerner(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        // # Fragmento principal = DemoFragment
                        val homeFragment = DemoFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.activity_main_content_id, homeFragment).commit()
                    }
                    1 -> {
                        // # Fragmento donde estan los articulos personales = SecondFragment
                        val ownArticlesFragment =
                            SecondFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.activity_main_content_id, ownArticlesFragment).commit()
                    }
                }
                if (position != 6 && position != 4) {
                    updateAdapter(position)
                }
                Handler().postDelayed({
                    drawerLayout.closeDrawer(GravityCompat.START)
                }, 200)
            }
        }))

        //Se actualiza el adaptador con los datos del elemento
        updateAdapter(0)

        // Establezco 'DemoFragment' como el fragmento predeterminado cuando se inicia la aplicación
        val homeFragment = DemoFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_main_content_id, homeFragment).commit()

        // Establezco imagen de encabezado
        navigation_header_img.setImageResource(R.drawable.ic_launcher_foreground)

        // Establecer el color de fondo del menu
        navigation_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))

    }


    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationRVAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapter
        adapter.notifyDataSetChanged()
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