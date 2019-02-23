package kampukter.service

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Dao
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kampukter.service.data.Models
import kampukter.service.data.repository.ServiceDatabase
import kampukter.service.ui.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) supportFragmentManager.beginTransaction().add(
            android.R.id.content,
            MainFragment()
        ).commit()
    }
}