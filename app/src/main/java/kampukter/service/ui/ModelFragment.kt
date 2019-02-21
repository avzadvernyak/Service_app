package kampukter.service.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kampukter.service.R
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.model_choice_fragment.*
import org.koin.android.viewmodel.ext.viewModel

class ModelFragment : Fragment() {

    private val viewModel by viewModel<ServiceViewModel>()

    private var modelAdapter: ModelAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.model_choice_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        modelAdapter = ModelAdapter { item ->
            activity?.run {
                setResult(AppCompatActivity.RESULT_OK, Intent().putExtra(EXTRA_MODEL_ID, item.id.toString()))
                finish()
            }
        }

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = modelAdapter
        }
        viewModel.models.observe(this, Observer { list ->
            modelAdapter?.setList(list)
        })
        viewModel.clearSearch()

        addModelButton.setOnClickListener{startActivity(Intent(activity, AddNewModelActivity::class.java))}
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.model_search_toolbar_menu, menu)
        (menu.findItem(R.id.action_search)?.actionView as? SearchView)?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrBlank()) viewModel.clearSearch() else viewModel.setQuery(newText)
                    return true
                }
            })
    }

    companion object {
        const val EXTRA_MODEL_ID = "EXTRA_MODEL_ID"
    }
}