package kampukter.service.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.data.RepairsView
import kotlinx.android.synthetic.main.repair_item.view.*

class RepairViewHolder(
    itemView: View,
    private val clickEventDelegate: ClickEventDelegate<RepairsView>
) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: RepairsView, isSelected: Boolean) {
        with(itemView) {
            setSelected(isSelected)

            titleTextView.text = item.id.toString()
            serialNumTextView.text = item.serialNumber.toString()
            modelNameTextView.text = item.modelName
            customerNameTextView.text  = item.customerName

            setOnClickListener {
                clickEventDelegate.onClick(item)
            }
            setOnLongClickListener {
                clickEventDelegate.onLongClick(item)
                return@setOnLongClickListener true
            }
        }
    }
}