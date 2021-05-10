package com.Korinver.androidsivar

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.Korinver.androidsivar.room.DataRecord
import com.Korinver.androidsivar.viewmodels.DataRecordViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_data_record_detail.*

class DataRecordDetail : AppCompatActivity() {

    private lateinit var datarecordViewModel: DataRecordViewModel
    private var recordId: Long = 0L
    private var isEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the layout from `activity_datarecord_detail.xml`
        setContentView(R.layout.activity_data_record_detail)
        val toolbar: Toolbar = findViewById(R.id.nwToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            //What to do on back clicked
            onBackPressed()
        })

        /* Retrieve the ViewModel that we have defined (`DataRecordViewModel::class.java`)
           from the ViewModelProvider service.
        */
        datarecordViewModel = ViewModelProvider(this).get(DataRecordViewModel::class.java)

        /* Check Intent for Extra Data, based on the predefined key (which is the same that we use
           to insert such data. Also notice that since we expect it to be of type Long, we use
           `getLongExtra`. There's getters for different data types.
        */
        if (intent.hasExtra(Constants.DATA_RECORD_ID)) {
            recordId = intent.getLongExtra(Constants.DATA_RECORD_ID, 0L)

            /*  In this case, since the DAO `get(id)` method returns a `LiveData` object,
                we use the `observer` pattern to populate the view.
             */
            datarecordViewModel.get(recordId).observe(this, Observer {

                // Ger references to the UI items in the layout
                val viewId = findViewById<TextView>(R.id.datarecord_id)
                val viewAuthor = findViewById<EditText>(R.id.authorNew)
                val viewTitle = findViewById<EditText>(R.id.titleData)
                val viewDescription = findViewById<EditText>(R.id.descriptioNew)
                val viewContent = findViewById<EditText>(R.id.contentNew)

                // Protect from null, which occurs when we delete the item
                if (it != null) {
                    // populate with data
                    viewId.text = it.id.toString()
                    viewAuthor.setText(it.author)
                    viewTitle.setText(it.title)
                    viewDescription.setText(it.description)
                    viewContent.setText(it.content)
                }
            })
            isEdit = true
        }

        /* Prepare OnClickListeners for each button:
            Save, Update and Delete.
           They pretty much do the same operations and checks, but use the specific
           insert, update, delete method from the ViewModel.
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

        /* Hide buttons depending on our case: this is a very simplistic UI management
           example, and you need to correctly set the constraints on the Layout to make
           this at least marginally pleasant. There's better ways, of course. :-)
         */
        if (isEdit) {
            /* btnSave calls the dao.save method, which actually creates a new record
               By hiding it, we correctly allow only Update and Delete
             */
            btnSave.visibility = View.GONE
        } else {
            /* No reason to Update or Delete a new Record yet to be saved */
            btnUpdate.visibility = View.GONE
            btnDelete.visibility = View.GONE
        }
    }
}