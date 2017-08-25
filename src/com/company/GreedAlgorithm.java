package com.company;

public class GreedAlgorithm {
    private GreedConfiguration CurrentConfiguration;

    public GreedAlgorithm (GreedConfiguration configuration)
    {
        CurrentConfiguration = configuration;
    }

    public double CalculateRoundResult() throws Exception
    {
        if (CurrentConfiguration == null)
        {
            throw new Exception("There is no Greed configuration present. Please create one somehow.");
        }

        int diceToRoll = 5;
        double currentPoints = 0;
        while (true)
        {
            RollResult roll = GreedRoll(diceToRoll, currentPoints);
            if (roll.PointsScored == 0)
            {
                return 0;
            }else if (!roll.ContinueRolling)
            {
                return currentPoints + roll.PointsScored;
            }else
            {
                currentPoints += roll.PointsScored;
                diceToRoll = roll.DiceToRoll;
            }
        }

    }

    private RollResult GreedRoll (int diceRemaining, double previousPoints)
    {
        int[] rolledDice = CreateDiceRoll(diceRemaining);
        if (!HasScoringDice(rolledDice))
        {
            return new RollResult(0, 0, false);
        }
        RollResult current = CalculateDicePointsAndRemainingDice(rolledDice);
        double currentPoints = previousPoints + current.PointsScored;
        current = DetermineIfWeContinueRolling(rolledDice, current, currentPoints);
        return current;
    }
    
    private int[] CreateDiceRoll (int howManyDice)
    {
        int[] rolledDice = new int[6];

        for (int diceToRoll = 0; diceToRoll < howManyDice; diceToRoll++)
        {
            int valueRolled = (int)(Math.random()*6);
            rolledDice[valueRolled]++;
        }
        return rolledDice;
    }

    private boolean HasScoringDice(int[] diceRolled)
    {
        return diceRolled[0] > 0 || diceRolled[4] > 0
                || diceRolled[1] > 2 || diceRolled[2] > 2
                || diceRolled[3] > 2 || diceRolled[5] > 2;
    }

    private RollResult CalculateDicePointsAndRemainingDice (int[] currentDice)
    {
        double pointsScored = 0;
        int diceRemaining = 5;
        for (int die = 0; die < 6; die++)
        {
            int pointValue = die + 1;
            if (die == 0)
            {
                pointValue = 10;
            }

            if (currentDice[die] == 3)
            {
                pointsScored += pointValue;
                diceRemaining -= 3;
            }else if (currentDice[die] == 4)
            {
                pointsScored += pointValue * 2;
                diceRemaining -= 4;
            }else if (currentDice[die] == 5)
            {
                pointsScored += pointValue *4;
                diceRemaining -= 5;
            }
        }

        if (currentDice[0] > 0 && currentDice[0] <3)
        {
            pointsScored = pointsScored + currentDice[0];
            diceRemaining -= currentDice[0];
        }

        if (currentDice[4] > 0 && currentDice[4] <3)
        {
            pointsScored = pointsScored + .5 * currentDice[4];
            diceRemaining -= currentDice[4];
        }

        return new RollResult(diceRemaining, pointsScored, false);
    }

    private RollResult DetermineIfWeContinueRolling(int[] rolledDice, RollResult current, double currentPoints)
    {
        current = RerollThreeOfAKindCheck(rolledDice, current, currentPoints);

        if (current.DiceToRoll == 1)
        {
            current = RerollOptionsWithOneSpareDice(rolledDice, current, currentPoints);
        }else if (current.DiceToRoll == 2)
        {
            current = RerollOptionsWithTwoSpareDice(rolledDice, current, currentPoints);
        }else if (current.DiceToRoll == 3)
        {
            current = RerollOptionsWithThreeSpareDice(rolledDice, current, currentPoints);
        }

        if (!current.ContinueRolling)
        {
            current.ContinueRolling = DetermineIfWeWillRerollBasedOnAvailableDice(current, currentPoints);
        }

        return current;
    }

    private RollResult RerollThreeOfAKindCheck (int[] rolledDice, RollResult current, double currentPoints)
    {
        if (CurrentConfiguration.RerollThree2 && CurrentConfiguration.RerollThree2PointMaximum >= currentPoints && current.PointsScored > 2 && rolledDice[1] == 3)
        {
            current.DiceToRoll += 3;
            current.PointsScored -= 2;
        }else if (CurrentConfiguration.RerollThree3 && CurrentConfiguration.RerollThree3PointMaximum >= currentPoints && current.PointsScored > 3 && rolledDice[2] == 3)
        {
            current.DiceToRoll += 3;
            current.PointsScored -= 3;
        }

        return current;
    }

