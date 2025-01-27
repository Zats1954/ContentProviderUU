package ru.zatsoft.permissionsuu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.zatsoft.permissionsuu.databinding.ActivityMainBinding
import android.Manifest
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var toolBar: Toolbar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "

        binding.btnCamera.setOnClickListener {
            val permission = Manifest.permission.CAMERA
            cameraPermission.launch(permission)
            }

        binding.btnContacts.setOnClickListener {
            val permission = Manifest.permission.READ_CONTACTS
            contactsPermission.launch(permission)
        }
    }

    private val cameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission())
    { isGranted ->
            if(isGranted){
              val intent = Intent(this, CameraView::class.java)
              startActivity(intent)
            }
            else {
              Toast.makeText(this,
                         "Нет разрешения использовать камеру",
                               Toast.LENGTH_LONG).show()
            }
    }

    private val contactsPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission())
    { isGranted ->
        if(isGranted){
            val intent = Intent(this, ContactsPermission1::class.java)
            startActivity(intent)
        }
        else {
            Toast.makeText(this,
                "Нет разрешения использовать контакты",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit)
            finishAffinity()
        return super.onOptionsItemSelected(item)
    }
}