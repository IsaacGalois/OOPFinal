package com.astontech.dao;

import com.astontech.bo.Directory;
import java.util.List;

public interface DirectoryDAO {

    //notes: GET methods
    public Directory getDirectoryById(int dirId);
    public List<Directory> getDirectoryList();

    //notes: EXECUTE methods
    public int insertDirectory(Directory dir);
    public boolean updateDirectory(Directory dir);
    public boolean deleteDirectory(int dirId);

}
