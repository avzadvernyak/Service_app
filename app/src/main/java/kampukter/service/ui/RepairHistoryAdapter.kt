package kampukter.service.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.R
import kampukter.service.data.RepairsView

typealias RepairClickListener<T> = (T) -> Unit

class RepairHistoryAdapter(private val repairClickListener: RepairClickListener<RepairsView>? = null
) : RecyclerView.Adapter<RepairHistoryViewHolder>()  {
    private var repairs: List<RepairsView>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairHistoryViewHolder {
        return RepairHistoryViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.repair_history_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return repairs?.size ?: 0
    }

    override fun onBindViewHolder(holder: RepairHistoryViewHolder, position: Int) {
        repairs?.get(position)?.let { item ->
            holder.bind(item, repairClickListener)
        }
    }

    fun setList(list: List<RepairsView>) {
        this.repairs = list
        notifyDataSetChanged()
    }
}