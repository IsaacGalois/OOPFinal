package com.astontech.bo;

public class VFile extends BaseBO {

    private int fileId;
    private String fileName;
    private String fileType;
    private double fileSize;
    private String path;
    private int dirId;


    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDirId() {
        return dirId;
    }

    public void setDirId(int dirId) {
        this.dirId = dirId;
    }

    public String toString() {
        return new String(this.getFileId()+" "+this.getFileName()+" "+this.getFileType()+" "+this.getFileSize()+" "+this.getPath()+" "+this.getDirId());
    }
}