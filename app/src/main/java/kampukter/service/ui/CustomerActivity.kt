package kampukter.service.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) supportFragmentManager.beginTransaction().add(
            android.R.id.content,
            CustomerFragment()
        ).commit()
    }

}