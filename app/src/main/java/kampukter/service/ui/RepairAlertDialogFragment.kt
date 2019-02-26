package kampukter.service.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kampukter.service.R
import kampukter.service.data.RepairsView
import kampukter.service.viewmodel.ServiceViewModel

class RepairAlertDialogFragment : DialogFragment() {

    private lateinit var viewModel: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(ServiceViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.returnBack))
            .setMessage(arguments?.getString(ARG_MESSAGE))
            .setPositiveButton( android.R.string.yes) { _, _ -> viewModel.endRepairIssue() }
            .setNegativeButton( android.R.string.no) { _, _ -> }
            .create()
    }

    companion object {

        private const val ARG_MESSAGE = "ARG_MESSAGE"

        const val TAG = "RepairAlertDialogFragment"

        fun create(message: String): RepairAlertDialogFragment = RepairAlertDialogFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MESSAGE, message)
            }
        }

    }

/*
    private lateinit var viewModel: ServiceViewModel
    private lateinit var repair: RepairsView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServiceViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.repairsId.observe(this, Observer { it -> repair = it })

        with(repair)
        {
            return AlertDialog.Builder(activity!!).setTitle(getString(R.string.returnBack))
                .setMessage(getString(R.string.deviceWithSN, modelName, serialNumber))
                .setNegativeButton("No") { _, _ -> }.create()
        }
    }
*/
}