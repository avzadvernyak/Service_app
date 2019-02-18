package kampukter.service.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.repair_history_fragment.*
import org.koin.android.viewmodel.ext.viewModel


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

        })
        val selectedSerialNumber = arguments?.getString("selectedSerialNumber")
        if (selectedSerialNumber != null) {
            viewModel.setQuerySerialNumber(selectedSerialNumber)
        }
    }
}
