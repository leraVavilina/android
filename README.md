# Колисниченко Главы 100-106
```
<android:layout_width="fill_parent"
android:layout_height="fill_parent"
>
<TextView
android:id="@+id/txtl"
android:layout_width="fill_parent"
android:layout_height="wrap'---content"
android:textSize="20pt"
android:textStyle="bold"
android:text="Hello"
/>
</LinearLayout>">
```

Результат внесенных в код изменений показан на рис. 4.15 .

<p align="center">Рис. 4.15. Изменение размера и стиля шрифта</p>

Теперь попробуем усложнить нашу задачу и изменить текст нашего виджета из
Jаvа-кода. Для этого откройте файл, содержащий Jаvа-код нашего приложения:
<название naкeтa>\MainActivity.java. В моем случае этот файл называется com.example.den.
ch4\МainActivity.java. На рис. 4.16 показано его содержимое по умолчанию, а также
область навигации, объясняющая, как до него «добраться».

Измените Jаvа-файл так, чтобы он выглядел, как показано в листинге 4.9. Строки,
которые нужно добавить в ваш Jаvа-файл, выделены полужирным шрифтом.

<p align="center">Рис. 4.16. Файл MainAclivity.java</p>

> Листинг 4.9 Файл MainActivity.java
```
package com.example.ch04;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ТextView;
puЬlic class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedinstanceState){
    super.onCreate(savedinstanceState);
    setContentView(R.layout.activity_main);
    TextView txtl = (T~tView) findViewВyid (R. id. txtl) ;
    txt1.setText("Hi!");
  }
}
```
Теперь запустите приложение. В качестве значения текстового поля будет установлена строка "Нi!" (рис. 4.17).

Текстовое поле *EditText* позволяет вводить и редактировать текст. Основной метод
этого поля - метод *getText()*, позволяющий получить введенный пользователем текст. Значение, возвращаемое методом *getText()*, имеет тип *Editable*. По сути,
*EditaЫe* - это надстройка над *String*, но в отличие от него, значение типа *EditaЫe*
может быть изменено в процессе выполнения программы (*string* является неизменяемым типом - при изменении значения типа string nonpocry создается новый
экземпляр *String*, содержащий новое значение).

<p align="center">Рис. 4.17. Результат работы программы из листинга 4.9 </p>

Кроме метода *getText()* вам может понадобиться метод *selectAll()*, выделяющий
весь текст в окне. Если весь текст выделять не нужно, можно использовать метод

```
setSelection():
setSelection(int start, int stop)
```

Этот метод выделяет участок текста, начиная с позиции *start* до позиции *stop*.
Установить тип начертания шрифта можно с помощью метода *setTypeface* - например:

```
txtl.setTypeface(null, Typeface.NORМAL);
```

Вместо *NORМAL* можно указать *BOLD* и *ITALIC*. 
Для добавления поля *Edittext* в разметку окна нужно добавить следующий код в файл разметки:
```
<EditText
  android:id="@+id/entryl"
  android:layout_width="fill_parent"
  android:layout_height="wrap_content"
  android:text="Enter text"/>
```

Далее мы научимся работать с этим полем - займемся получением и установкой
текста поля. А пока ограничимся только тем, что добавим поле в файл разметки.
Как выглядит это поле, вы уже видели ранее. 

## 4.2.2 Кнопки

Кнопки - очень важные элементы пользовательского интерфейса, поскольку
к ним относятся не только непосредственно сами кнопки, но и переключатели
(независимые и зависимые). Кнопки определяются пятью классами: *Button*,
*CheckButton*, *ToggleButton*, *RadioButton*, *ImageButton*.

Классы *CheckButton*, *ToggleButton* и *RadioButton* являются потомками класса
*CompoudВutton*, который, в свою очередь, является потомком класса *Button*. А вот
класс *ImageButton* является потомком класса *ImageView*, поэтому *ImageButton* - это
больше изображение, нежели кнопка в прямом понимании этого слова.

### Button - обычная кнопка

Начнем с обычной кнопки и продемонстрируем создание относительно простого
приложения. Jаvа-кода в этом приложении будет чуть больше, чем обычно, поэтому читателям, 
не знакомым с Java, нужно быть внимательными, чтобы не допустить ошибку.

Добавьте в наш проект, демонстрирующий изменение значения текстового поля
с помощью Jаvа-кода (см. листинг 4.9), одну кнопку. Теперь изменение значения
текстового поля будет происходить не при запуске приложения, а при нажатии на
кнопку.

