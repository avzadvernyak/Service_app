package kampukter.service.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kampukter.service.R
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.add_new_customer.*
import org.koin.android.viewmodel.ext.viewModel

class AddNewCustomerActivity : AppCompatActivity() {

    private val viewModel by viewModel<ServiceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_customer)
        setSupportActionBar(newCustomerToolbar).apply { title=getString(R.string.addNewCustomer) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        saveNewCustomerButton.setOnClickListener {
            if (!newCustomerEditText.text.isEmpty() && newCustomerEditText.length() > 2) {
                AlertDialog.Builder(this).setTitle(getString(R.string.addNewCustomer))
                    .setMessage(getString(R.string.queAddNewCustomer,newCustomerEditText.text))
                    .setPositiveButton(getString(R.string.addNewCustomerYes)) { _, _ ->
                        viewModel.addCustomer(newCustomerEditText.text.toString())
                        finish()
                    }
                    .setNegativeButton(getString(R.string.addNewCustomerNo)) { _, _ -> this.finish() }
                    .create().show()
            } else Snackbar.make(
                addNewCustomerLayout,getString(R.string.addNewCustomerError),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}