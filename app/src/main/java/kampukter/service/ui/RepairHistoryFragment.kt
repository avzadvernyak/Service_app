package kampukter.service.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.data.Repair
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.repair_history_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class RepairHistoryFragment : Fragment() {
    private val viewModel by viewModel<ServiceViewModel>()
    private var repairHistoryAdapter: RepairHistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(kampukter.service.R.layout.repair_history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(historyToolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Title"
            subtitle = "Subtitle"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        repairHistoryAdapter = RepairHistoryAdapter()
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = repairHistoryAdapter
        }
        viewModel.repairs.observe(this, Observer { list ->
            repairHistoryAdapter?.setList(list)
            historyToolbar.title = "s/n:"+list.last().serialNumber
            historyToolbar.subtitle= list.last().modelName

            if( list.find { it.issueDate == null } != null) repeatRepairFAB.hide()

            repeatRepairFAB.setOnClickListener {

                fragmentManager?.let {
                    RepeatRepairDialogFragment.create(
                        "Add a repair for device with s/n "+list.last().serialNumber,
                        DialogInterface.OnClickListener { _, _ ->
                            viewModel.addRepair(Repair(
                                serialNumber = list.last().serialNumber,
                                modelId = list.last().modelId,
                                customerId = list.last().customerId,
                                beginDate = Date()
                            ))
                        })
                        .show(it, RepeatRepairDialogFragment.TAG)
                }
            }
        })
        val selectedSerialNumber = arguments?.getString("selectedSerialNumber")
        if (selectedSerialNumber != null) {
            viewModel.setQuerySerialNumber(selectedSerialNumber)
        }

    }
}
