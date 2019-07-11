package com.pressfforrespect.codenamespictures.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.HashSet;


public class Board {
    private Team[][] team = new Team[5][4];
    private Team starter;
    private String[][] pics = new String[5][4];
    private Random rand = new Random();


    public Board() {
        setPics();
        setStarter();
        setTeam();
    }

    private void setPics() {
        HashSet<Integer> picNums = new HashSet<>();

        while (picNums.size() < 20) {
            picNums.add(rand.nextInt(80) + 1);
        }
        Object[] picNumsArr = picNums.toArray();
        for (int i = 0; i < 20; i++) {
            pics[i / 5][i % 5] = Integer.toString((int) picNumsArr[i]) + ".png";
        }
    }

    private void setStarter() {
        if(rand.nextBoolean()) {
            starter = Team.RED;
        } else {
            starter = Team.BLUE;
        }

    }

    private void setTeam() {
        Team[] initial = {Team.RED, Team.RED, Team.RED, Team.RED, Team.RED, Team.RED, Team.RED, Team.RED,
                Team.BLUE, Team.BLUE, Team.BLUE, Team.BLUE, Team.BLUE, Team.BLUE, Team.BLUE,
                Team.BYSTANDER, Team.BYSTANDER, Team.BYSTANDER, Team.BYSTANDER, Team.ASSASSIN};

       initial[7] = (starter == Team.RED) ? Team.RED : Team.BLUE;

        Collections.shuffle(Arrays.asList(initial));

        for (int i = 0; i < 20; i++) {
            team[i / 5][i % 5] = initial[i];
        }
    }
}
