package kampukter.service.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RepairHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idSelectedItem = intent.getStringExtra(EXTRA_MESSAGE).toLong()

        if (savedInstanceState == null) supportFragmentManager.beginTransaction().add(
            android.R.id.content,
            RepairHistoryFragment()
        ).commit()
    }

    companion object {
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }
}