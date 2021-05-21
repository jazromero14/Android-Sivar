package com.korinver.androidsivar

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.korinver.androidsivar.adapters.RecyclerAdapter
import com.korinver.androidsivar.models.Articles
import com.korinver.androidsivar.models.Results
import com.korinver.androidsivar.retrofit.ApiInterface
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.datarecord_viewholder.view.*
import kotlinx.android.synthetic.main.fragment_demo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.isInitialized as isInitialized1


class DemoFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var linearLayoutManager = LinearLayoutManager(context)
    private lateinit var rootView: View
    private lateinit var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    private var mArticles: MutableList<Articles> = ArrayList()
    private lateinit var apiInterface: Call<Results>
    private lateinit var nestedScroll: NestedScrollView
    private var isLoading: Boolean = false
    private var idioma: Int = 0
    private var page: Int = 1

    val limit = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_demo, container, false)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        layoutManager = LinearLayoutManager(requireContext())
        // establezco el adaptador personalizado a RecyclerView
        adapter = RecyclerAdapter(requireContext(), mArticles, R.layout.cardview)

        //recycler
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recycler_view)
        nestedScroll = requireView().findViewById(R.id.nestedScroll)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        //llamo a la funcion para actualizar los botones de paginación
        upButtom(1)
        // Se define el adapter
        adapter = RecyclerAdapter(requireContext(), mArticles, R.layout.cardview)
        recyclerView.adapter = adapter
        getData("es", 1)


        //boton para poder cambiar de pagina al momento de traer los datos
        btnSiguiente.setOnClickListener {
            if (idioma== 0) {
                page += 1
                mArticles.clear()
                getData("es", page)
                upButtom(page)
            } else {
                page += 1
                mArticles.clear()
                getData("en", page)
                upButtom(page)
            }
        }

        //boton para retroceder de pagina al momento de traer los datos
        btnAnterior.setOnClickListener {
            if (idioma==0) {
                page -= 1
                mArticles.clear()
                getData("es", page)
                upButtom(page)
            } else {
                page -= 1
                mArticles.clear()
                getData("en", page)
                upButtom(page)
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val checkable = menu.findItem(R.id.checkable_menu)
        checkable.isChecked
    }
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.checkable_menu -> {
                var isChecked = !item.isChecked;
                item.title = "idioma: Ingles"
                item.isChecked = isChecked;
                showIdiom(item)
                true
            }
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.idiom_menu, menu)
    }
    private fun showIdiom(item: MenuItem) {

        if (item.isChecked){
            item.title = "idioma: Ingles"
            idioma = 1
            mArticles.clear()
            getData("en", page)
            upButtom(page)
        }else{
            item.title = "idioma: Español"
            idioma = 0
            mArticles.clear()
            getData("es", page)
            upButtom(page)
        }

    }
    //funcion que habilita o deshabilita los botones de SIguiente y Anterior para la paginación
    private fun upButtom(page: Int) {
        if (page <= 1) {
            btnAnterior.isFocusable = false
            btnAnterior.isClickable = false
            btnAnterior.isEnabled = false
        } else {
            btnAnterior.isFocusable = true
            btnAnterior.isClickable = true
            btnAnterior.isEnabled = true
            btnAnterior.visibility = View.VISIBLE
        }
        if (page >= 5) {

            btnSiguiente.isFocusable = false
            btnSiguiente.isClickable = false
            btnSiguiente.isEnabled = false
        } else {
            btnSiguiente.isFocusable = true
            btnSiguiente.isClickable = true
            btnSiguiente.isEnabled = true

        }
    }

    /* función que hace la llamada a la api
    **/
    private fun getData(language: String, page: Int) {

        //Llave para poder acceder a la API
        val API_KEY = "4f6ad0c7ee86446f8857e1edc6c479fc"

        //Se mandan los parametros de la connsulta
        apiInterface =
            ApiInterface.create().getResults("android", "popularity", language, page, API_KEY)
        //metodo de  la llamda
        apiInterface.enqueue(object : Callback<Results> {

            //Metodo sobre que hacer si la llamada falla
            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.e("results", t.toString())
                Snackbar.make(
                    view!!,
                    "ha ocurrido un error, intentalo de nuevo",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Action", null).show()
            }

            //Metodo sobre que hacer si la llamada es exitosa
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                //verificamos que la respuesta no sea nula
                if (response.body() != null) {
                    if (response.body()!!.status == "error") {

                        Snackbar.make(
                            view!!,
                            "ha ocurrido un error, intentalo de nuevo",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Action", null).show()
                    } else {
                        val results = response.body().toString()
                        val articles = response.body()!!.articles
                        mArticles.addAll(articles)
                        adapter!!.notifyDataSetChanged()
                        Log.e("results", articles.toString())
                    }

                } else {
                    Log.e("results", "sin respuesta")
                    Snackbar.make(
                        view!!,
                        "ha ocurrido un error, intentalo de nuevo",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Action", null).show()
                }
            }
        })
    }
}

