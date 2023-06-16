package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WaveFileAnalyser {
    private BufferedReader reader;
    private ArrayList<Integer> spawnList,delayList;

    public WaveFileAnalyser() {
        spawnList = new ArrayList<Integer>();
        delayList = new ArrayList<Integer>();
        initWaveFileAnalyser();
    }

    public void initWaveFileAnalyser() {
        try {
            reader = new BufferedReader(new FileReader(variables.Maps.getMapWaveFile(Values.LEVEL)));
        } catch (IOException e) {
            System.out.println("WaveFileAnalyser cant read " + Values.LEVEL);
        }
    }

    public int getWavesNum() {
        int lineCount = 0;
        try {
            while (reader.readLine() != null) {
                lineCount++;
            }
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lineCount;
    }


    public void initArrayLists(int lineNum) {
        initWaveFileAnalyser();
        spawnList.clear();
        delayList.clear();
        try {
            int currentLine = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (currentLine == lineNum) {
                    String[] lineArray = line.split(",");
                    for (String s : lineArray) {
                        String[] lineArray2 = s.split("(?<=w)");
//                        System.out.println("yds" + lineArray2);
                        for (String s2 : lineArray2) {
                            if(s2.contains("w")) {
                                String s2withoutCommas = s2.replace("w", "");
                                spawnList.add(Integer.parseInt(s2withoutCommas));
                            }else if (!s2.trim().isEmpty()) {
                                delayList.add(Integer.parseInt(s2));
                            }
                        }
                    }
                    break; // Found the desired line, no need to continue reading
                }
                currentLine++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Integer> getSpawnList() {
//        return spawnList;
        return new ArrayList<Integer>(spawnList);
    }
    public ArrayList<Integer> getDelayList() {
//        return delayList;
        return new ArrayList<Integer>(delayList);
    }
}