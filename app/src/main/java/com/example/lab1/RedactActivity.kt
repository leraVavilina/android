package com.example.lab1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RedactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.redact_activity)

       redact();

    }

    fun prevButtonClick(){
        this.finish()
    }

    fun redact(){
        var data =  selectFromDataBase();
        var table = findViewById<TableLayout>(R.id.tableRedact);
        table.removeAllViews();
        data.map { dataTable ->
            var tableRow = TableRow(this)
            var editsText = TextView(this);
            editsText.text = "id = "+dataTable[0];
            var zaprosText =  EditText(this);
            zaprosText.hint = dataTable[1]
            var codeText =  EditText(this);
            codeText.hint = dataTable[2]
            zaprosText.width = 500;
            codeText.width = 100;
            var button = Button(this);
            button.text = "Сохранить";
            button.setOnClickListener { // Обработка нажатия
                var zapros = zaprosText.text.toString();
                var code = codeText.text.toString();
                if(zapros.isEmpty()){
                    zapros = dataTable[1]
                }
                if(code.isEmpty()){
                    code = dataTable[2]
                }
                SaveChangesButton(dataTable[0],zapros,code)
            }
            tableRow.addView(editsText);
            tableRow.addView(zaprosText);
            tableRow.addView(codeText);
            tableRow.addView(button);
            tableRow.gravity = Gravity.CENTER;
            table.addView(tableRow)
        }
    }

    fun SaveChangesButton(id:String, zapros:String, code:String){
        var mydЬ = DataBase(this);
        var sqdЬ = mydЬ.writableDatabase;
        val values = ContentValues()

        values.put(mydЬ.ZAPROS, zapros)
        values.put(mydЬ.CODE, code)

        sqdЬ.update(mydЬ.ТАВLЕ_NАМЕ,values,mydЬ.UID +" = "+id, arrayOf());

        sqdЬ. close () ;
        mydЬ.close();

        redact()
    }

    @SuppressLint("Range")
    fun selectFromDataBase():ArrayList<Array<String>>{
        var mydЬ = DataBase(this);
        var sqdЬ = mydЬ.writableDatabase;

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
}