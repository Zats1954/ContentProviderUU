package ru.zatsoft.permissionsuu

import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.zatsoft.permissionsuu.databinding.ActivityContactsPermissionBinding


class ContactsPermission : AppCompatActivity() {

    private lateinit var toolBar: Toolbar
    private lateinit var binding:ActivityContactsPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "
        val builder = getContacts()
    }

    private fun getContacts(): StringBuilder {
        val resolver: ContentResolver = contentResolver
        var builder = StringBuilder()

        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null,null, null)
        if (cursor!!.count > 0) {
            while (cursor.moveToNext()){
                val id = cursor.getColumnIndex(ContactsContract.Contacts._ID).let{
                     if(it > 0) cursor.getString(it)  else " " }
                val name = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).let{
                    if(it >  0) cursor.getString(it) else " "     }

                val phoneNumber = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER).let{
                    if(it > 0) cursor.getString(it) else " "     }.toInt()

                if (phoneNumber > 0) {
                    val cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                    if(cursorPhone!!.count > 0) {
                        while (cursorPhone.moveToNext()) {
                            val phoneNumValue = cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER).let{
                                if(it > 0) cursor.getString(it) else " "
                            }
                            builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                phoneNumValue).append("\n\n")
                            Log.e("Name ===>",phoneNumValue);
                        }
                    }
                    cursorPhone.close()
                }
            }
        } else {
            Toast.makeText(this, "Ошибка чтения контактов", Toast.LENGTH_LONG).show()
        }
        cursor.close()
        return builder
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