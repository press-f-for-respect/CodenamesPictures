package com.pressfforrespect.codenamespictures.game;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Board {
    private Team[] team = new Team[20];
    private Team starter;
    private ArrayList<Integer> picNums = new ArrayList<>();
    private Random rand = new Random();
    private int numOfBlueCards = 7, numOfRedCards = 7;


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

       if(starter == Team.RED)
           numOfRedCards += 1;
       else{
           initial[7] = Team.BLUE;
           numOfBlueCards += 1;
       }

        Collections.shuffle(Arrays.asList(initial));

        team = initial;
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

    public Team[] getTeam() {
        return team;
    }

    public int reduceNumOfTeamCards(Team team){
        if(team == Team.RED)
            numOfRedCards -= 1;
        if(team == Team.BLUE)
            numOfBlueCards -= 1;

        if(numOfBlueCards == 0)
            return 2;
        if(numOfRedCards == 0)
            return 1;
        return 0;
    }
}
