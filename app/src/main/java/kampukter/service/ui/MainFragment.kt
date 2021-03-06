package kampukter.service.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kampukter.service.R
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import android.app.Activity.RESULT_OK
import kampukter.service.data.Filter
import kampukter.service.ui.ServiceActivity.Companion.EXTRA_CODE_ADD
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val KEY_SELECTED_ITEMS = "KEY_SELECTED_ITEMS"

class MainFragment : Fragment() {

    private var repairAdd: Boolean = false

    private val viewModel by viewModel<ServiceViewModel>()

    private var repairAdapter: RepairAdapter? = null

    private var actionMode: ActionMode? = null

    private val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.action_1 -> repairAdapter?.let {
                    repairAdapter?.selectedItemIds?.let {
                        viewModel.setSelected(it.toList())
                    }
                }
                R.id.action_2 -> repairAdapter?.let {
                    repairAdapter?.selectedItemIds?.let {

                        //viewModel.deleteSelected(it.toSet())
                    }
                }
            }
            mode?.finish()
            return true
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            actionMode = mode
            mode?.menuInflater?.inflate(R.menu.main_fragment_menu, menu)
            mainFragmentToolbar.visibility = View.GONE
            addRepairButton.visibility = View.GONE
            actionMode?.subtitle = mainFragmentToolbar.subtitle
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            repairAdapter?.endSelection()
            mainFragmentToolbar.visibility = View.VISIBLE
            addRepairButton.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? AppCompatActivity)?.setSupportActionBar(mainFragmentToolbar)

        val layoutmanager = androidx.recyclerview.widget.LinearLayoutManager(context)

        repairAdapter = RepairAdapter(view.context).apply {
            enableActionMode(actionModeCallback) { count ->
                actionMode?.title = getString(R.string.main_fragment_action_mode_title, count)
                if (count == 0) {
                    actionMode?.finish()
                    repairAdapter?.endSelection()
                }
            }
        }
        with(repairRecyclerView) {
            adapter = repairAdapter
            layoutManager = layoutmanager
        }

        viewModel.repairsView.observe(this, Observer { repairs ->
            if (repairAdd) {
                layoutmanager.scrollToPosition(0)
                repairAdd = false
            }
            repairAdapter?.setRepair(repairs)
        })
        viewModel.repairToSend.observe(
            this@MainFragment,
            Observer { sendResultString ->
                sendResultString.let {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, it)
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(sendIntent, "My Send"))
                }
            })
        if (viewModel.getQueryString().isNullOrEmpty()) viewModel.setQueryString("")
        addRepairButton.setOnClickListener {
            startActivityForResult(Intent(activity, ServiceActivity::class.java), PICK_RESULT_ADD)
        }

        savedInstanceState?.let { bundle ->
            bundle.getLongArray(KEY_SELECTED_ITEMS)?.let { selectedItemIds ->
                repairAdapter?.setSelection(selectedItemIds)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        repairAdapter?.selectedItemIds?.let { selectedItemIds ->
            outState.putLongArray(KEY_SELECTED_ITEMS, selectedItemIds.toLongArray())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_search_toolbar_menu, menu)

        val mySQMenu = (menu.findItem(R.id.action_search)?.actionView as? SearchView)
        val searchString: CharSequence? = viewModel.getQueryString()

        if (!searchString.isNullOrEmpty()) {
            menu.findItem(R.id.action_search).expandActionView()
            mySQMenu?.setQuery(searchString, false)
        }
        mySQMenu?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrBlank()) viewModel.setQueryString("")
                    else viewModel.setQueryString(newText)
                    return true
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter_all -> {
                viewModel.setFilter(Filter.ALL)
                mainFragmentToolbar.subtitle = ""
            }
            R.id.action_filter_ready -> {
                viewModel.setFilter(Filter.WITHOUT_DATE)
                mainFragmentToolbar.subtitle = "Готовые"
            }
            R.id.action_filter_issue -> {
                viewModel.setFilter(Filter.WITH_DATE)
                mainFragmentToolbar.subtitle = "Выданные"
            }
            R.id.action_filter_in_work -> {
                viewModel.setFilter(Filter.IN_WORK)
                mainFragmentToolbar.subtitle = "В работе"
            }
            R.id.action_create_backup ->{
                fragmentManager?.let {
                    BackupDialogFragment.create().show(it, "makeBackup")}
                //viewModel.createBackup()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_RESULT_ADD) {
            if (resultCode == RESULT_OK) {
                data?.extras?.getBoolean(EXTRA_CODE_ADD)?.let { result ->
                    Log.d("blablabla", "Return from AddActivity " + repairAdd.toString())
                    repairAdd = result
                }
            }
        }
    }

    companion object {
        const val PICK_RESULT_ADD = 1
    }
}