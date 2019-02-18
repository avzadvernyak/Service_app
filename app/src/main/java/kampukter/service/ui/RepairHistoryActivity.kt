package kampukter.service.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kampukter.service.ui.RepairAdapter.Companion.EXTRA_MESSAGE


class RepairHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idSelectedItem = intent.getStringExtra(EXTRA_MESSAGE)

        val bundle = Bundle()
        bundle.putString("selectedSerialNumber", idSelectedItem )
        val repairHistoryFragment = RepairHistoryFragment()
        repairHistoryFragment.arguments = bundle


        if (savedInstanceState == null) supportFragmentManager.beginTransaction().add(
            android.R.id.content,
            repairHistoryFragment
        ).commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = idSelectedItem
    }
}