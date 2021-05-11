package com.Korinver.androidsivar.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Korinver.androidsivar.Constants.Companion.DATA_RECORD_ID
import com.Korinver.androidsivar.DataRecordDetail
import com.Korinver.androidsivar.R
import com.Korinver.androidsivar.room.DataRecord

class DataRecordAdapter internal constructor(context: Context) :

    RecyclerView.Adapter<DataRecordAdapter.DataRecordViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemsList = emptyList<DataRecord>().toMutableList()

    private val onClickListener: View.OnClickListener

    init {
        /* Se recibe un solo elemento de tipo DataRecord durannte el proceso
                datarecordViewModel.allItems.observe(this, Observer { items ->
                    items?.let { adapter.setItems(it) }
                })
           en `DataRecordListActivity`
        * */
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DataRecord

            Log.d("TAG","Setting onClickListener for item ${item.id}")

            /* iniciamos un nuevo Intent con datos adicionales personalizados.
             */

            val intent = Intent(v.context, DataRecordDetail::class.java).apply {
                putExtra(DATA_RECORD_ID, item.id)
            }
            v.context.startActivity(intent)
        }
    }

    /* Esta es una clase interna que asocia los elementos en ViewHolder
        diseño con variables que se utilizarán dentro de OnBindViewHolder.
    */
    inner class DataRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.datarecord_viewholder_title)
        val itemAuthor: TextView = itemView.findViewById(R.id.datarecord_viewholder_author)
    }

    /* Básicamente, infla el diseño de ViewHolder y devuelve un objeto ViewHolder
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataRecordViewHolder {
        val itemView = inflater.inflate(R.layout.datarecord_viewholder, parent, false)
        return DataRecordViewHolder(itemView)
    }

    /* Aquí es donde ViewHolder se llena con datos del Item.
     */
    override fun onBindViewHolder(holder: DataRecordViewHolder, position: Int) {
        val current = itemsList[position]

        // Se hará referencia en el View.OnClickListener anterior
        holder.itemView.tag = current

        with(holder) {
            // Se establecen valores de IU
            itemTitle.text = current.title
            itemAuthor.text = current.author

            itemView.setOnClickListener(onClickListener)
        }
    }

    internal fun setItems(items: List<DataRecord>) {
        this.itemsList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemsList.size
}