package com.Korinver.androidsivar

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Korinver.androidsivar.adapters.RecyclerAdapter
import com.Korinver.androidsivar.models.Articles
import com.Korinver.androidsivar.models.Results
import com.Korinver.androidsivar.retrofit.ApiInterface
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
  private lateinit var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
   private  var mArticles : MutableList<Articles> = ArrayList()
    private lateinit var apiInterface: Call<Results>
    private lateinit var nestedScroll: NestedScrollView
    private var isLoading: Boolean = false
    private var page: Int = 1

    val limit = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_demo, container, false)
        layoutManager = LinearLayoutManager(activity)

        // establezco el adaptador personalizado a RecyclerView
        adapter = RecyclerAdapter(requireContext() , mArticles, R.layout.cardview)
        return rootView
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        //establezco la toolbar personalizada a la vista
        val toolbar: Toolbar = view!!.findViewById<Toolbar>(R.id.mainToolbarAct)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        titlemain.text = "Android Sivar"
        toolbar.setNavigationIcon(R.drawable.ic_headline);
        //recycler
        val recyclerView: RecyclerView = view!!.findViewById(R.id.recycler_view)
        nestedScroll = view!!.findViewById(R.id.nestedScroll)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        //llamo a la funcion para actualizar los botones de paginación
        upButtom(1)
        // Se define el adapter
        adapter = RecyclerAdapter(requireContext(), mArticles, R.layout.cardview)
        recyclerView.adapter = adapter
        getData("es", 1)

        //Se detecta si el toggle button se le da click y si cambia su estado de ON/OFF
        languageSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            //Si esta en modo ON se traeran datos en Español
            if (isChecked){
                mArticles.clear()
                getData("es", page)
                upButtom(page)
            }else{

                //y si esta en modo OFF se traeran datos en ingles
                mArticles.clear()
                getData("en",page)
                upButtom(page)
            }
        }

        //boton para poder cambiar de pagina al momento de traer los datos
        btnSiguiente.setOnClickListener {
            if (languageSwitch.isChecked){
                page += 1
                mArticles.clear()
                getData("es", page)
                upButtom(page)
            }else{
                page +=1
                mArticles.clear()
                getData("en", page)
                upButtom(page)
            }
        }

        //boton para retroceder de pagina al momento de traer los datos
        btnAnterior.setOnClickListener {
            if (languageSwitch.isChecked){
                page -= 1
                mArticles.clear()
                getData("es", page)
                upButtom(page)
            }else{
                page -=1
                mArticles.clear()
                getData("en", page)
                upButtom(page)
            }
        }

    }

    //funcion que habilita o deshabilita los botones de SIguiente y Anterior para la paginación
    private fun upButtom(page: Int){
        if( page<=1 ){
            btnAnterior.isFocusable = false
            btnAnterior.isClickable = false
            btnAnterior.isEnabled = false
        }else{
            btnAnterior.isFocusable = true
            btnAnterior.isClickable = true
            btnAnterior.isEnabled = true
            btnAnterior.visibility = View.VISIBLE
        }
        if (page >= 5 ){

                btnSiguiente.isFocusable = false
                btnSiguiente.isClickable = false
                btnSiguiente.isEnabled = false
        }else{
            btnSiguiente.isFocusable = true
            btnSiguiente.isClickable = true
            btnSiguiente.isEnabled = true

        }
    }

    /* función que hace la llamada a la api
    **/
    private fun getData(language: String, page : Int) {

        //Llave para poder acceder a la API
        val API_KEY = "4f6ad0c7ee86446f8857e1edc6c479fc"

        //Se mandan los parametros de la connsulta
        apiInterface = ApiInterface.create().getResults("android","popularity", language, page,API_KEY)
        //metodo de  la llamda
        apiInterface.enqueue(object : Callback<Results> {

            //Metodo sobre que hacer si la llamada falla
            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.e("results", t.toString())
                Snackbar.make(view!!, "ha ocurrido un error, intentalo de nuevo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            //Metodo sobre que hacer si la llamada es exitosa
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                //verificamos que la respuesta no sea nula
                if (response.body() != null){
                    if(response.body()!!.status == "error"){

                        Snackbar.make(view!!, "ha ocurrido un error, intentalo de nuevo", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }else{
                        val results = response.body().toString()
                        val articles = response.body()!!.articles
                        mArticles.addAll(articles)
                        adapter!!.notifyDataSetChanged()
                        Log.e("results", articles.toString())
                    }

                }else{
                    Log.e("results", "sin respuesta")
                    Snackbar.make(view!!, "ha ocurrido un error, intentalo de nuevo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }
        })
    }
}

