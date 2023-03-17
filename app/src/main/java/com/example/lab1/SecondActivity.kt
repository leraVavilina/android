package com.example.lab1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
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
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.example.lab1.databinding.ActivityMainBinding
import java.io.File

class SecondActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private  var nameUser ="";
    private  var isAnon = false;
    private var sex = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.second_activity)

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
       var res =  sharedPreference.getString("admin","")

if(res == "true"){

    var button = findViewById<Button>(R.id.searchButton2);
    button.visibility = View.VISIBLE;
     button = findViewById<Button>(R.id.searchButton);
    button.visibility = View.VISIBLE;
     button = findViewById<Button>(R.id.searchButton4);
    button.visibility = View.VISIBLE;
}
        else{
    dataUserFromLogin();
    showDialogLogin();
        }
        subscribeOnTextChangeSeach();
    }

    fun subscribeOnTextChangeSeach(){

        var searchArea = findViewById<TextView>(R.id.searchText)
        searchArea.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s===null){
                    return;
                }
                var otvet = findViewById<TextView>(R.id.resultText);
                otvet.text = "";
                var result =  selectFromDataBase();
                result.map{ zapros ->
                    if(zapros[1].contains(s) || zapros[2].contains(s)){
                        var fullOtvet ="id = "+zapros[0]+" || "+" Термин = "+ zapros[1]+" || Код = "+ zapros[2];

                        otvet.text = otvet.text.toString() + fullOtvet + "\n";
                    }
                }
            }
        })
    }

    fun showDialogLogin(){

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
    }
    fun dataUserFromLogin(){

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
        if(content.contains("sex")) {
            index = content.indexOf("sex:");
            index += 4;
            index2 = content.indexOf(";", index2 + 1);
            sex = content.substring(index, index2);
            dataUser += "Пол: $sex";
        }
    }


    fun addSearchButton(view: View){
        var otvet = findViewById<TextView>(R.id.resultText);
        otvet.text = "";
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    fun redactSearchButton(view: View){
        var otvet = findViewById<TextView>(R.id.resultText);
        otvet.text = "";
        val intent = Intent(this, RedactActivity::class.java)
        startActivity(intent)
    }
    fun deleteSearchButton(view: View){
        var otvet = findViewById<TextView>(R.id.resultText);
        otvet.text = "";
        val intent = Intent(this, DeleteActivity::class.java)
        startActivity(intent)
    }


    @SuppressLint("Range")
    fun selectFromDataBase():ArrayList<Array<String>>{
        var mydЬ = DataBase(this);
        var sqdЬ = mydЬ.writableDatabase;
        //   sqdЬ.delete("search",null,null);

        var query = "SELECT " + mydЬ.UID + ", " + mydЬ.ZAPROS+ ", "+
                mydЬ.CODE+ " FROM " + mydЬ.ТАВLЕ_NАМЕ+" ;";

        var otvet = sqdЬ.rawQuery(query, null);

        var returnOtvet = ArrayList<Array<String>>();
        while (otvet.moveToNext()) {
            var inData = Array<String>(3,{""});
            inData[0] = (otvet.getInt(otvet.getColumnIndex(mydЬ.UID))).toString();
            inData[1] =(otvet.getString(otvet.getColumnIndex(mydЬ.ZAPROS)));
            inData[2] = (otvet.getString(otvet.getColumnIndex(mydЬ.CODE)));
            returnOtvet.add(inData);
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

    @SuppressLint("SuspiciousIndentation")
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
                    dataUser+="\nИмя пользователя:"+this.nameUser+"\n";
                }
                else{
                    dataUser+="Да\n";
                }
            dataUser+="Пол: $sex";

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