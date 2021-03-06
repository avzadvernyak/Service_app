package kampukter.service.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kampukter.service.R
import kampukter.service.data.Repair
import kampukter.service.data.RepairState
import kampukter.service.ui.BarCodeReadActivity.Companion.EXTRA_CODE
import kampukter.service.ui.CustomerFragment.Companion.EXTRA_OWNER_ID
import kampukter.service.ui.ModelFragment.Companion.EXTRA_MODEL_ID
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.service_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ServiceActivity : AppCompatActivity() {

    private val viewModel by viewModel<ServiceViewModel>()
    var modelIdAdd = 0L
    var customerIdAdd = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.service_activity)
        setSupportActionBar(newRepairToolbar).apply { title = getString(R.string.addNewRepair) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        scanCodebutton.setOnClickListener {
            startActivityForResult(Intent(this, BarCodeReadActivity::class.java), SCAN_CODE_REQUEST)
        }
        modelRequestbutton.setOnClickListener {
            startActivityForResult(
                Intent(this, ModelActivity::class.java),
                PICK_MODEL_REQUEST
            )
        }
        ownerRequestbutton.setOnClickListener {
            startActivityForResult(
                Intent(this, CustomerActivity::class.java),
                PICK_OWNER_REQUEST
            )
        }

        viewModel.modelId.observe(this, Observer { it ->
            modelTextView.text = it.title
            modelIdAdd = it.id
        })
        viewModel.customerId.observe(this, Observer { it ->
            customerTextView.text = it.title
            customerIdAdd = it.id
        })
        viewModel.repairs.observe(this, Observer { it ->
            if (it.count() > 0 && serialTextView.text.toString() == it.last().serialNumber) {
                viewModel.setModelId(it.first().modelId)
                viewModel.setCustomerId(it.first().customerId)
            }
        })

        viewModel.repairState.observe(this, Observer { repairState ->
            when (repairState) {
                is RepairState.Success -> {
                    val result = Repair(
                        serialNumber = serialTextView.text.toString(),
                        modelId = modelIdAdd,
                        customerId = customerIdAdd,
                        beginDate = Date()
                    )
                    Log.d("blablabla", "Add to base")
                    viewModel.addRepair(result)
                    finish()
                }
                is RepairState.Failure -> {
                    Log.d("blablabla", repairState.reason)
                    customerTextView.text = ""
                    modelTextView.text = ""
                    serialTextView.setText("")
                    Snackbar.make(
                        serviceActivityLayout,
                        "Data already exists.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })
        serialTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length > 4) {
                    viewModel.setQuerySerialNumber("%" + s.toString() + "%")
                }
            }
        })
        saveNewRepairButton.setOnClickListener {
            if (serialTextView.text.isNotEmpty() && modelIdAdd != 0L && customerIdAdd != 0L) {
                viewModel.setQuerySNRepair(serialTextView.text.toString())
                // TRUE if added new repair
                setResult(RESULT_OK, Intent().putExtra(EXTRA_CODE_ADD, true))
                finish()
            } else Snackbar.make(
                serviceActivityLayout, getString(R.string.addNewRepairError),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }


    public override fun onActivityResult(
        requestCode: Int,
        resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                SCAN_CODE_REQUEST -> data?.extras?.getString(EXTRA_CODE)?.let { code ->
                    serialTextView.setText(code)
                }

                PICK_MODEL_REQUEST -> data?.extras?.getString(EXTRA_MODEL_ID)?.let { modelId ->
                    viewModel.setModelId(modelId.toLong())
                }

                PICK_OWNER_REQUEST -> data?.extras?.getString(EXTRA_OWNER_ID)?.let { ownerId ->
                    viewModel.setCustomerId(ownerId.toLong())
                }

            }
        }
    }

    companion object {
        const val SCAN_CODE_REQUEST = 1
        const val PICK_MODEL_REQUEST = 2
        const val PICK_OWNER_REQUEST = 3
        const val EXTRA_CODE_ADD = "EXTRA_CODE_ADD"
    }
}