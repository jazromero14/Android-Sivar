package com.korinver.androidsivar

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.korinver.androidsivar.room.DataRecord
import com.korinver.androidsivar.viewmodels.DataRecordViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_data_record_detail.*

class DataRecordDetail : AppCompatActivity() {

    private lateinit var datarecordViewModel: DataRecordViewModel
    private var recordId: Long = 0L
    private var isEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cargando  el diseño de `activity_datarecord_detail.xml`
        setContentView(R.layout.activity_data_record_detail)
        val toolbar: Toolbar = findViewById(R.id.nwToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            //Que hacer al hacer clic en la parte posterior
            onBackPressed()
        })

        /* Recuperamos el ViewModel que hemos definido (`DataRecordViewModel :: class.java`)
            desde el servicio ViewModelProvider.
        */
        datarecordViewModel = ViewModelProvider(this).get(DataRecordViewModel::class.java)

        /*Verificamos el intent de los datos adicionales, en función de la clave predefinida (que es la misma que usamos
            para insertar dichos datos. También se observa como esperamos que sea de tipo Long, usamos
            `getLongExtra`. Hay captadores para diferentes tipos de datos.
        */
        if (intent.hasExtra(Constants.DATA_RECORD_ID)) {
            recordId = intent.getLongExtra(Constants.DATA_RECORD_ID, 0L)

            /* En este caso, dado que el método DAO `get (id)` devuelve un objeto `LiveData`,
                 utilizamos el patrón `observer` para poblar la vista.
             */
            datarecordViewModel.get(recordId).observe(this, Observer {

                // HAcemos referencias a los elementos de la interfaz de usuario en el diseño
                val viewId = findViewById<TextView>(R.id.datarecord_id)
                val viewAuthor = findViewById<EditText>(R.id.authorNew)
                val viewTitle = findViewById<EditText>(R.id.titleData)
                val viewDescription = findViewById<EditText>(R.id.descriptioNew)
                val viewContent = findViewById<EditText>(R.id.contentNew)

                // Protegemos del caso cuando sea nulo, que ocurre cuando eliminamos el elemento.
                if (it != null) {
                    //poblamos con datos
                    viewId.text = it.id.toString()
                    viewAuthor.setText(it.author)
                    viewTitle.setText(it.title)
                    viewDescription.setText(it.description)
                    viewContent.setText(it.content)
                }
            })
            isEdit = true
        }

        /* Preparamos OnClickListeners para cada botón:
             Guardar, actualizar y eliminar.
            Realizan prácticamente las mismas operaciones y comprobaciones, pero utilizan el método específico
            insertar, actualizar, eliminar el método de ViewModel.
         */
        val btnSave = btnSave
        btnSave.setOnClickListener { view ->
            val id = 0L
            val author = authorNew.text.toString()
            val title = titleData.text.toString()
            val descript = descriptioNew.text.toString()
            val cont = contentNew.text.toString()

            if (author.isBlank() || author.isEmpty() || title.isBlank() || title.isEmpty() || descript.isEmpty() || descript.isBlank() || cont.isBlank() || cont.isEmpty()) {
                Snackbar.make(view, "Campos obligatorios, por favor completarlos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                val item = DataRecord(id = id, author = author, title = title, description = descript, content = cont )
                datarecordViewModel.insert(item)
                finish()
            }
        }

        val btnUpdate = btnUpdate
        btnUpdate.setOnClickListener { view ->
            val id = datarecord_id.text.toString().toLong()
            val author = authorNew.text.toString()
            val title = titleData.text.toString()
            val descript = descriptioNew.text.toString()
            val cont = contentNew.text.toString()

            if (author.isBlank() || author.isEmpty() || title.isBlank() || title.isEmpty() || descript.isEmpty() || descript.isBlank() || cont.isBlank() || cont.isEmpty()) {
                Snackbar.make(view, "Campos obligatorios, por favor completarlos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                val item = DataRecord(id = id, author = author, title = title, description = descript, content = cont)
                datarecordViewModel.update(item)
                finish()
            }
        }

        val btnDelete = btnDelete
        btnDelete.setOnClickListener {
            val id = datarecord_id.text.toString().toLong()
            val author = authorNew.text.toString()
            val title = titleData.text.toString()
            val descript = descriptioNew.text.toString()
            val cont = contentNew.text.toString()


            val item = DataRecord(id = id, author = author, title = title, description = descript, content = cont)
            datarecordViewModel.delete(item)
            finish()
        }

        /* Oculte botones según el caso: esta es una gestión de UI muy simplista
            ejemplo
         */
        if (isEdit) {
            /* btnSave llama al método dao.save, que en realidad crea un nuevo registro
                Al ocultarlo, permitimos correctamente solo Actualizar y Eliminar
             */
            btnSave.visibility = View.GONE
        } else {
            /*No hay motivo para actualizar o eliminar un nuevo registro que aún no se haya guardado*/
            btnUpdate.visibility = View.GONE
            btnDelete.visibility = View.GONE
        }
    }
}