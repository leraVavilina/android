package com.example.lab1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.example.lab1.databinding.ActivityMainBinding
import java.io.File

class SecondActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private  var nameUser ="";
    private  var isAnon = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.second_activity)

        val content = File(filesDir, FILE_NАМЕ).readText()

        var dataUser  = "Анонимно: ";
        var index2 = 0;
        var index = content.indexOf("anon:");
        index+=5;
        index2 = content.indexOf(";");
        if(content.substring(index,index2).equals("false")){
            dataUser+="Нет";
            isAnon = false;
        }
        else{
            dataUser+="Да";
            isAnon = true;
        }
        dataUser += "\n";
        if(content.contains("name")){
            dataUser+="Имя:";
            var index = content.indexOf("name:");
            index+=5;
            index2 = content.indexOf(";",index2+1);
            nameUser = content.substring(index,index2);
            dataUser+=nameUser;
        }

        var dialog = AlertDialog.Builder(this);
        var mes = "";
        if(isAnon){
            mes ="Анонимный вход"
        }
        else{
            mes ="Вас зовут: "+nameUser
        }
        dialog.setMessage(mes)
            .setCancelable(false)
            .setPositiveButton("Верно", DialogInterface.OnClickListener()
            { dialogInterface, i ->
                dialogInterface.cancel();
            })
            .setNegativeButton("Неверно", DialogInterface.OnClickListener()
            { dialogInterface, i ->
                File(filesDir, FILE_NАМЕ).writeText("");
                val intent = Intent(this@SecondActivity, MainActivity::class.java)
                startActivity(intent)
            })
        var alert = dialog.create();
        alert.setTitle("Подтвердите вход");
        alert.show();

        if(!isAnon){
            findViewById<TextView>(R.id.userNameText2).text = "Имя пользователя:\n"+nameUser;
        }
        else{
            findViewById<TextView>(R.id.userNameText2).text = "Анонимный вход";
        }

        var searchArea = findViewById<TextView>(R.id.searchText)
        searchArea.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
          if(s===null){
              return;
          }
                var otvet = findViewById<TextView>(R.id.resultText);
                otvet.text = "";
           var result =  selectFromDataBase();
                result.map{ zapros ->
                    if(zapros.contains(s)){
                        var fullOtvet ="";

                        var index2 = zapros.indexOf(';');
                        var index = zapros.indexOf("zapros:");
                        index+=7;
                        index2 = zapros.indexOf(";", index2+1);
                        fullOtvet = "Термин => "+ zapros.substring(index,index2);
                        index = zapros.indexOf("code:");
                        index+=5;
                        index2 = zapros.indexOf(";",index2+1);
                        fullOtvet += "Код => "+ zapros.substring(index,index2);

                        otvet.text = otvet.text.toString() + fullOtvet + "\n";
                    }
                }
            }
        })
    }

    fun addSearchButton(view: View){
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    fun searchButton(view: View){
    }

    fun insertInDataBase(insertZapros: String, insertCode: String){
        // zaproc, code
        var mydЬ = DataBase(this);
        var sqdЬ = mydЬ.writableDatabase;

        var insertQuery = "INSERT INTO " +  mydЬ.ТАВLЕ_NАМЕ +
                " (" + mydЬ.ZAPROS + ", "+mydЬ.CODE+") VALUES ('" +
                insertZapros+ "','"+insertCode+"')";

        sqdЬ.execSQL(insertQuery);
        sqdЬ. close () ;
        mydЬ.close();
    }

    @SuppressLint("Range")
    fun selectFromDataBase():ArrayList<String>{
        var mydЬ = DataBase(this);
        var sqdЬ = mydЬ.writableDatabase;

        var query = "SELECT " + mydЬ.UID + ", " + mydЬ.ZAPROS+ ", "+
                mydЬ.CODE+ " FROM " + mydЬ.ТАВLЕ_NАМЕ+" ;";

       var otvet = sqdЬ.rawQuery(query, null);

        var returnOtvet = ArrayList<String>();
        while (otvet.moveToNext()) {
            var id = otvet.getInt(otvet.getColumnIndex(mydЬ.UID));
            var zapros = otvet.getString(otvet.getColumnIndex(mydЬ.ZAPROS));
            var code = otvet.getString(otvet.getColumnIndex(mydЬ.CODE));
            returnOtvet.add("id:"+id+";zapros:"+zapros+";code:"+code+";");
        }
        sqdЬ. close () ;
        mydЬ.close();

        return returnOtvet;
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        title = "Меню"
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val content = File(filesDir, FILE_NАМЕ).readText()
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
            var dataUser  = "Анонимно: ";
                if(!this.isAnon){
                    dataUser+="Нет";
                    dataUser+="\nИмя пользователя:"+this.nameUser;
                }
                else{
                    dataUser+="Да\n";
                }
                dataUser += "";

            dialog.setMessage(dataUser)
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
        else if(item.equals(R.id.action_web)){
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }
        else if(item.equals(R.id.action_exit_person_cabinet)){
            File(filesDir, FILE_NАМЕ).writeText("anon:true;");
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return true;
    }


}