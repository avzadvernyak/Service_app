package kampukter.service.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.data.Customer
import kotlinx.android.synthetic.main.customer_item.view.*

class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(result: Customer, customerClickListener: CustomerClickListener<Customer>?) {

        with(itemView) {
            customerTextView.text = result.title
            setOnClickListener { customerClickListener?.invoke(result) }
        }
    }

}