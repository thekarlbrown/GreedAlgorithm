package com.company;

public class RollResult
{
    int DiceToRoll;
    double PointsScored;
    boolean ContinueRolling;

    public RollResult (int diceToRoll, double pointsScored, boolean continueRolling)
    {
        DiceToRoll = diceToRoll;
        PointsScored = pointsScored;
        ContinueRolling = continueRolling;
    }
}