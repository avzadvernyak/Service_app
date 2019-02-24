package kampukter.service.ui

import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
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
                val repairEdit = Repair(
                    id = id,
                    customerId = customerId,
                    modelId = modelId,
                    serialNumber = serialNumber,
                    beginDate = beginDate,
                    notes = notes,
                    defect = defect,
                    endDate = endDate,
                    issueDate = issueDate
                )
                idTextView.text = id.toString()
                customerTextView.text = customerName
                defectTextInputEdit.setText(defect)
                noteTextInputEdit.setText(notes)
                begDateTextView.text = DateFormat.format(getString(R.string.dateFormat), beginDate).toString()
                if (endDate != null) {
                    stateTextView.text = getString(R.string.repairStateReady)
                    dateState.text = DateFormat.format(
                        getString(R.string.dateFormat),
                        endDate
                    )
                    stateTextView.setBackgroundColor(Color.GREEN)
                    repairStateButton.text = getString(R.string.repairStateInWork)
                } else {
                    stateTextView.text = getString(R.string.repairStateInWork)
                    dateState.text = ""
                    stateTextView.setBackgroundColor(Color.YELLOW)
                    repairStateButton.text = getString(R.string.repairStateReady)
                }
                editRepairToolbar.title = serialNumber
                editRepairToolbar.subtitle = modelName

                saveEditRepair.setOnClickListener {
                    viewModel.endRepair(
                        repairEdit.copy(
                            notes = noteTextInputEdit.text.toString(),
                            defect = defectTextInputEdit.text.toString()
                        )
                    )
                    finish()
                }
                repairStateButton.setOnClickListener {
                    viewModel.endRepair(
                        if (endDate != null) {
                            repairEdit.copy(
                                notes = noteTextInputEdit.text.toString(),
                                defect = defectTextInputEdit.text.toString(),
                                endDate = null
                            )
                        } else {
                            repairEdit.copy(
                                notes = noteTextInputEdit.text.toString(),
                                defect = defectTextInputEdit.text.toString(),
                                endDate = Date()
                            )
                        }
                    )
                    Log.d("blablabla", "switch date->" + endDate.toString())
                }
                issueButton.setOnClickListener {
                   /*
                    if (savedInstanceState == null) supportFragmentManager.beginTransaction().add(
                        android.R.id.content,
                        RepairAlertDialogFragment()
                    ).commit()
*/
                    AlertDialog.Builder(this@RepairEditActivity).setTitle(getString(R.string.returnBack))
                        .setMessage(getString(R.string.deviceWithSN,modelName,serialNumber))
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.endRepair(
                                repairEdit.copy(
                                    notes = noteTextInputEdit.text.toString(),
                                    defect = defectTextInputEdit.text.toString(),
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

