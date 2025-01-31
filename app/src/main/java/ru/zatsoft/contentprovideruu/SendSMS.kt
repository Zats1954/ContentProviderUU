package ru.zatsoft.contentprovideruu

import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.zatsoft.contentprovideruu.databinding.ActivitySendSmsBinding


class SendSMS : AppCompatActivity() {
    private lateinit var binding: ActivitySendSmsBinding
    private lateinit var toolBar: Toolbar
    private var phone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendSmsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "
        supportActionBar?.setTitle(" ")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarMain.setNavigationOnClickListener {
            onBackPressed()
        }

        phone = intent.getStringExtra("phone")
        binding.tvPhone.text = getString(R.string.telephone_message, phone)
        binding.btnSend.setOnClickListener {
            sendSMS(binding.etMessage.editText!!.text.toString())
        }
    }

    private fun sendSMS(message: String) {
        val smsManager: SmsManager
        try {
//            if (Build.VERSION.SDK_INT>=23) {
//                smsManager =  applicationContext.getSystemService(SmsManager::class.java)
//            } else{
            smsManager = SmsManager.getDefault()
//            }
            smsManager.sendTextMessage(phone, null, message, null, null)
            Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext, "Please enter all the data.." + e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}