Графическая разметка проекта показана на рис. 4.18, а ХМL-разметка представлена
в листинге 4.1О. 

> Листинг 4.10 Разметка проекта с кнопкой
```
<?xml version="l.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
  android:orientation="vertical"
  android:layout_width="fill_parent"
  android:layout_height="fill_parent"
  >
<TextView
  android:id="@+id/txtl"
  android:layout_width="fill_parent"
  android:layout_height="wrap_content"
  android:textSize="20pt"
  android:textStyle="bold"
  android:text="Hello"
  />
<Button
  android:id="@+id/buttonl"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Button" />
</LinearLayout>
```

<p align="center"> Рис. 4.18. Разметка проекта с кнопкой</p>

Обратите внимание на идентификаторы текстового поля и кнопки. Текстовое поле
называется *txtl*, а кнопка - *buttonl*. Эти идентификаторы мы будем использовать
и в Jаvа-коде. Кстати, приятно, когда Android Studio пишет код за вас - автодополнение кода очень удачно реализовано в этой среде. Jаvа~код нашего приложения
приведен в листинге 4.11. 

> Листинг 4.11. Пример установки обработчика события кнопки
```
package corn.exarnple.ch04;

import android.support.v7.app.AppCornpatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedinstanceState){
    super.onCreate(savedinstanceState);
    setContentView(R.layout.activity_main);
    Button buttonl = (Button)findViewByid(R.id.buttonl);
    buttonl.setOnClickListener(new View.OnClickListener(){
    
      public void onClick(View v) {
        final TextView txtl;
        txtl = (TextView) findViewByid(R.id.txtl);
        txtl.setText("Hi!");
      }
    });
  }
}
```

Обработчик нажатия кнопки устанавливается методом *setOnClickListener*. Далее
с помощью *new View.OnClickListener()* создается код нового обработчика, который
просто указывается ниже. В самом обработчике мы находим текстовое поле *txtl* и
устанавливаем для него новый текст. При запуске приложения вы увидите надпись
**Hello** и кнопку. По нажатию кнопки текст надписи будет изменен на **Hi!**.

Думаю, как установить обработчик нажатия кнопки, понятно. Однако код получился весьма громоздкий. Скажем, когда у вас одна или две кнопки, то такой код -
это не проблема. А вот когда у вас 5 кнопок (или больше), то очень легко запутаться - уж очень много скобок. Хотя Android Studio делает все возможное, чтобы вам
помочь, есть один способ уменьшить код и существенно упростить его для восприятия.

Представим, что вы в файле разметки объявили пять кнопок с именами от *buttonl*
до *button5*. Сначала нужно найти кнопки: 

```
final Button buttonl = (Button)findViewByid(R.id.buttonl);
final Button button2 = (Button)findViewByid(R.id.button2);
...
final Button button5 = (Button)findViewByid(R.id.button5);
```

Потом устанавливаем один обработчик для всех кнопок: 

```
buttonl.setOnClickListener(this);
button2.setOnClickListener(this);
...
button5.setOnClickListener(this);
```

Затем анализируем, какая кнопка бьmа нажата, и выполняем соответствующее действие:

```
@Override
puЬlic void onClick(View v) {
  switch (v.getid()) {
    case R.id.buttonl: txtl.setText("Button1") ; break;
    case R.id.button2: txtl.setText("Button2") ; break;
    case R.id.buttonЗ: txtl.setText("Button3") ; break;
    case R.id.button4: txtl.setText("Button4") ; break;
    case R.id.button5: txtl.setText("Button5") ; break;
  }
}
```

Существует еще один, более компактный, способ установки обработчика нажатия
кнопки. В ХМL-разметке с помощью атрибута *onClick* нужно указать имя методаобработчика:

```
  <Button
    android:layout_width="wrap content"
    android:layout_height="wrap_content"
    android:text="Click"
    android:onClick="sendМessage" />
```

Далее в коде просто объявить метод *sendМessage()*:

```
puЬlic class MainActivity extends AppCompatActivity{

  @Override
  protected void onCreate(Bundle savedinstanceState) {
    super.onCreate(savedinstanceState);
    setContentView(R.layout.activity_main);
    }
    // Обработка нажатия кнопки
  puЬlic void sendМessage(View view){
    TextView textView = (TextView) findViewByid(R.id.textView);
    EditText editText = (EditText) findViewByid(R.id.editText);
    textView.setText("Hello, "+ editText.getText());
  }
}
```

Какой метод использовать - решать вам. На мой взгляд, последний способ более
компактный - если нужно установить код не для группы кнопок, а только для одной кнопки.
