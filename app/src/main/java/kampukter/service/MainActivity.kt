package kampukter.service

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kampukter.service.ui.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) supportFragmentManager.beginTransaction().add(
            android.R.id.content,
            MainFragment()
        ).commit()
    }
}