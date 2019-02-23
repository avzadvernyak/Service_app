package kampukter.service.ui

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kampukter.service.R
import kampukter.service.data.Repair
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.repair_edit.*
import org.koin.android.viewmodel.ext.viewModel
import java.util.*

class RepairEditActivity : AppCompatActivity() {

    private val viewModel by viewModel<ServiceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.repair_edit)

        setSupportActionBar(editRepairToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val idSelectedRepair = intent.getStringExtra(RepairAdapter.EXTRA_MESSAGE)

        viewModel.setQueryId(idSelectedRepair.toLong())
        viewModel.repairsId.observe(this, Observer {
            with(it) {
                idTextView.text = id.toString()
                customerTextView.text = customerName
                noteEditText.setText(notes)
                defectEditText.setText(defect)
                begDateTextView.text = DateFormat.format(getString(R.string.dateFormat), beginDate).toString()
                if (endDate != null) {
                    stateTextView.text =
                        getString(R.string.repairStateReady) + DateFormat.format(
                            getString(R.string.dateFormat),
                            endDate
                        ).toString()
                    repairStateButton.text = getString(R.string.repairStateInWork)
                } else {
                    stateTextView.text = getString(R.string.repairStateInWork)
                    repairStateButton.text = getString(R.string.repairStateReady)
                }
                editRepairToolbar.title = serialNumber
                editRepairToolbar.subtitle = modelName
                saveEditRepair.setOnClickListener {
                    viewModel.endRepair(
                        Repair(
                            id = id,
                            customerId = customerId,
                            modelId = modelId,
                            serialNumber = serialNumber,
                            beginDate = beginDate,
                            notes = noteEditText.text.toString(),
                            defect = defectEditText.text.toString(),
                            endDate = endDate,
                            issueDate = issueDate
                        )
                    )
                    finish()
                }
                repairStateButton.setOnClickListener {
                    viewModel.endRepair(
                        if (endDate != null) {
                            Repair(
                                id = id,
                                customerId = customerId,
                                modelId = modelId,
                                serialNumber = serialNumber,
                                beginDate = beginDate,
                                notes = noteEditText.text.toString(),
                                defect = defectEditText.text.toString(),
                                endDate = null,
                                issueDate = issueDate
                            )
                        } else {
                            Repair(
                                id = id,
                                customerId = customerId,
                                modelId = modelId,
                                serialNumber = serialNumber,
                                beginDate = beginDate,
                                notes = noteEditText.text.toString(),
                                defect = defectEditText.text.toString(),
                                endDate = Date(),
                                issueDate = issueDate
                            )
                        }
                    )
                    Log.d("blablabla", "switch date->" + endDate.toString())
                }
                issueButton.setOnClickListener {
                    AlertDialog.Builder(this@RepairEditActivity).setTitle(getString(R.string.returnBack))
                        .setMessage(getString(R.string.deviceWithSN)+"\n $modelName - $serialNumber")
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.endRepair(
                                Repair(
                                    id = id,
                                    customerId = customerId,
                                    modelId = modelId,
                                    serialNumber = serialNumber,
                                    beginDate = beginDate,
                                    notes = noteEditText.text.toString(),
                                    defect = defectEditText.text.toString(),
                                    endDate = Date(),
                                    issueDate = Date()
                                )
                            )
                            finish()
                        }
                        .setNegativeButton("No") { _, _ -> }
                        .create().show()

                }

            }
        })
    }
}

