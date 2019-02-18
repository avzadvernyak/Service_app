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
import kampukter.service.R
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.model_choice_fragment.*
import org.koin.android.viewmodel.ext.viewModel

class RepairHistoryFragment: Fragment() {
    private val viewModel by viewModel<ServiceViewModel>()
    private var repairHistoryAdapter: RepairHistoryAdapter? = null
    private val selectedSerialNumber: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.repair_history_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        viewModel.setQuerySerialNumber(selectedSerialNumber)
        repairHistoryAdapter = RepairHistoryAdapter()
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = repairHistoryAdapter
        }
        viewModel.repairs.observe(this, Observer { list ->
            repairHistoryAdapter?.setList(list)
        })
        viewModel.clearSearchSerialNumber()

    }
}