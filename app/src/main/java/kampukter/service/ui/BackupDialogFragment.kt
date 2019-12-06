package kampukter.service.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kampukter.service.viewmodel.ServiceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BackupDialogFragment : DialogFragment() {
    private val viewModel by viewModel<ServiceViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Make a backup?")
            .setPositiveButton(android.R.string.yes) { _, _ ->
                viewModel.createBackup()
            }
            .setNegativeButton(android.R.string.no) { _, _ -> }
        return builder.create()
    }

    companion object {
        fun create(): BackupDialogFragment = BackupDialogFragment()
    }
}