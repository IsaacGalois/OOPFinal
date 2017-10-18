package com.astontech.dao.mysql;

import com.astontech.bo.Directory;
import com.astontech.bo.VFile;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SimpleQuery extends MySQL {

    public boolean truncateTable() {
        Connect();
        boolean didTrunc1 = false;
        boolean didTrunc2 = false;
        int result = 0;

        try {
            String sp = "DELETE FROM OOPFinal.File where OOPFinal.File.fileId >= 0;";

            CallableStatement cStmt = connection.prepareCall(sp);

            result = cStmt.executeUpdate();

            if(result != 0)
                didTrunc1 = true;

            sp = "DELETE FROM OOPFinal.Directory where OOPFinal.Directory.dirId >= 0;";

            cStmt = connection.prepareCall(sp);

            result = cStmt.executeUpdate();

            if(result != 0)
                didTrunc2 = true;


        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return didTrunc1 && didTrunc2;
    }

    public Directory getMostFiles() {
        Connect();
        Directory retD = new Directory();
        ResultSet rs = null;

        try {
            String sp = "SELECT distinct * FROM OOPFinal.Directory WHERE OOPFinal.Directory.numOfFiles in (Select MAX(OOPFinal.Directory.numOfFiles) from OOPFinal.Directory);";
            CallableStatement cStmt = connection.prepareCall(sp);

            rs = cStmt.executeQuery();
            rs.next();

            retD = DirectoryDAOImpl.HydrateObject(rs);

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return retD;
    }

    public Directory getLargestInSize() {
        Connect();
        Directory retD = new Directory();
        ResultSet rs = null;

        try {
            String sp = "SELECT distinct * FROM OOPFinal.Directory WHERE OOPFinal.Directory.dirSize in (Select MAX(OOPFinal.Directory.dirSize) from OOPFinal.Directory);";
            CallableStatement cStmt = connection.prepareCall(sp);

            rs = cStmt.executeQuery();
            rs.next();

            retD = DirectoryDAOImpl.HydrateObject(rs);

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return retD;
    }

    public ArrayList<VFile> get5LargestFiles() {
        Connect();
        ArrayList<VFile> fileList = new ArrayList<>();
        ResultSet rs = null;

        try {
            String sp = "SELECT * FROM OOPFinal.File ORDER BY OOPFinal.File.fileSize DESC LIMIT 5;";
            CallableStatement cStmt = connection.prepareCall(sp);

            rs = cStmt.executeQuery();

            while(rs.next()) {
                fileList.add(VFileDAOImpl.HydrateObject(rs));
            }

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return fileList;
    }

    public ArrayList<VFile> getFilesByType(String fileType) {
        Connect();
        ArrayList<VFile> fileList = new ArrayList<>();
        ResultSet rs = null;

        try {
            String sp = "SELECT * FROM OOPFinal.File WHERE OOPFinal.File.fileType = ?;";

            CallableStatement cStmt = connection.prepareCall(sp);
            cStmt.setString(1, fileType);
            rs = cStmt.executeQuery();

            while(rs.next()) {
                fileList.add(VFileDAOImpl.HydrateObject(rs));
            }

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return fileList;
    }



    public int getId(String path) {
        Connect();
        int result = 0;
        ResultSet rs;

        try {
            String sp = "SELECT OOPFinal.Directory.dirId FROM OOPFinal.Directory WHERE OOPFinal.Directory.path = ?;";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setString(1, path);
            rs = cStmt.executeQuery();

            if(rs.next())
                result = rs.getInt(1);

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return result;
    }


}
