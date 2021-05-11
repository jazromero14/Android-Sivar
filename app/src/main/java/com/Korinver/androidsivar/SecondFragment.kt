package com.Korinver.androidsivar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.Korinver.androidsivar.adapters.DataRecordAdapter
import com.Korinver.androidsivar.viewmodels.DataRecordViewModel
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    private lateinit var datarecordViewModel: DataRecordViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_second, container, false)

        return rootView
    }

    override fun onViewCreated( itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        //Se establece la toolbar personalizada
        val toolbar: Toolbar = view!!.findViewById<Toolbar>(R.id.secondToolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        toolbar.title = "Android Sivar"
        toolbar.setNavigationIcon(R.drawable.ic_headline);

        //boton flotante para nuevo registro del crud
        val fab: View = view!!.rootView.findViewById(R.id.fltArticle)
        fab.setOnClickListener { view ->
            val intent = Intent(context, DataRecordDetail::class.java)
            context!!.startActivity(intent)

        }

        val recyclerView = datarecord_list
        val adapter = DataRecordAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        /*Se recupera el ViewModel que hemos definido, que en nuestro caso es un `DataRecordViewModel :: class.java`, de
            el servicio ViewModelProvider. ahi tendremos nuestros datos
        */
        datarecordViewModel = ViewModelProvider(this).get(DataRecordViewModel::class.java)

        /* Simplemente asocio un observador con cada uno de los elementos contenidos en el modelo de vista.
            Tenga en cuenta que esto se puede hacer porque `allItems` en el` DataRecordViewModel` es un `LiveData`
            objeto.
            El método `setItems` de la clase` DataRecordAdapter`, se encarga de poblar cada
            línea de RecyclerView, de acuerdo con el diseño especificado en `datarecord_viewholder`.
        */
        datarecordViewModel.allItems.observe(viewLifecycleOwner, Observer { items ->
            items?.let { adapter.setItems(it) }
        })

    }




}
