package helpers;

import java.util.ArrayList;

public class Wave {
    private final ArrayList<Integer> spawnList;
    private final ArrayList<Integer> delayList;
    private int currentIndex = 0;

    public Wave(ArrayList<Integer> spawnList, ArrayList<Integer> delayList) {
        this.spawnList = spawnList;
        this.delayList = delayList;
    }

    public ArrayList<Integer> getSpawnList() {
        return spawnList;
    }

    public ArrayList<Integer> getDelayList() {return delayList;}

    public int getCurrentDelay() {
        if (delayList.size() > currentIndex) {
//            System.out.println(delayList.get(currentIndex));
            return delayList.get(currentIndex);
        }
        return -1;
    }
    public int getCurrentEnemyType() {
        if (spawnList.size() > currentIndex) {
            return spawnList.get(currentIndex);
        }
        return -1;
    }
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
    public int getSize() {return spawnList.size();}
}
