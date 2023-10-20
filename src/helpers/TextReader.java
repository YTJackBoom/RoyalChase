package helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse zum Lesen von Textdateien
 */
public class TextReader {

    private InputStream file;
    private BufferedReader reader;
    private ArrayList<Integer> spawnList, delayList;
    private Values playerValues;

    /**
     * Konstruktor für den TextReader
     * @param file Datei
     */
    public TextReader(InputStream file) {
        this.file = file;
    }

    /**
     * Konstruktor für den WaveFileAnalyser
     * @param playerValues Values-Objekt
     */
    public TextReader(Values playerValues) {
        this.playerValues = playerValues;
        spawnList = new ArrayList<>();
        delayList = new ArrayList<>();
        initWaveFileAnalyser(playerValues.getLevel());
    }

    /**
     * Methode um die Zeilen einer Textdatei zu lesen
     * @return Zeilen der Textdatei
     */
    public String[] readLines() throws IOException {
        List<String> linesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                linesList.add(line);
            }
        }

        return linesList.toArray(new String[0]);
    }


    /**
     * Methode um die Map daten aus einer Textdatei zu lesen
     * @return Mapdaten als 2D-Array
     */
    public int[][] readTileData() throws IOException {
        List<int[]> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
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

        int rows = dataList.size();
        int cols = dataList.get(0).length;
        int[][] dataArray = new int[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                dataArray[i][j] = dataList.get(j)[i];
            }
        }

        return dataArray;
    }

    /**
     * Initialisiert für WaveFile Analysen
     * @param i Level
     */
    public void initWaveFileAnalyser(int i) {
        reader = new BufferedReader(new InputStreamReader(AssetLocation.Maps.getMapWaveFile(i)));
    }

    /**
     * Methode um die Anzahl an Wellen zu bekommen
     * @return Anzahl an Wellen
     */
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

    /**
     * Methode um die Spawn- und Delay-Listen zu initialisieren
     * @param lineNum Zeilennummer
     */
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
