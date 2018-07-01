package com.lifan.rollcallassistant;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportExcel extends Activity {

//    DBController controller = new DBController(this);
//    Button btnimport;
//    ListView lv;
//    final Context context = this;
//    ListAdapter adapter;
//    ArrayList<HashMap<String, String>> myList;
//    public static final int requestcode = 1;

    EditText output;
    String classfilename = "classfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_excel);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(RollCall.EXTRA_MESSAGE);

        output = (EditText) findViewById(R.id.textOut);

    }

    public void onImportClick(View view) {
        printlnToUser("reading XLSX file from resources");
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



//                for (int c = 0; c<cellsCount; c++) {
//                    String value = getCellAsString(row, c, formulaEvaluator);
//                    String cellInfo = "r:"+r+"; c:"+c+"; v:"+value;
//                    printlnToUser(cellInfo);
//                }
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
//            File file = new File(getApplicationContext().getFilesDir(),classfilename);
            //writeObjectIntoLocal(getApplicationContext(),classfilename,class1);
            saveClass(class1);
//            ClassInfo TestReadClass = new ClassInfo();
//            TestReadClass = readClass();
//            Toast.makeText(getApplicationContext(), TestReadClass.Students[TestReadClass.getStudentNum()-2].getStuName(), Toast.LENGTH_SHORT).show();

        }catch (Exception e) {
            /* proper exception handling to be here */
            //printlnToUser(e.toString());
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


//    public boolean writeObjectIntoLocal(Context context, String filename, ClassInfo bean){
//        try{
//            FileOutputStream fos = getApplicationContext().openFileOutput(classfilename,Context.MODE_WORLD_READABLE);
//            //Toast.makeText(getApplicationContext(), "Create fos successfully", Toast.LENGTH_SHORT).show();
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            //Toast.makeText(getApplicationContext(), "Create oos successfully", Toast.LENGTH_SHORT).show();
//            oos.writeObject(bean);
//            //Toast.makeText(getApplicationContext(), "Write file successfully", Toast.LENGTH_SHORT).show();
//            fos.close();
//            oos.close();
//            Toast.makeText(getApplicationContext(), "Create file successfully", Toast.LENGTH_SHORT).show();
//            return true;
//        }catch (FileNotFoundException e) {
//            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
//            return false;
//        }catch (IOException e) {
//            Toast.makeText(getApplicationContext(), "IO error", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//    }

    protected String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("dd/MM/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {
            /* proper error handling should be here */
            printlnToUser(e.toString());
        }
        return value;
    }

    /**
     * print line to the output TextView
     * @param str
     */
    private void printlnToUser(String str) {
        final String string = str;
        if (output.length()>8000) {
            CharSequence fullOutput = output.getText();
            fullOutput = fullOutput.subSequence(5000,fullOutput.length());
            output.setText(fullOutput);
            output.setSelection(fullOutput.length());
        }
        output.append(string+"\n");
    }


//        btnimport = (Button) findViewById(R.id.buttonImport);
//        lv = getListView();
//        btnimport.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
//                fileintent.setType("gagt/sdf");
//                try {
//                    startActivityForResult(fileintent, requestcode);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(getApplicationContext(), "无法导入文件", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
//
//        myList= controller.getAllStudents();
//        if (myList.size() != 0) {
//            ListView lv = getListView();
//            ListAdapter adapter = new SimpleAdapter(ImportExcel.this, myList,
//                    R.layout.fullfil_list, new String[]{"Stu_ID", "Stu_Name", "Stu_Absence", "Stu_Answer", "Stu_Average"}, new int[]{
//                    R.id.text_ID, R.id.text_name, R.id.text_absence, R.id.text_answer, R.id.text_average});
//            setListAdapter(adapter);
//        }
//
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (data == null)
//            return;
//
//
//        switch (requestCode) {
//            case requestcode:
//                String filepath = data.getData().getPath();
//                controller = new DBController(getApplicationContext());
//                SQLiteDatabase db = controller.getWritableDatabase();
//                String tableName = "proinfo";
//                db.execSQL("delete from " + tableName);
//
//                try {
//                    if (resultCode == RESULT_OK) {
//                        try {
//
//                            //FileReader file = new FileReader(filepath);
//                            FileInputStream file = new FileInputStream(filepath);
//                            BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
//                            Toast.makeText(getApplicationContext(), filepath, Toast.LENGTH_SHORT).show();
//                            ContentValues contentValues = new ContentValues();
//                            String line = "";
//
//                            db.beginTransaction();
//
//                            while ((line = buffer.readLine()) != null) {
//
//                                String[] str = line.split(",", 5);  // defining 5 columns with null or blank field //values acceptance
//                                //Id, Company,Name,Price
//                                String Stu_ID = str[0].toString();
//                                String Stu_Name = str[1].toString();
//                                String Stu_Absence = str[2].toString();
//                                String Stu_Answer = str[3].toString();
//                                String Stu_Average = str[4].toString();
//
//                                contentValues.put("Company", Stu_ID);
//                                contentValues.put("Name", Stu_Name);
//                                contentValues.put("Price", Stu_Absence);
//                                contentValues.put("Price", Stu_Answer);
//                                contentValues.put("Price", Stu_Average);
//                                db.insert(tableName, null, contentValues);
//                                Toast.makeText(getApplicationContext(), "成功更新数据库", Toast.LENGTH_SHORT).show();
//                            }
//                            db.setTransactionSuccessful();
//                            db.endTransaction();
//                        } catch (IOException e) {
//                            if (db.inTransaction())
//                                db.endTransaction();
//                            Dialog d = new Dialog(this);
//                            d.setTitle(e.getMessage().toString() + "first");
//                            d.show();
//                            // db.endTransaction();
//                        }
//                    } else {
//                        if (db.inTransaction())
//                            db.endTransaction();
//                        Dialog d = new Dialog(this);
//                        d.setTitle("Only CSV files allowed");
//                        d.show();
//                    }
//                } catch (Exception ex) {
//                    if (db.inTransaction())
//                        db.endTransaction();
//
//                    Dialog d = new Dialog(this);
//                    d.setTitle(ex.getMessage().toString() + "second");
//                    d.show();
//                    // db.endTransaction();
//                }
//        }
//        myList= controller.getAllStudents();
//
//        if (myList.size() != 0) {
//            ListView lv = getListView();
//            ListAdapter adapter = new SimpleAdapter(ImportExcel.this, myList,
//                    R.layout.fullfil_list, new String[]{"Stu_ID", "Stu_Name", "Stu_Absence", "Stu_Answer", "Stu_Average"}, new int[]{
//                    R.id.text_ID, R.id.text_name, R.id.text_absence, R.id.text_answer, R.id.text_average});
//            setListAdapter(adapter);
//            Toast.makeText(getApplicationContext(), "数据导入成功", Toast.LENGTH_SHORT).show();
//        }
//    }
}
