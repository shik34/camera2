package com.example.camera2;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {
    //Переменная для установления связи между приложением и камерой
    private int CAMERA_CAPTURE;
    //Дает возможность обратиться к захваченному камерой изображению
    private Uri pUri;
    //Будет использоваться при захвате изображения
    final int PIC_CROP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Объявляем использование кнопки, привязываем к нашей кнопке:
        Button button=(Button)findViewById(R.id.bs);
    }

    //Описываем нажатие клавиши "Запустить камеру"
    public void Photo(View view) {
        try {
            //Используем стандартное системное намерение на использование камеры:
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Задаем возможность работать с полученными с камеры данными:
            startActivityForResult(captureIntent, CAMERA_CAPTURE);
        }
        catch(ActivityNotFoundException cant){
            //Показываем сообщение об ошибке:
            String errorMessage = "Ваше устройство не поддерживает работу с камерой!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    //Добавляем метод обработки данных с камеры (то есть изображения)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE){
            pUri=data.getData();
            cropImage();
        }

        else if (requestCode == PIC_CROP) {
            //Получаем данные
            Bundle extras = data.getExtras();
            //Получаем изображение
            Bitmap thePic = extras.getParcelable("data");
            //Создаем ссылку на наш элемент ImageView в activity_main.xml:
            ImageView picView = (ImageView)findViewById(R.id.pic);
            //Отображаем в нем полученное с камеры изображение:
            picView.setImageBitmap(thePic);

        }
    }

    private void cropImage() {
        try {
        }
        catch(ActivityNotFoundException cant){
            //Показываем сообщение об ошибке:
            String errorMessage = "Ваше устройство не поддерживает работу с камерой!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
        //Вызываем стандартное действие захвата изображения:
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //Указываем тип изображения и Uri
        cropIntent.setDataAndType(pUri, "image/*");
        //Настраиваем характеристики захвата:
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //Указываем размер в X и Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //Извлекаем данные:
        cropIntent.putExtra("return-data", true);
        //Запускаем Activity и возвращаемся в метод onActivityResult
        startActivityForResult(cropIntent, PIC_CROP);

    }
}