    private RollResult RerollOptionsWithOneSpareDice (int[] rolledDice, RollResult current, double currentPoints)
    {
        if (CurrentConfiguration.PrioritizeRerollingMoreDiceWithOneSpareDice)
        {
            if (CurrentConfiguration.RerollOne1Two5PointMaximumWithOneSpareDice >= currentPoints && rolledDice[0] == 2 && rolledDice[4] == 2)
            {
                current.PointsScored -= 2;
                current.DiceToRoll += 3;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollTwo5PointMaximumWithOneSpareDice >= currentPoints && rolledDice[0] == 1 && rolledDice[4] == 2)
            {
                current.PointsScored -= 1;
                current.DiceToRoll += 2;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne1One5PointMaximumWithOneSpareDice >= currentPoints && rolledDice[0] == 2 && rolledDice[4] == 1)
            {
                current.PointsScored -= 1.5;
                current.DiceToRoll += 2;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne1PointMaximumWithOneSpareDice >= currentPoints && rolledDice[0] == 2)
            {
                current.PointsScored -= 1;
                current.DiceToRoll += 1;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne5PointMaximumWithOneSpareDice >= currentPoints
                    && ((rolledDice[0] == 1 && rolledDice[4] == 1) || rolledDice[4] == 2))
            {
                current.PointsScored -= .5;
                current.DiceToRoll += 1;
                current.ContinueRolling = true;
            }
        }else
        {
            if (CurrentConfiguration.RerollOne1PointMaximumWithOneSpareDice >= currentPoints && rolledDice[0] == 2)
            {
                current.PointsScored -= 1;
                current.DiceToRoll += 1;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne5PointMaximumWithOneSpareDice >= currentPoints
                    && ((rolledDice[0] == 1 && rolledDice[4] == 1) || rolledDice[4] == 2))
            {
                current.PointsScored -= .5;
                current.DiceToRoll += 1;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollTwo5PointMaximumWithOneSpareDice >= currentPoints && rolledDice[0] == 1 && rolledDice[4] == 2)
            {
                current.PointsScored -= 1;
                current.DiceToRoll += 2;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne1One5PointMaximumWithOneSpareDice >= currentPoints && rolledDice[0] == 2 && rolledDice[4] == 1)
            {
                current.PointsScored -= 1.5;
                current.DiceToRoll += 2;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne1Two5PointMaximumWithOneSpareDice >= currentPoints && rolledDice[0] == 2 && rolledDice[4] == 2)
            {
                current.PointsScored -= 2;
                current.DiceToRoll += 3;
                current.ContinueRolling = true;
            }
        }

        return current;
    }

    private RollResult RerollOptionsWithTwoSpareDice (int[] rolledDice, RollResult current, double currentPoints)
    {
        if (CurrentConfiguration.PrioritizeRerollingMoreDiceWithTwoSpareDice)
        {
            if (CurrentConfiguration.RerollOne1One5PointMaximumWithTwoSpareDice >= currentPoints && rolledDice[0] == 2 && rolledDice[4] == 1)
            {
                current.PointsScored -= 1.5;
                current.DiceToRoll += 2;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollTwo5PointMaximumWithTwoSpareDice >= currentPoints && rolledDice[0] == 1 && rolledDice[4] == 2)
            {
                current.PointsScored -= 1;
                current.DiceToRoll += 2;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne5PointMaximumWithTwoSpareDice >= currentPoints && ((rolledDice[0] == 1 && rolledDice[4] == 2)
                    || (rolledDice[0] == 2 && rolledDice[4] == 1) || (rolledDice[0] == 0 && rolledDice[4] == 2)))
            {
                current.PointsScored -= .5;
                current.DiceToRoll += 1;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne1PointMaximumWithTwoSpareDice >= currentPoints && rolledDice[0] == 2 && rolledDice[4] == 0)
            {
                current.PointsScored -= 1;
                current.DiceToRoll += 1;
                current.ContinueRolling = true;
            }
        }else
        {
            if (CurrentConfiguration.RerollOne5PointMaximumWithTwoSpareDice >= currentPoints && ((rolledDice[0] == 1 && rolledDice[4] == 2)
                    || (rolledDice[0] == 2 && rolledDice[4] == 1) || (rolledDice[0] == 0 && rolledDice[4] == 2)))
            {
                current.PointsScored -= .5;
                current.DiceToRoll += 1;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne1PointMaximumWithTwoSpareDice >= currentPoints && rolledDice[0] == 2 && rolledDice[4] == 0)
            {
                current.PointsScored -= 1;
                current.DiceToRoll += 1;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollOne1One5PointMaximumWithTwoSpareDice >= currentPoints && rolledDice[0] == 2 && rolledDice[4] == 1)
            {
                current.PointsScored -= 1.5;
                current.DiceToRoll += 2;
                current.ContinueRolling = true;
            }else if (CurrentConfiguration.RerollTwo5PointMaximumWithTwoSpareDice >= currentPoints && rolledDice[0] == 1 && rolledDice[4] == 2)
            {
                current.PointsScored -= 1;
                current.DiceToRoll += 2;
                current.ContinueRolling = true;
            }
        }

        return current;
    }

    private RollResult RerollOptionsWithThreeSpareDice(int[] rolledDice, RollResult current, double currentPoints)
    {
        if (CurrentConfiguration.RerollOne1PointMaximumWithThreeSpareDice >= currentPoints && rolledDice[0] == 2)
        {
            current.PointsScored -= 1;
            current.DiceToRoll += 1;
            current.ContinueRolling = true;
        }else if (CurrentConfiguration.RerollOne5PointMaximumWithThreeSpareDice >= currentPoints && (rolledDice[4] == 2 || (rolledDice[0] == 1 && rolledDice[4] == 1)))
        {
            current.PointsScored -= .5;
            current.DiceToRoll += 1;
            current.ContinueRolling = true;
        }
        return current;
    }

    private boolean DetermineIfWeWillRerollBasedOnAvailableDice (RollResult current, double currentPoints)
    {
        if (current.DiceToRoll == 5)
        {
            return CurrentConfiguration.RerollFiveDicePointMaximum >= currentPoints;
        }else if (current.DiceToRoll == 4)
        {
            return CurrentConfiguration.RerollFourDicePointMaximum >= currentPoints;
        }else if (current.DiceToRoll == 3)
        {
            return CurrentConfiguration.RerollThreeDicePointMaximum >= currentPoints;
        }else if (current.DiceToRoll == 2)
        {
            return CurrentConfiguration.RerollTwoDicePointMaximum >= currentPoints;
        }else
        {
            return CurrentConfiguration.RerollOneDicePointMaximum >= currentPoints;
        }
    }

}

class RollResult
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
