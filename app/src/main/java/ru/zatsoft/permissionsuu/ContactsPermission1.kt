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
import androidx.recyclerview.widget.LinearLayoutManager
import ru.zatsoft.permissionsuu.databinding.ActivityContactsPermissionBinding


class ContactsPermission1 : AppCompatActivity() {

    private lateinit var toolBar: Toolbar
    private lateinit var binding:ActivityContactsPermissionBinding
    private lateinit var adapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "
        val builder = getContacts()
        adapter = CustomAdapter(builder)
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.addItemDecoration(MyItemDecoration(this, R.drawable.divider))
        binding.rvList.setHasFixedSize(true)

    }

    private fun getContacts(): List<Contact> {

       return listOf(
           Contact("Александр Максименко","+4953365675"),
           Contact("Давид Волк","+4951259073"),
           Contact("Илья Лантратов","+4952254265"),
           Contact("Станислав Агкацев","+4953005195"),
           Contact("Александр Солдатенков","+4953772154"),
           Contact("Арсен Адамов","+4951683476"),
           Contact("Валентин Пальцев","+4954285520"),
           Contact("Вячеслав Караваев","+4954416721"),
           Contact("Данил Круговой","+4952214640"),
           Contact("Евгений Морозов","+4953452285"),
           Contact("Игорь Дивеев","+4995641250"),
           Contact("Илья Самошников","+4953416724"),
           Contact("Максим Осипенко","+4952976702"),
           Contact("Юрий Горшков","+4954580059"),
           Contact("Александр Черников","+4952733408"),
           Contact("Алексей Батраков","+4952558712"),
           Contact("Алексей Миранчук","+4951391184")
       )
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