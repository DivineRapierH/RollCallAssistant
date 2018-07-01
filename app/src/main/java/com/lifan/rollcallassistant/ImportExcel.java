package com.lifan.rollcallassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportExcel extends Activity {

    EditText output;
    String classfilename = "classfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_excel);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(RollCall.EXTRA_MESSAGE);

    }

    public void onImportClick(View view) {
        InputStream stream = getResources().openRawResource(R.raw.student_info);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

            ClassInfo class1 = new ClassInfo(rowsCount);

            for (int r = 0; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = 5;
                Cell[] cells = new Cell[5];

                for(int i=0; i<5; i++)
                    cells[i]=row.getCell(i);
                //读取并导入该行的所有数据
                class1.Students[r] = new StudentDetails();
                CellValue cellId = formulaEvaluator.evaluate(cells[0]);
                class1.Students[r].setStuId(cellId.getStringValue());
                CellValue cellName = formulaEvaluator.evaluate(cells[1]);
                class1.Students[r].setStuName(cellName.getStringValue());
                CellValue cellAbsence = formulaEvaluator.evaluate(cells[2]);
                class1.Students[r].setStuAbsence((int)cellAbsence.getNumberValue());
                CellValue cellAnswer = formulaEvaluator.evaluate(cells[3]);
                class1.Students[r].setStuAnswer((int)cellAnswer.getNumberValue());
                CellValue cellAverage = formulaEvaluator.evaluate(cells[4]);
                class1.Students[r].setStuAverage(cellAverage.getNumberValue());
            }

            Toast.makeText(getApplicationContext(), "Import Info successfully", Toast.LENGTH_SHORT).show();
            //用文件流保存到本地，从而实现Activity之间的通信
            saveClass(class1);

        }catch (Exception e) {
            /* proper exception handling to be here */
            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
        }

    }

    public void saveClass(ClassInfo class_1){
        SharedPreferences preferences = getSharedPreferences("base64",MODE_PRIVATE);
        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            //创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            //将对象写入字节流
            oos.writeObject(class_1);
            //将字节流编码成base64的字符串
            String class_1_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("class_1",class_1_Base64);
            editor.commit();
        }catch (IOException e){
            Toast.makeText(getApplicationContext(), "IO Failure", Toast.LENGTH_SHORT).show();
        }
        Log.i("OK","Successfully saved");
    }

    public ClassInfo readClass(){
        ClassInfo class_1 = new ClassInfo();
        SharedPreferences preferences = getSharedPreferences("base64",MODE_PRIVATE);
        String classBase64 = preferences.getString("class_1","");

        //读取字节
        byte[] base64 = Base64.decodeBase64(classBase64.getBytes());
        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try{
            //再次封装
            ObjectInputStream ois = new ObjectInputStream(bais);
            try{
                //读取对象
                class_1 = (ClassInfo) ois.readObject();
            }catch (ClassNotFoundException e){
                Toast.makeText(getApplicationContext(), "ClassNotFoundException", Toast.LENGTH_SHORT).show();
            }
        }catch (StreamCorruptedException e){
            Toast.makeText(getApplicationContext(), "StreamCorruptedException", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_SHORT).show();
        }
        return class_1;
    }

}
