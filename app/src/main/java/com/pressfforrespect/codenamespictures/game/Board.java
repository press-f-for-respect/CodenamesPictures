package com.pressfforrespect.codenamespictures.game;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Board {
    private Team[][] team = new Team[4][5];
    private Team starter;
    private ArrayList<Integer> picNums = new ArrayList<>();
    private Random rand = new Random();


    public Board() {
        setPics();
        setStarter();
        setTeam();
    }

    private void setPics() {
        while (picNums.size() < 20) {
            int cardNum = rand.nextInt(80);
            if(!picNums.contains(cardNum))
                picNums.add(cardNum);
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

    public void endTurn(){
        if(starter == Team.RED)
            starter = Team.BLUE;
        else
            starter = Team.RED;
    }

    public Team getStarter() {
        return starter;
    }

    public ArrayList<Integer> getPicNums() {
        return picNums;
    }

    public Team[][] getTeam() {
        return team;
    }
}
