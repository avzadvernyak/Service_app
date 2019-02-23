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
        val idSelectedRepair = intent.getStringExtra(RepairAdapter.EXTRA_MESSAGE)

        viewModel.setQueryId(idSelectedRepair.toLong())
        viewModel.repairsId.observe(this, Observer {
            with(it) {
                idTextView.text = id.toString()
                modelTextView.text = modelName
                customerTextView.text = customerName
                serialNumberTextView.text = serialNumber
                noteEditText.setText(notes)
                defectEditText.setText(defect)
                begDateTextView.text = DateFormat.format(" dd/MM/yyyy", Date(beginDate)).toString()
                stateTextView.text = if (endDate != null && endDate != 0L)
                    "Ready-" + DateFormat.format("dd/MM/yyyy", Date(endDate)).toString()
                else "in work"
                editRepairToolbar.title = serialNumber
                saveRepairButton.setOnClickListener { view ->
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

                    if (endDate != null && endDate != 0L) {
                        viewModel.endRepair(
                            Repair(
                                id = id,
                                customerId = customerId,
                                modelId = modelId,
                                serialNumber = serialNumber,
                                beginDate = beginDate,
                                notes = noteEditText.text.toString(),
                                defect = defectEditText.text.toString(),
                                endDate = 0L,
                                issueDate = issueDate
                            )
                        )
                    } else {
                        viewModel.endRepair(
                            Repair(
                                id = id,
                                customerId = customerId,
                                modelId = modelId,
                                serialNumber = serialNumber,
                                beginDate = beginDate,
                                notes = noteEditText.text.toString(),
                                defect = defectEditText.text.toString(),
                                endDate = System.currentTimeMillis(),
                                issueDate = issueDate
                            )
                        )
                    }
                    Log.d("blablabla", "switch date->" + endDate.toString())
                }
                issueButton.setOnClickListener {
                    AlertDialog.Builder(this@RepairEditActivity).setTitle("Выдача устройства")
                        .setMessage("Устройство $modelName \n s/n $serialNumber .\n Осуществить выдачу?")
                        .setPositiveButton("Да") { _, _ ->
                            viewModel.endRepair(
                                Repair(
                                    id = id,
                                    customerId = customerId,
                                    modelId = modelId,
                                    serialNumber = serialNumber,
                                    beginDate = beginDate,
                                    notes = noteEditText.text.toString(),
                                    defect = defectEditText.text.toString(),
                                    endDate = System.currentTimeMillis(),
                                    issueDate = System.currentTimeMillis()
                                )
                            )
                            finish()
                        }
                        .setNegativeButton("Нет") { _, _ -> finish() }
                        .create().show()

                }

            }
        })
    }
}

