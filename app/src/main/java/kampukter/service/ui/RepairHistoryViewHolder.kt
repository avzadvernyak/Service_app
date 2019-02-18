package kampukter.service.ui

import android.view.View
import kampukter.service.data.RepairsView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.models_item.view.*

class RepairHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(result: RepairsView, repairClickListener: RepairClickListener<RepairsView>?) {

        with(itemView) {
            modelTextView.text = result.modelName
            setOnClickListener { repairClickListener?.invoke(result) }
        }
    }
}