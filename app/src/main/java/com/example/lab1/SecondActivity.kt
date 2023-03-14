package com.example.lab1

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.lab1.databinding.ActivityMainBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var dialog = AlertDialog.Builder(this);
        dialog.setMessage("Вас зовут: ")
            .setCancelable(false)
            .setPositiveButton("Верно", DialogInterface.OnClickListener()
            { dialogInterface, i ->
                dialogInterface.cancel();
            })
            .setNegativeButton("Неверно", DialogInterface.OnClickListener()
            { dialogInterface, i ->
                val intent = Intent(this@SecondActivity, MainActivity::class.java)
                startActivity(intent)
            })
        var alert = dialog.create();
        alert.setTitle("Подтвердите вход");
        alert.show();

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.second_activity)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        title = "Меню"
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var item = item.itemId;
        if(item.equals(R.id.action_search)){
            //search
        }
        else if(item.equals(R.id.action_settings)){
            //settings
        }
        else if(item.equals(R.id.action_draw)){
            val intent = Intent(this, DrawActivity::class.java)
            startActivity(intent)
        }
        else if(item.equals(R.id.action_userdata)){
            var dialog = AlertDialog.Builder(this);
            dialog.setMessage("Вас зовут: ")
                .setPositiveButton("Ок", DialogInterface.OnClickListener()
                { dialogInterface, i ->
                    dialogInterface.cancel();
                })
            var alert = dialog.create();
            alert.setTitle("Данные о пользователе");
            alert.show();
        }
        else if(item.equals(R.id.action_exit)){
            this.finish()
        }
        return true;
    }


}