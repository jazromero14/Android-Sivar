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
        val toolbar: Toolbar = view!!.findViewById<Toolbar>(R.id.secondToolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        toolbar.title = "Android Sivar"
        toolbar.setNavigationIcon(R.drawable.ic_headline);

        val fab: View = view!!.rootView.findViewById(R.id.fltArticle)
        fab.setOnClickListener { view ->
            val intent = Intent(context, DataRecordDetail::class.java)
            context!!.startActivity(intent)

        }

        val recyclerView = datarecord_list
        val adapter = DataRecordAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        /* We need the data to populate the list with. For this we will retrieve the ViewModel
           that we have defined, which in our case is a `DataRecordViewModel::class.java`, from
           the ViewModelProvider service.
        */
        datarecordViewModel = ViewModelProvider(this).get(DataRecordViewModel::class.java)

        /* Simply associate an observer with each of the items contained in the viewmodel.
           Notice that this can be done because `allItems` in the `DataRecordViewModel` is a `LiveData`
           object.
           The method `setItems` of the `DataRecordAdapter` class, takes care of populating each
           line of the RecyclerView, according to the layout specified in `datarecord_viewholder`.
           This is also where we set any actions (click, longclick...) per item in the list.
        */
        datarecordViewModel.allItems.observe(viewLifecycleOwner, Observer { items ->
            items?.let { adapter.setItems(it) }
        })

    }




}
