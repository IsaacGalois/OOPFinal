import com.astontech.bo.Directory;
import com.astontech.bo.VFile;
import com.astontech.dao.mysql.DirectoryDAOImpl;
import com.astontech.dao.mysql.SimpleQuery;
import com.astontech.dao.mysql.VFileDAOImpl;

import java.io.File;
import java.util.Scanner;

public class Main {

//    static DirectoryDAOImpl dDAO = new DirectoryDAOImpl();
    static VFileDAOImpl fDAO = new VFileDAOImpl();
    static Scanner darkly = new Scanner(System.in);
    static DirectoryDAOImpl dDao = new DirectoryDAOImpl();

    public static void main(String[] args) {
        System.out.print("Enter a Directory to begin: ");
        String aPath = darkly.nextLine();
        Directory home = new Directory();

        File homeFile = new File(aPath);
        home.setPath(aPath);
        String[] split = null;
        try {
            split = homeFile.getCanonicalPath().split("/");
        } catch (Exception ex) {
            ex.toString();
        }
        home.setDirName(split[split.length-1]);
        home.setDirSize(folderSize(homeFile));
        home.setNumFiles(homeFile.listFiles().length);
        dDao.insertDirectory(home);

        RecurSearch(aPath);

        int choice = 0;

        while(choice != 6) {
            System.out.println("\nPlease choose an option below:");
            System.out.println("1. Display Directory with most files.");
            System.out.println("2. Display Directory largest in size.");
            System.out.println("3. Display 5 largest Files in size.");
            System.out.println("4. Display all files of a certain type.");
            System.out.println("5. Clear the Database and start over.");
            System.out.println("6. Exit\n");
            System.out.print("Enter choice here: ");
            String input = darkly.nextLine();

            choice = Integer.parseInt(input);

            while (choice < 1 || choice > 6) {
                System.out.println("Incorrect input. Please choose a value between 1 and 6");
                System.out.println("\nPlease choose an option below:");
                System.out.println("1. Display Directory with most files.");
                System.out.println("2. Display Directory largest in size.");
                System.out.println("3. Display 5 largest Files in size.");
                System.out.println("4. Display all files of a certain type.");
                System.out.println("5. Clear the Database and start over.");
                System.out.println("6. Exit\n");
                System.out.print("Enter choice here: ");
                input = darkly.nextLine();
                choice = Integer.parseInt(input);
            }

            SimpleQuery sq = new SimpleQuery();

            switch (choice) {
                case 1:
                    System.out.println("\nBelow are details of the directory with the most files.");
                    System.out.println("dirId   dirName   dirSize   numOfFiles   path");
                    System.out.println(sq.getMostFiles().toString());
                    break;

                case 2:
                    System.out.println("\nBelow are details of the directory that is the largest in size.");
                    System.out.println("dirId   dirName   dirSize   numOfFiles   path");
                    System.out.println(sq.getLargestInSize().toString());
                    break;

                case 3:
                    System.out.println("\nBelow are details of the 5 largest files");
                    System.out.println("fileId   fileName   fileType   fileSize   path   dirId");
                    for(VFile file : sq.get5LargestFiles())
                        System.out.println(file.toString());
                    break;

                case 4:
                    System.out.print("\nEnter the file type you want displayed: ");
                    input = darkly.nextLine();

                    System.out.println("\nBelow are details of the files of the type " + input);
                    System.out.println("fileId   fileName   fileType   fileSize   path   dirId");
                    for(VFile file : sq.getFilesByType(input))
                        System.out.println(file.toString());
                    break;

                case 5:
                    if (!sq.truncateTable())
                        System.out.println("Error deleting db");
                    System.out.println("Database deleted.");
                    System.out.print("Enter a Directory to begin: ");
                    aPath = darkly.nextLine();

                    dDao = new DirectoryDAOImpl();
                    home = new Directory();

                    home.setPath(aPath);
                    split = aPath.split("/");
                    home.setDirName(split[split.length-1]);
                    dDao.insertDirectory(home);

                    RecurSearch(aPath);
                    break;

                case 6:
                    System.out.println("Exiting Application.");
                    break;

                default:
                    System.out.println("An error occurred");
            }
        }
    }

    private static void RecurSearch(String dirPath) {

        File dir = new File(dirPath);


        try {
            File[] files = dir.listFiles();
            for(File file: files) {
                if(file.isDirectory()) {
                    System.out.println("directory: "+file.getCanonicalPath());
                    Directory aDir = new Directory();
                    aDir.setDirName(file.getName());
//                    aDir.setDirSize(((double)file.length())/1000000);

                    double totalSize = 0;
                    for (File thing:file.listFiles()) {
                        if(!thing.isDirectory())
                            totalSize = totalSize + ((double)thing.length());
                    }


                    aDir.setDirSize(folderSize(file));

                    aDir.setNumFiles(file.listFiles().length);
                    aDir.setPath(file.getCanonicalPath());

//                    System.out.println("DirName: "+file.getName());
                    System.out.println("DirSize: "+((double)file.length()));
                    System.out.println("DirSize2: "+totalSize);
                    System.out.println("folderSize: "+folderSize(file));
//                    System.out.println("NumFiles: "+file.listFiles().length);
//                    System.out.println("Path: "+file.getPath());

                    dDao.insertDirectory(aDir);

                    RecurSearch(file.getCanonicalPath());
                }
                else {
                    VFile aFile = new VFile();
                    String name = file.getName();

                    aFile.setFileName(name);
                    aFile.setFileType(name.substring(name.indexOf(".")+1));


                    aFile.setFileSize(((double)file.length()));
                    aFile.setPath(file.getPath());
                    SimpleQuery sq = new SimpleQuery();
                    aFile.setDirId(sq.getId(file.getParent()));

//                    System.out.println(name.substring(0,name.indexOf(".")));
//                    System.out.println(name.substring(name.indexOf(".")+1));
                    System.out.println("file: "+file.getCanonicalPath());
//                    System.out.println("Path: "+file.getPath());
                    System.out.println("Size: "+file.length());
//                    System.out.println("Parent: "+file.getParent());
//                    System.out.println("dirId of parent: "+sq.getId(file.getParent()));

                    fDAO.insertFile(aFile);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static double folderSize(File dir) {
        double length = 0.0;
        for (File file : dir.listFiles()) {
            if(file.isFile())
                length += (double)file.length();
            else
                length += folderSize(file);
        }
        return length;
    }
}


