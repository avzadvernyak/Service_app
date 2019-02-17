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
import kotlinx.android.synthetic.main.customer_choice_fragment.*
import org.koin.android.viewmodel.ext.viewModel

class CustomerFragment : Fragment() {

    private val viewModel by viewModel<ServiceViewModel>()
    private var customerAdapter: CustomerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.customer_choice_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        customerAdapter = CustomerAdapter { item ->
            activity?.run {
                setResult(AppCompatActivity.RESULT_OK, Intent().putExtra(EXTRA_OWNER_ID, item.id.toString()))
                finish()
            }
        }
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = customerAdapter
        }
        viewModel.customer.observe(this, Observer { list ->
            customerAdapter?.setList(list)
        })
        viewModel.clearSearchCustomer()
        addCustomerButton.setOnClickListener{startActivity(Intent(activity, AddNewCustomerActivity::class.java))}
        //addCustomerButton.setOnClickListener { viewModel.addCustomer("Матрица ДЭК") }
        //addCustomerButton.setOnClickListener { viewModel.delAll() } AddNewModelActivity
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.customer_search_toolbar_menu, menu)
        (menu.findItem(R.id.action_search)?.actionView as? SearchView)?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrBlank()) viewModel.clearSearchCustomer() else viewModel.setQueryCustomer(newText)
                    return true
                }
            })
    }

    companion object {
        const val EXTRA_OWNER_ID = "EXTRA_OWNER_ID"
    }
}