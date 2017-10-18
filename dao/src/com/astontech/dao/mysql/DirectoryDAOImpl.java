package com.astontech.dao.mysql;

import com.astontech.bo.Directory;
import com.astontech.dao.DirectoryDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDAOImpl extends MySQL implements DirectoryDAO {
    @Override
    public Directory getDirectoryById(int dirId) {
        Connect();
        Directory directory = null; //not instantiated because if no records are returned we don't want to allocate memory

        try {
            String sp = "{call usp_GetDirectory(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_BY_ID);
            cStmt.setInt(2, dirId);
            ResultSet rs = cStmt.executeQuery();

            if(rs.next()) {
                directory = HydrateObject(rs);
            }

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return directory;
    }

    @Override
    public List<Directory> getDirectoryList() {
        Connect();
        List<Directory> DirectoryList = new ArrayList<>(); //not instantiated because if no records are returned we don't want to allocate memory

        try {
            String sp = "{call usp_GetDirectory(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_COLLECTION);
            cStmt.setInt(2, 0);
            ResultSet rs = cStmt.executeQuery();

            while(rs.next()) {
                DirectoryList.add(HydrateObject(rs));
            }


        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return DirectoryList;
    }

    @Override
    public int insertDirectory(Directory dir) {
        Connect();
        int id = 0;

        try {                    //(int dirId, String dirName, int dirSize, int numFiles, String path)
            String sp = "{call usp_ExecuteDirectory(?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, INSERT);
            cStmt.setInt(2, 0);
            cStmt.setString(3, dir.getDirName());
            cStmt.setDouble(4, dir.getDirSize());
            cStmt.setInt(5, dir.getNumFiles());
            cStmt.setString(6, dir.getPath());
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
    public boolean updateDirectory(Directory dir) {
        Connect();
        boolean didUpdate = false;

        try {                    //(int dirId, String dirName, int dirSize, int numFiles, String path)
            String sp = "{call usp_ExecuteDirectory(?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, UPDATE);
            cStmt.setInt(2, dir.getDirId());
            cStmt.setString(3, dir.getDirName());
            cStmt.setDouble(4, dir.getDirSize());
            cStmt.setInt(5, dir.getNumFiles());
            cStmt.setString(6, dir.getPath());
            ResultSet rs = cStmt.executeQuery();

            if(rs.next())
                didUpdate = true;

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return didUpdate;
    }

    @Override
    public boolean deleteDirectory(int dirId) {
        Connect();
        boolean didDelete = false;

        try {                    //(int dirId, String dirName, int dirSize, int numFiles, String path)
            String sp = "{call usp_ExecuteDirectory(?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, DELETE);
            cStmt.setInt(2, dirId);
            cStmt.setString(3, "");
            cStmt.setDouble(4, 0);
            cStmt.setInt(5, 0);
            cStmt.setString(6, "");
            ResultSet rs = cStmt.executeQuery();

            if(rs.next() && rs.getInt(1) > 0)
                didDelete = true;

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return didDelete;
    }

    public static Directory HydrateObject(ResultSet rs) throws SQLException{
        /*  private int dirId;
            private String dirName;
            private int dirSize;
            private int numFiles;
            private String path;
        */

        //notes:    HYDRATING AN OBJECT
        Directory directory = new Directory();
        directory.setDirId(rs.getInt(1));
        directory.setDirName(rs.getString(2));
        directory.setDirSize(rs.getInt(3));
        directory.setNumFiles(rs.getInt(4));
        directory.setPath(rs.getString(5));

        return directory;
    }
}
