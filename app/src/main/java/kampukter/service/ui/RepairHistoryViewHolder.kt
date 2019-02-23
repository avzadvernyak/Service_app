package kampukter.service.ui

import android.text.format.DateFormat
import android.view.View
import kampukter.service.data.RepairsView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.repair_history_item.view.*
import java.util.*

class RepairHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(result: RepairsView, repairClickListener: RepairClickListener<RepairsView>?) {

        with(itemView) {
            idTextView.text = result.id.toString()
            beginDateTextView.text = DateFormat.format("dd/MM/yyyy", result.beginDate).toString()
            if (result.endDate != null) endDateTextView.text =
                DateFormat.format("dd/MM/yyyy", result.endDate).toString()
            else endDateTextView.text = "in work"
            defectTextView.text = result.defect
            customerTextView.text = result.customerName
            notesTextView.text = result.notes
            if (result.issueDate != null) issueTextView.text =
                DateFormat.format("dd/MM/yyyy", result.issueDate).toString()
            else issueTextView.text = "in work"

            setOnClickListener { repairClickListener?.invoke(result) }
        }
    }
}