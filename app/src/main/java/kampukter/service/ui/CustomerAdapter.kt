package kampukter.service.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.R
import kampukter.service.data.Customer

typealias CustomerClickListener<T> = (T) -> Unit

class CustomerAdapter(
    private val customerClickListener: CustomerClickListener<Customer>? = null
) : RecyclerView.Adapter<CustomerViewHolder>() {
    private var owner: List<Customer>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.customer_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return owner?.size ?: 0
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        owner?.get(position)?.let { item ->
            holder.bind(item, customerClickListener)
        }
    }

    fun setList(list: List<Customer>) {
        this.owner = list
        notifyDataSetChanged()
    }
}