package kampukter.service.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kampukter.service.R

class RepeatRepairDialogFragment: DialogFragment() {
    private lateinit var myDialogInterfaceListener : DialogInterface.OnClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.addingRepair))
            .setMessage(arguments?.getString(ARG_MESSAGE))
            .setCancelable(false)
            .setPositiveButton( android.R.string.yes, myDialogInterfaceListener)
            .setNegativeButton( android.R.string.no) { _, _ -> }
            .create()
    }
    private fun setOnDialogInterfaceListener(arg: DialogInterface.OnClickListener){
        myDialogInterfaceListener = arg
    }

    companion object {
        const val TAG = "RepeatRepairDialogFragment"
        private const val ARG_MESSAGE = "ARG_MESSAGE"
        fun create(message: String, myListener: DialogInterface.OnClickListener ): RepeatRepairDialogFragment =
            RepeatRepairDialogFragment().apply {
                setOnDialogInterfaceListener(myListener)
                arguments = Bundle().apply {
                    putString(ARG_MESSAGE, message)
                }
            }
    }
}