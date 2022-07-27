package com.example.android.filesystemreadandwriteinternalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final String IMAGE_NAME = "azeemunarimage";
    public static final String FILE_NAME = "mytxtfile";
    private static final String DIR_NAME = "my_directory";

    EditText mFileContent;
    TextView mOutputText;
    ImageView mImageView;
    Button mBtnCreateFile,mBtnReadFile,mBtnFileList,mBtnDeleteFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        String path = getFilesDir().getAbsolutePath();
        mOutputText.setText(path+"\n");
        mBtnCreateFile.setOnClickListener(this::createFile);
        mBtnReadFile.setOnClickListener(this::readFile);
        mBtnFileList.setOnClickListener(this::showFileList);
        mBtnDeleteFile.setOnClickListener(this::deleteFiles);

    }


    public void readCustomDirFile(View view) {
        File path = getDir(DIR_NAME,MODE_PRIVATE);
        File file = new File(path,"abc.txt");
        if (file.exists()){
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream fileInputStream = null;

            try {
                fileInputStream = new FileInputStream(file);
                int read;
                while((read = fileInputStream.read()) != -1){
                    stringBuilder.append((char) read);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mOutputText.setText(stringBuilder.toString());
        }
        else {
            mOutputText.setText("no file");
        }
    }

    public void createDirectory(View view) {
        File path = getDir(DIR_NAME,MODE_PRIVATE);
        File file = new File(path,"abc.txt");
        String data = "this is my data in new directory";

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("SetTextI18n")
    private void createFile(View view) {
        writeTextData();
        writeImageData();
    }

    private void readFile(View view) {
        readTextFile();
        readImageFile();
    }

    private void showFileList(View view) {
        String[] fileList = fileList();
        for (String fileName:fileList) {
            mOutputText.append(fileName+"\n");
        }
    }

    private void deleteFiles(View view) {

        String[] fileList = fileList();
        for (String fileName:fileList) {
            boolean b = deleteFile(fileName);
            if (b){
                mOutputText.setText("All files deleted");
                mImageView.setImageResource(R.drawable.ic_launcher_background);
            }else {
                mOutputText.setText("Empty files");
            }
        }
    }

    private void writeTextData() {
        String data = mFileContent.getText().toString();
        FileOutputStream outputStream = null;
        try {

            outputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.flush();
            mOutputText.setText("File Written\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeImageData() {
        Bitmap data = getImage();
        @SuppressLint("UseCompatLoadingForDrawables")
        // BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable(R.drawable.azeemunar);

        FileOutputStream outputStream = null;

        try {
            outputStream = openFileOutput(IMAGE_NAME+".jpg", MODE_PRIVATE);
            data.compress(Bitmap.CompressFormat.JPEG,70,outputStream);
            mOutputText.append("Image Written");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (outputStream != null){
                try {
                    outputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void readImageFile() {
        Bitmap bitmap = null;
        InputStream inputStream = null;

        try {
            inputStream = openFileInput(IMAGE_NAME+".jpg");
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mImageView.setImageBitmap(bitmap);
    }

    private void readTextFile() {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream =  null;

        try {
            inputStream = openFileInput(FILE_NAME);
            int read;
            while((read = inputStream.read()) != -1){
                stringBuilder.append((char) read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mOutputText.setText(stringBuilder.toString());
    }






    private Bitmap getImage(){
        Bitmap image = null;

        try {
            InputStream inputStream = getAssets().open("azeemunar.jpg");
            image = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void initViews() {
        mFileContent = findViewById(R.id.et);
        mOutputText = findViewById(R.id.textView);
        mBtnCreateFile = findViewById(R.id.btnCreateFile);
        mBtnReadFile = findViewById(R.id.btnReadFile);
        mBtnFileList = findViewById(R.id.btnFileList);
        mBtnDeleteFile = findViewById(R.id.btnDeleteFile);
        mImageView = findViewById(R.id.imageView);
    }


}