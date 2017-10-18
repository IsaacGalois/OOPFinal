package com.astontech.bo;

public class Directory extends BaseBO {

    private int dirId;
    private String dirName;
    private double dirSize;
    private int numFiles;
    private String path;


    public int getDirId() {
        return dirId;
    }

    public void setDirId(int dirId) {
        this.dirId = dirId;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public double getDirSize() {
        return dirSize;
    }

    public void setDirSize(double dirSize) {
        this.dirSize = dirSize;
    }

    public int getNumFiles() {
        return numFiles;
    }

    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        return new String(this.getDirId() + " " + this.getDirName() + " " + this.getDirSize() + " "+ this.getNumFiles() + " " + this.getPath());
    }
}
