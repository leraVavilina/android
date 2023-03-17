package com.example.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DeleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deletes_activity)

       del();

    }

    fun prevButtonClick(){
        this.finish()
    }

    fun del(){
        var data =  selectFromDataBase();
        var table = findViewById<TableLayout>(R.id.tableRedact);
        table.removeAllViews();
        data.map { dataTable ->
            var tableRow = TableRow(this)
            var editsText = TextView(this);
            editsText.text = "id = "+dataTable[0]+" || "+" Термин = "+ dataTable[1]+" || Код = "+ dataTable[2];
            editsText.width = 500;
            var button = Button(this);
            button.text = "Удалить";
            button.setOnClickListener { // Обработка нажатия
                deleteButton(dataTable[0])
            }
            tableRow.addView(editsText);
            tableRow.addView(button);
            tableRow.gravity = Gravity.CENTER;
            table.addView(tableRow)
        }
    }

    fun deleteButton(id:String){
        var mydЬ = DataBase(this);
        var sqdЬ = mydЬ.writableDatabase;

        sqdЬ.delete(mydЬ.ТАВLЕ_NАМЕ, mydЬ.UID+" = "+id,null);

        sqdЬ. close () ;
        mydЬ.close();
        del()
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