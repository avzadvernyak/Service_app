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
        setSupportActionBar(newCustomerToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        saveNewCustomerButton.setOnClickListener {
            if (!newCustomerEditText.text.isEmpty() && newCustomerEditText.length() > 2) {
                AlertDialog.Builder(this).setTitle("Add new customer")
                    .setMessage(" Add ${newCustomerEditText.text} in base?")
                    .setPositiveButton("YES") { _, _ ->
                        viewModel.addCustomer(newCustomerEditText.text.toString())
                        finish()
                    }
                    .setNegativeButton("NO") { _, _ -> this.finish() }
                    .create().show()
            } else Snackbar.make(
                addNewCustomerLayout,
                "Shouldn't be empty and more than 2 characters.",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}