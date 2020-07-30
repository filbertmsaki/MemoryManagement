package com.example.memorymanagement;

import android.widget.ProgressBar;

public class Memory {
    private String title;
    private String usedSpace;
    private String freeSpace;
    private String totalSpace;
    private int progressBar;
    private int image;

    public Memory(String title, String usedSpace, String freeSpace, String totalSpace, int progressBar) {
        this.title = title;
        this.usedSpace = usedSpace;
        this.freeSpace = freeSpace;
        this.totalSpace = totalSpace;
        this.progressBar = progressBar;
    }

    public int getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(int progressBar) {
        this.progressBar = progressBar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(String usedSpace) {
        this.usedSpace = usedSpace;
    }

    public String getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(String freeSpace) {
        this.freeSpace = freeSpace;
    }

    public String getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(String totalSpace) {
        this.totalSpace = totalSpace;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
