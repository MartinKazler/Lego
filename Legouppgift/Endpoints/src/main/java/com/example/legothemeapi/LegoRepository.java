package com.example.legothemeapi;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

@Repository
public class LegoRepository {

    public HashMap<Integer, Lego> legoList;

    public LegoRepository() throws FileNotFoundException {
        this.legoList = new HashMap<>();
        readLegoFileInfo();
    }

    public Collection<Lego> getAll() {
        return legoList.values();
    }

    public void readLegoFileInfo() throws FileNotFoundException {
        File file = new File("themes.csv");
        Scanner fileRead = new Scanner(file);

       // Skip first row
        String textLine = fileRead.nextLine();
        while (fileRead.hasNextLine()) {
            textLine = fileRead.nextLine();
            String[] row = textLine.split(",");
            Lego lego = new Lego();
            lego.set_id(Integer.parseInt(row[0]));
            lego.setName(row[1]);

            // Check if parentId exists, if not - set 0
            lego.setParentId((row.length == 3) ? Integer.parseInt(row[2]) : 0);
            legoList.put(lego.get_id(), lego);
        }
        fileRead.close();
    }
}