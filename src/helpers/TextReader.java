package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextReader {

    private File file;
    private BufferedReader reader;
    private ArrayList<Integer> spawnList, delayList;
    private Values playerValues;

    // Constructor using File object
    public TextReader(File file) {
        this.file = file;
    }

    // Constructor using Values object
    public TextReader(Values playerValues) {
        this.playerValues = playerValues;
        spawnList = new ArrayList<>();
        delayList = new ArrayList<>();
        initWaveFileAnalyser(playerValues.getLevel());
    }

    public String[] readLines() throws IOException {
        List<String> linesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                linesList.add(line);
            }
        }

        return linesList.toArray(new String[0]);
    }

    public int[][] readTileData() throws IOException {
        List<int[]> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                int[] data = new int[split.length];
                for (int i = 0; i < split.length; i++) {
                    data[i] = Integer.parseInt(split[i].trim());
                }
                dataList.add(data);
            }
        }

        int[][] dataArray = new int[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            dataArray[i] = dataList.get(i);
        }

        return dataArray;
    }

    public void initWaveFileAnalyser(int i) {
        try {
            reader = new BufferedReader(new FileReader(variables.Maps.getMapWaveFile(i)));
        } catch (IOException e) {
            System.out.println("WaveFileAnalyser can't read " + i);
        }
    }

    public int getWavesNum() {
        int lineCount = 0;
        try {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lineCount;
    }

    public void initArrayLists(int lineNum) {
        initWaveFileAnalyser(playerValues.getLevel());
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
                        for (String s2 : lineArray2) {
                            if(s2.contains("w")) {
                                String s2withoutCommas = s2.replace("w", "");
                                spawnList.add(Integer.parseInt(s2withoutCommas));
                            } else if (!s2.trim().isEmpty()) {
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
        return new ArrayList<>(spawnList);
    }

    public ArrayList<Integer> getDelayList() {
        return new ArrayList<>(delayList);
    }
}
