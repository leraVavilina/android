package com.example.lab1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.lab1.databinding.ActivityMainBinding


class DrawActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        musicStart = MediaPlayer.create(this, R.raw.melody);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.draw)
    }

    private lateinit var figure:ShapeDrawable;
    private var picture = 1;

    private  var isMusicStart = false;
    private lateinit var musicStart:MediaPlayer;

    fun musicGoButton(view: View){
        var image = findViewById<ImageView>(R.id.imageButton)
        if (!isMusicStart) {
            isMusicStart = true
            image.setImageResource(R.drawable.pause)
            musicStart.start();
        }
        else if(isMusicStart){
            isMusicStart = false
            image.setImageResource(R.drawable.start)
            musicStart.pause();
        };

    }

    @SuppressLint("ResourceType")
    fun nextClick(view:View){
        if(picture==1){
            findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.swappicturejd1)
        }
        else if(picture==2){
            findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.swappicturejd2)
        }
        else if(picture==3){
            findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.swappicturejd3)
        }
        else if(picture==4){
            findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.swappicturejd4)
        }
        picture++;
        if(picture==5){
            picture = 1;
        }
    }

    fun rectButtonClick(view:View){
        figure = ShapeDrawable(RectShape());
        figure.intrinsicHeight =20;
        figure.intrinsicWidth =20;
        figure.paint.color = Color.BLUE;
        findViewById<ImageView>(R.id.imageView2).setImageDrawable(figure)
    }

    fun circleButtonClick(view:View){
        figure = ShapeDrawable(OvalShape());
        figure.intrinsicHeight =20;
        figure.intrinsicWidth =20;
        figure.paint.color = Color.RED;
        findViewById<ImageView>(R.id.imageView2).setImageDrawable(figure)
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