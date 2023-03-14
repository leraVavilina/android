package com.example.lab1

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.lab1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private  var CHANNEL_ID = "channel"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        var context = getApplicationContext();
        var toastAnon = Toast.makeText(context,"Выбран анонимный профиль", Toast.LENGTH_LONG);
        var toastNotAnon = Toast.makeText(context,"Выбран открытый профиль", Toast.LENGTH_LONG);

        createNotificationChannel();

        var isAnonCheck = findViewById<CheckBox>(R.id.isAnonCheckBox)
        isAnonCheck.setOnClickListener { view ->
            if (view is CheckBox) {
                if (view.isChecked)   {

                    toastAnon.show();
                    toggleImageButton(true);
                    toggleUserName(false);
                }
                else{
                    toastNotAnon.show();
                    toggleImageButton(false);
                    toggleUserName(true);
                }
                var notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(1);
            }
        }


        var editText = findViewById<EditText>(R.id.nameUser);
        editText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    var text = findViewById<TextView>(R.id.title);
                    text.setText("Приложение по поиску из глоссария. Добро пожаловать "+editText.text)
                    // perform action on key press

                    return true
                }
                return false
            }
        })



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "name channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService(
            NotificationManager::class.java
        ).createNotificationChannel(channel)
    }

        fun clickContinue(v:View){
        val intent = Intent(this@MainActivity, SecondActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun acceptNameClick(v:View){
        var editText = findViewById<EditText>(R.id.nameUser);
        var text = findViewById<TextView>(R.id.title);
        var userName = "NoName";
        if( !editText.text.isEmpty()){
            userName = editText.text.toString();
        }
        text.setText("Приложение по поиску из глоссария. Добро пожаловать "+userName)

        var builder = Notification.Builder(this,CHANNEL_ID);
        builder
            .setContentTitle("Логин через имя пользователя")
            .setSmallIcon(androidx.core.R.drawable.notification_template_icon_bg)
            .setContentText(userName)


        var someBuild = builder.build();
        someBuild.defaults = Notification.DEFAULT_VIBRATE;

        var notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1,someBuild );
        toggleImageButton(true);
    }

    fun toggleUserName(visible:Boolean){
        var text = findViewById<TextView>(R.id.title);
        text.setText("Приложение по поиску из глоссария")
        var name = findViewById<EditText>(R.id.nameUser);
        var nameText = findViewById<TextView>(R.id.nameView);
        var button = findViewById<Button>(R.id.acceptButton);
        var vis = View.INVISIBLE;
        if(visible){
            vis = View.VISIBLE;
        }
        name.visibility = vis;
        nameText.visibility = vis;
        button.visibility = vis;

    }

    fun toggleImageButton(visible:Boolean){
        var imageButton = findViewById<ImageButton>(R.id.imageButtonContinue);
        if(visible){
            imageButton.visibility = View.VISIBLE;
        }
        else{
            imageButton.visibility = View.INVISIBLE;
        }
    }



}