package com.astontech.dao.mysql;

import com.astontech.bo.VFile;
import com.astontech.dao.VFileDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VFileDAOImpl extends MySQL implements VFileDAO {
    @Override
    public VFile getFileById(int fileId) {
        Connect();
        VFile VFile = null; //not instantiated because if no records are returned we don't want to allocate memory

        try {
            String sp = "{call usp_GetFile(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_BY_ID);
            cStmt.setInt(2, fileId);
            ResultSet rs = cStmt.executeQuery();

            if(rs.next()) {
                VFile = HydrateObject(rs);
            }

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return VFile;
    }

    @Override
    public List<VFile> getFileList() {
        Connect();
        List<VFile> vFileList = new ArrayList<>(); //not instantiated because if no records are returned we don't want to allocate memory

        try {
            String sp = "{call usp_GetFile(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_COLLECTION);
            cStmt.setInt(2, 0);
            ResultSet rs = cStmt.executeQuery();

            while(rs.next()) {
                vFileList.add(HydrateObject(rs));
            }


        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return vFileList;
    }

    @Override
    public int insertFile(VFile file) {
        Connect();
        int id = 0;

        try {                    //(int fileId, String fileName, String fileType, double fileSize, String path, int dirId)
            String sp = "{call usp_ExecuteFile(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, INSERT);
            cStmt.setInt(2, 0);
            cStmt.setString(3, file.getFileName());
            cStmt.setString(4, file.getFileType());
            cStmt.setDouble(5, file.getFileSize());
            cStmt.setString(6, file.getPath());
            cStmt.setInt(7, file.getDirId());
            ResultSet rs = cStmt.executeQuery();

            if(rs.next())
                id = rs.getInt(1);
            else
                id = -1;


        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return id;
    }

    @Override
    public boolean updateFile(VFile file) {
        Connect();
        boolean didUpdate = false;

        try {                    //(int fileId, String fileName, String fileType, double fileSize, String path, int dirId)
            String sp = "{call usp_ExecuteFile(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, INSERT);
            cStmt.setInt(2, file.getFileId());
            cStmt.setString(3, file.getFileName());
            cStmt.setString(4, file.getFileType());
            cStmt.setDouble(5, file.getFileSize());
            cStmt.setString(6, file.getPath());
            cStmt.setInt(7, file.getDirId());
            ResultSet rs = cStmt.executeQuery();

            if(rs.next())
                didUpdate = true;

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return didUpdate;
    }

    @Override
    public boolean deleteFile(int fileId) {
        Connect();
        boolean didDelete = false;

        try {                    //(int fileId, String fileName, String fileType, double fileSize, String path, int dirId)
            String sp = "{call usp_ExecuteFile(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, INSERT);
            cStmt.setInt(2, fileId);
            cStmt.setString(3, "");
            cStmt.setString(4, "");
            cStmt.setDouble(5, 0);
            cStmt.setString(6, "");
            cStmt.setInt(7, 0);
            ResultSet rs = cStmt.executeQuery();

            if(rs.next() && rs.getInt(1) > 0)
                didDelete = true;

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return didDelete;
    }

    public static VFile HydrateObject(ResultSet rs) throws SQLException{
        /*  private int fileId;
            private String fileName;
            private String fileType;
            private double fileSize;
            private String path;
            private int dirId;
        */

        //notes:    HYDRATING AN OBJECT
        VFile VFile = new VFile();
        VFile.setFileId(rs.getInt(1));
        VFile.setFileName(rs.getString(2));
        VFile.setFileType(rs.getString(3));
        VFile.setFileSize(rs.getDouble(4));
        VFile.setPath(rs.getString(5));
        VFile.setDirId(rs.getInt(6));

        return VFile;
    }
}
