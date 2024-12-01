package main;

import java.io.File;
import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Run {

    public static void main(String[] args) {
        String packageName = "Dec" + Year.now().getValue();
        String className = challengeToSolve(packageName);

        try {
            Class<?> clazz = Class.forName(packageName + "." + className);
            Challenge challenge = (Challenge) clazz.getDeclaredConstructor().newInstance();
            System.out.println(challenge.solve());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String challengeToSolve(String packageName) {
        File directory = new File("src/" + packageName);
        if (!directory.exists() || !directory.isDirectory()) {
            return null;
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".java"));
        if (files == null || files.length == 0) {
            return null;
        }

        int maxDay = -1;
        int maxPart = -1;
        Pattern pattern = Pattern.compile("Day(\\d+)Part(\\d+)\\.java");
        for (File file : files) {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                int day = Integer.parseInt(matcher.group(1));
                int part = Integer.parseInt(matcher.group(2));

                if (day > maxDay || (day == maxDay && part > maxPart)) {
                    maxDay = day;
                    maxPart = part;
                }
            }
        }

        return "Day" + String.format("%02d", maxDay) + "Part" + maxPart;
    } 
    
}
