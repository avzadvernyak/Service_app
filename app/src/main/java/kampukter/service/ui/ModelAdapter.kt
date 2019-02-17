package kampukter.service.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.R
import kampukter.service.data.Models

typealias ItemClickListener<T> = (T) -> Unit

class ModelAdapter(
    private val itemClickListener: ItemClickListener<Models>? = null
) : RecyclerView.Adapter<ModelViewHolder>() {
    private var models: List<Models>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        return ModelViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.models_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return models?.size ?: 0
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        models?.get(position)?.let { item ->
            holder.bind(item, itemClickListener)
        }
    }

    fun setList(list: List<Models>) {
        this.models = list
        notifyDataSetChanged()
    }
}