package ru.zatsoft.contentprovideruu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.zatsoft.contentprovideruu.databinding.ActivityMainBinding
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var toolBar: Toolbar
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CustomAdapter
    private var contactList: MutableList<ContactModel>? = null


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
            PackageManager.PERMISSION_GRANTED){
            contactsPermission.launch(Manifest.permission.READ_CONTACTS)
             adapter.notifyDataSetChanged()
        } else{
            getContact()
        }
    }

    private val contactsPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission())
    { isGranted ->
        if(isGranted){
            Toast.makeText(this,
                "Есть разрешение использовать контакты",
                Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this,
                "Нет разрешения использовать контакты",
                Toast.LENGTH_LONG).show()
        }
    }

    private val callPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission())
    { isGranted ->
        if(isGranted){
            Toast.makeText(this,
                "Есть разрешение использовать звонки",
                Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this,
                "Нет разрешения использовать звонки",
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

    @SuppressLint("Range")
    private fun getContact(){
        contactList = ArrayList()
        val phones = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        while(phones!!.moveToNext()){
            val name =
                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber =
                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactModel = ContactModel(name, phoneNumber)
            contactList?.add(contactModel)
        }
        phones.close()
        adapter = CustomAdapter(contactList!!)
        binding.listView.adapter = adapter
        binding.listView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.listView.addItemDecoration(MyItemDecoration(this, R.drawable.divider))
        binding.listView.setHasFixedSize(true)

        adapter.setOnContactClickListener  (
            object: CustomAdapter.OnContactClickListener {
                override fun onContactClick(contact:ContactModel, position: Int){
                    val number = contact.phone
                    if(ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED){
                        callPermission.launch(Manifest.permission.CALL_PHONE)
                    } else {
                        callNumber(number)
                    }
                }
            })
        }

    private fun callNumber(number: String?){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }
}