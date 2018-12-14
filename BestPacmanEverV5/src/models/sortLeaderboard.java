package models;

import java.util.Comparator;

public class sortLeaderboard implements Comparator<String> {


    @Override
    /**
     * This function is used to recursively to look through my array and pick out all of the best scores.
     * This function also takes a string which holds both the user score and name and splits it allowing me to create a leaderboard
     */
    public int compare(String o1, String o2) {
        int nameScoreA, nameScoreB;

        nameScoreA = Integer.parseInt(o1.split(":")[1]);
        nameScoreB = Integer.parseInt(o2.split(":")[1]);

        int compareNameScore = Integer.compare(nameScoreA, nameScoreB);

        if(compareNameScore != 0) {
            return compareNameScore;
        }

        return o1.compareTo(o2);
    }
}
