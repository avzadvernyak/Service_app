package kampukter.service.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.R
import kampukter.service.data.RepairsView

class RepairAdapter(private val context: Context) : RecyclerView.Adapter<RepairViewHolder>() {

    var selectedItemIds: MutableSet<Long> = mutableSetOf()

    private var items = listOf<RepairsView>()
    private var isInSelection = false

    private var actionModeCallback: ActionMode.Callback? = null
    private var selectionCountListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairViewHolder =
        RepairViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repair_item, parent, false),
            clickEventDelegate
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepairViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, selectedItemIds.contains(item.id))
    }

    private val clickEventDelegate: ClickEventDelegate<RepairsView> = object : ClickEventDelegate<RepairsView> {
        override fun onClick(item: RepairsView) {
            if (isInSelection) {
                toggleItemSelection(item)
            }
/*else  actionModeCallback?.let { callback ->
                (context as AppCompatActivity).startActivity(Intent(context, RepairHistoryActivity::class.java))
            }*/
        }

        override fun onLongClick(item: RepairsView) {
            if (!isInSelection) {
                actionModeCallback?.let { callback ->
                    (context as AppCompatActivity).startSupportActionMode(callback)
                    isInSelection = true
                    toggleItemSelection(item)
                }
            }
        }
    }

    private fun toggleItemSelection(item: RepairsView) {
        if (selectedItemIds.contains(item.id)) {
            selectedItemIds.remove(item.id)
        } else {
            selectedItemIds.add(item.id)
        }
        notifyItemChanged(items.indexOf(item))
        selectionCountListener?.invoke(selectedItemIds.size)
    }

    fun setRepair(repairs: List<RepairsView>) {

        val newRepairIds = repairs.map { it.id }
        selectedItemIds = selectedItemIds.filter { newRepairIds.contains(it) }.toMutableSet()

        selectionCountListener?.invoke(selectedItemIds.size)
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition].id == repairs[newItemPosition].id

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = repairs.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition].id == repairs[newItemPosition].id
        })
        items = repairs
        diff.dispatchUpdatesTo(this)
    }

    fun setSelection(repairs: LongArray) {
        if (!repairs.isEmpty()) {
            actionModeCallback?.let<ActionMode.Callback, Unit> { callback ->
                (context as AppCompatActivity).startSupportActionMode(callback)
                isInSelection = true
                selectedItemIds.addAll(repairs.toMutableSet())
                selectionCountListener?.invoke(selectedItemIds.size)
            }
        }
    }

    fun enableActionMode(actionModeCallback: ActionMode.Callback, selectionCountListener: (Int) -> Unit) {
        this.actionModeCallback = actionModeCallback
        this.selectionCountListener = selectionCountListener
    }

    fun endSelection() {
        isInSelection = false
        val selectedItemPositions =
            items.filter { selectedItemIds.contains(it.id) }.map { items.indexOf(it) }
        selectedItemIds.clear()
        selectedItemPositions.forEach { notifyItemChanged(it) }
    }
}