package hoursorg.hourssheetv2;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileOperations {//Class for all on-file operations and related methods. To be instanciated in initialise method of cotnrollers
    private String filename;

    public void setFilename(String filename){this.filename = filename;}
    public String getFilename(){return this.filename;}

    public String getCurrentMonthFile(){
        LocalDate date = LocalDate.now();
        String monthFile = "";
        monthFile = date.getYear() + "-";
        int monthLength = Integer.toString(date.getMonthValue()).length();
        monthFile = (monthLength == 1) ? monthFile + "0" + date.getMonthValue() : monthFile + date.getMonthValue();
        monthFile += ".txt";
        return monthFile;
    }

    public Boolean writeToFile(String content, String filename){
        this.setFilename(filename);
        Path dataDirectory = Paths.get("src/main/Data/" + this.filename);
        try{
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(dataDirectory, CREATE, APPEND));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            writer.write(content);
            writer.newLine();
            writer.flush();
            writer.close();
            //add more fine-grained exception handling
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public List<String> getFileNamesInDataDirectory() {
        List<String> list = new ArrayList<>();
        String resultMessage = "";
        //add the default value to the list as the first item
        list.add(getCurrentMonthFile());
        //get the names of items from the directory "Data"
        try {
            Path dataDirectoryPath = Paths.get("src/main/Data");
            for (Path filePath : Files.newDirectoryStream(dataDirectoryPath)){
                if(!filePath.getFileName().toString().equals(getCurrentMonthFile())){
                    list.add(filePath.getFileName().toString());
                }
            }
        }catch (Exception e){
            list.set(0,"");
        }
        return list;
    }
}
