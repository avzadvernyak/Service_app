package kampukter.service.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.data.Models
import kotlinx.android.synthetic.main.models_item.view.*

class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(result: Models, itemClickListener: ItemClickListener<Models>?) {

        with(itemView) {
          modelTextView.text = result.title
            setOnClickListener { itemClickListener?.invoke(result) }
        }
    }

}