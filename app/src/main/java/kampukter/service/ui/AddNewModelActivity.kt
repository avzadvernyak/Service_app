package kampukter.service.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kampukter.service.R
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.add_new_model.*
import org.koin.android.viewmodel.ext.viewModel

class AddNewModelActivity : AppCompatActivity() {

    private val viewModel by viewModel<ServiceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_model)

        setSupportActionBar(newModelToolbar).apply {title = getString(R.string.addNewModel) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        saveNewModelButton.setOnClickListener {
            if (!newModelEditText.text.isEmpty() && newModelEditText.length() > 5) {
                AlertDialog.Builder(this).setTitle(getString(R.string.addNewModel))
                    .setMessage(getString( R.string.queAddNewModel, newModelEditText.text))
                    .setPositiveButton(getString( R.string.addNewModelYes)) { _, _ ->
                        viewModel.addModel(newModelEditText.text.toString())
                        finish()
                    }
                    .setNegativeButton(getString( R.string.addNewModelNo)) { _, _ -> this.finish() }
                    .create().show()
            } else Snackbar.make(
                addNewModelLayout,getString( R.string.addNewModelError),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
