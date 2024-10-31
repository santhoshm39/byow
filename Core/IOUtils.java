package byow.Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class IOUtils {

    public IOUtils() {

    }

    public void writeContents(String result, File file) {
        file.delete();
        try {
            FileWriter writer = new FileWriter(file.getName());
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readContents(File file) {
        String data = "";
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void saveGame(String moveSoFar, int gameSeed) {
        File saveFile = Paths.get("savefile.txt").toFile();
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result = moveSoFar + "," + gameSeed;
        writeContents(result, saveFile);
    }

    public String[] loadGame() {
        Path proj3Dir = Paths.get("savefile.txt");
        if (!Files.exists(proj3Dir)) {
            return null;
        }
        String data = readContents(proj3Dir.toFile());
        String[] dataArray = data.split(",");
        return dataArray;
    }
}
