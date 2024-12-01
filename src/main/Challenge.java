package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Year;
import java.util.Scanner;

public abstract class Challenge {
    
    public abstract long solve();
    
    public Scanner getInputFile() {
        String index = this.getClass().getSimpleName().substring(3, 5);
        if (index.charAt(0) == '0') {
            index = index.substring(1);
        }
        try {
            return new Scanner(new File("InputFiles/" + Year.now().getValue() + "/" + index + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
