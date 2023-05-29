package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WaveFileAnalyser {
    private BufferedReader reader;
    private int currentMap;
    public WaveFileAnalyser(int currentMap) {
        this.currentMap = currentMap;
        initWaveFileAnalyser(currentMap);
    }

    public void initWaveFileAnalyser(int currentMap) {
        try {
            reader = new BufferedReader(new FileReader(variables.Maps.getMapWaveFile(currentMap)));
        } catch (IOException e) {
            e.printStackTrace();
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

    public ArrayList<Integer> getArrayList(int lineNum) {
        ArrayList<Integer> spawnList = new ArrayList<Integer>();
        try {
            initWaveFileAnalyser(currentMap);
            int currentLine = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (currentLine == lineNum) {
                    String[] lineArray = line.split(",");
                    for (String s : lineArray) {
                        spawnList.add(Integer.parseInt(s));
                    }
                    break; // Found the desired line, no need to continue reading
                }
                currentLine++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(spawnList);
        return spawnList;
    }
}