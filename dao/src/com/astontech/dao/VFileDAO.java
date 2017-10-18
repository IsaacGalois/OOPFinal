package com.astontech.dao;

import com.astontech.bo.VFile;
import java.util.List;

public interface VFileDAO {

    //notes: GET methods
    public VFile getFileById(int dirId);
    public List<VFile> getFileList();

    //notes: EXECUTE methods
    public int insertFile(VFile dir);
    public boolean updateFile(VFile dir);
    public boolean deleteFile(int dirId);
    
}
