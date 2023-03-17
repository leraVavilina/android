package com.example.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.example.lab1.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.add_activity)

    }

    @SuppressLint("Range", "Recycle")
    fun insertInDataBase(insertZapros: String, insertCode: String){
        var mydЬ = DataBase(this);
        var sqdЬ = mydЬ.writableDatabase;

        var id = 0;

        var query = "SELECT " + mydЬ.UID + ", " + mydЬ.ZAPROS+ ", "+
                mydЬ.CODE+ " FROM " + mydЬ.ТАВLЕ_NАМЕ+" ;";

        var otvet = sqdЬ.rawQuery(query, null);
        while (otvet.moveToNext()) {
            id = otvet.getInt(otvet.getColumnIndex(mydЬ.UID));
        }
        id++;


        var insertQuery = "INSERT INTO " +  mydЬ.ТАВLЕ_NАМЕ +
                " (" + mydЬ.UID +", " + mydЬ.ZAPROS + ", "+mydЬ.CODE+") VALUES ('"+id+"','" +
                insertZapros+ "','"+insertCode+"')";

        sqdЬ.execSQL(insertQuery);
        sqdЬ. close () ;
        mydЬ.close();
    }

    fun addSearchButton(view: View){
        var zapros = findViewById<TextView>(R.id.searchText);
        var code = findViewById<TextView>(R.id.codeText);
        var error = findViewById<TextView>(R.id.messageText);
        error.text = "";
        var isError = false;
        if(zapros.length()<1){
            error.text = "Ошибка термина => Слишком короткий термин.\n";
            isError = true;
        }
        try {
            code.text.toString().toInt();
        }
        catch (ex:java.lang.Exception){
            error.text=error.text.toString() + "Ошибка кода => Не число";
            isError = true;
        }
        if(!isError){
            insertInDataBase(zapros.text.toString(),code.text.toString())
            error.text = "Успешно добавлено";
        }
    }

    fun prevButton(view: View){
        this.finish();
    }

}