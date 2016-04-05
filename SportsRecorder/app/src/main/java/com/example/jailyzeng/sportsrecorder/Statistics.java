package com.example.jailyzeng.sportsrecorder;

/**
 * Created by liwingyee on 5/4/16.
 */
public class Statistics {

    private static String teamNameA = "Team A";
    private static String teamNameB = "Team B";

    private static int firstScoreA = 0;
    private static int secondScoreA = 0;
    private static int firstScoreB = 0;
    private static int secondScoreB = 0;

    public static String getTeamNameA() {
        return teamNameA;
    }

    public static void setTeamNameA(String teamNameA) {
        if( teamNameA.length() > 0 ) Statistics.teamNameA = teamNameA;
    }

    public static String getTeamNameB() {
        return teamNameB;
    }

    public static void setTeamNameB(String teamNameB) {
        if( teamNameB.length() > 0 ) Statistics.teamNameB = teamNameB;
    }


    public static int getFirstScoreA() {
        return firstScoreA;
    }

    public static void incrementFirstScoreA() {
        Statistics.firstScoreA++;
    }

    public static void decrementFirstScoreA() {
        if( firstScoreA > 0 ) Statistics.firstScoreA--;
    }

    public static int getSecondScoreA() {
        return secondScoreA;
    }

    public static void incrementSecondScoreA() {
        Statistics.secondScoreA++;
    }

    public static void decrementSecondScoreA() {
        if( secondScoreA > 0 ) Statistics.secondScoreA--;
    }

    public static int getFirstScoreB() {
        return firstScoreB;
    }

    public static void incrementFirstScoreB() {
        Statistics.firstScoreB++;
    }

    public static void decrementFirstScoreB() {
        if( firstScoreB > 0 ) Statistics.firstScoreB--;
    }

    public static int getSecondScoreB() {
        return secondScoreB;
    }

    public static void incrementSecondScoreB() {
        Statistics.secondScoreB++;
    }

    public static void decrementSecondScoreB() {
        if( secondScoreB > 0 ) Statistics.secondScoreB--;
    }

}
