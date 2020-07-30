package com.example.memorymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private String TAG =getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView memoryList = findViewById(R.id.list_of_memory);
        List<Memory> memoryDataSource = new ArrayList<>();

        String heading = "RAM Information";
        long totalRamValue = totalRAMMemorySize();
        long freeRamValue = freeRAMMemorySize();
        long usedRamValue = totalRamValue - freeRamValue;
        double d =  (double)freeRamValue/ (double)totalRamValue;
        int processBar = (int) (d*100);
        int progress = processBar;

        Memory mMemory = new Memory(heading, formatSize(usedRamValue) , formatSize(freeRamValue) , formatSize(totalRamValue) , progress);
        memoryDataSource.add(mMemory);
        String internalMemoryTitle = "Internal Memory Information";
        long totalInternalValue = totalInternalMemorySize();
        long freeInternalValue = AvailableInternalMemorySize();
        long usedInternalValue = totalInternalValue - freeInternalValue;

        double dInternal =  (double)freeInternalValue/ (double)totalInternalValue;
        int processBarInternal = (int) (dInternal*100);
        int progressInt = processBarInternal;

        Memory internalMemory = new Memory(internalMemoryTitle, formatSize(usedInternalValue), formatSize(freeInternalValue), formatSize(totalInternalValue), progressInt);
        memoryDataSource.add(internalMemory);

            if (externalMemoryAvailable()){
                String externalMemoryTitle = "External Memory Information";
                long totalExternalValue = totalExternalMemorySize();
                long freeExternalValue = AvailableExternalMemorySize();
                long usedExternalValue = totalExternalValue - freeExternalValue;
                double dExt =  (double)freeExternalValue/ (double)totalExternalValue;
                int processBarExt = (int) (dExt*100);
                int progressExt = processBarExt;

                Memory externalMemory = new Memory(externalMemoryTitle, formatSize(usedExternalValue), formatSize(freeExternalValue), formatSize(totalExternalValue), progressExt);
                memoryDataSource.add(externalMemory);
            }
            else {
                String externalMemoryTitle = "External Memory Information";
                long totalExternalValue = 0;
                long freeExternalValue = 0;
                long usedExternalValue = 0;
                double dExt =  (double)freeExternalValue/ (double)totalExternalValue;
                int processBarExt = (int) (dExt*100);
                int progressExt = processBarExt;

                Memory externalMemory = new Memory(externalMemoryTitle, formatSize(usedExternalValue), formatSize(freeExternalValue), formatSize(totalExternalValue), progressExt);
                memoryDataSource.add(externalMemory);
            }


        MemoryAdapter memoryAdapter = new MemoryAdapter(MainActivity.this, memoryDataSource);
        memoryList.setAdapter(memoryAdapter);

    }
    private long freeRAMMemorySize(){

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        long availableMemory = memoryInfo.availMem;
        return  availableMemory;
    }

    private long totalRAMMemorySize(){

        Context context = getApplicationContext();
        ActivityManager activityManager =(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalMemory = memoryInfo.totalMem;
        return totalMemory;

    }
    public static long AvailableInternalMemorySize(){
        File path = Environment.getDataDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long blockSize = statFs.getBlockSize();
        long availableBlock = statFs.getAvailableBlocks();
        return availableBlock * blockSize;
    }
    public static long totalInternalMemorySize(){
        File path = Environment.getDataDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long blockSize = statFs.getBlockSize();
        long availableBlock = statFs.getBlockCount();
        return availableBlock * blockSize;
    }
    public static boolean externalMemoryAvailable(){
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();

        return isSDPresent && isSDSupportedDevice;
    }
    public static long AvailableExternalMemorySize(){
        if (externalMemoryAvailable()){
            File path = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(path.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlock = statFs.getAvailableBlocks();
            return availableBlock * blockSize;
        }else {
            return 0;
        }
    }
    public static long totalExternalMemorySize(){
        if (externalMemoryAvailable()){
            File path = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(path.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlock = statFs.getBlockCount();
            return availableBlock * blockSize;
        }else {
            return 0;
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = " KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = " MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }



}