package basics;

import java.util.ArrayList;

public class Wave {
    private ArrayList<Integer> spawnList;
    private int currentIndex = 0;

    public Wave(ArrayList<Integer> spawnList) {
        this.spawnList = spawnList;
    }

    public ArrayList<Integer> getSpawnList() {
        return spawnList;
    }

    public int getCurrentEnemyType() {
        return spawnList.get(currentIndex);
    }
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
