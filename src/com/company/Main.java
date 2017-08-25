package com.company;
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        GreedConfiguration currentGameConfiguration = new GreedConfiguration();

        // Set the configuration for re-rolling dice when you have three 2's or 3's
        currentGameConfiguration.SetGreedConfigurationForRerolling();

        // Set the configuration for re-rolling dice when you have one spare dice
        currentGameConfiguration.SetGreedConfigurationForOneSpareDice();

        // Set the configuration for re-rolling dice when you have two spare dice
        currentGameConfiguration.SetGreedConfigurationForTwoSpareDice();
        
        // Set the configuration for re-rolling dice when you have three spare dice
        currentGameConfiguration.SetGreedConfigurationForThreeSpareDice();

        // Set the configuration for the maximum points at which you will re-roll your available dice
        currentGameConfiguration.SetGreedConfigurationForRerollingAvailableDice();

        // Figure out how many rounds we will be simulating
        currentGameConfiguration.SetGreedConfigurationForNumberOfRounds();

        // Using safe variables, calculate the total points scored over a number of rounds on average
        BigDecimal averageScore = new BigDecimal("0");
        GreedAlgorithm algorithm = new GreedAlgorithm(currentGameConfiguration);
        try
        {
            for (int currentRoundCount = 0; currentRoundCount < currentGameConfiguration.NumberOfRounds; currentRoundCount++)
            {
                double roundScore = algorithm.CalculateRoundResult();
                averageScore = averageScore.add(BigDecimal.valueOf(roundScore));
            }
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        averageScore = averageScore.divide(BigDecimal.valueOf(currentGameConfiguration.NumberOfRounds));

        System.out.println("The average score over " + currentGameConfiguration.NumberOfRounds
        + " rounds with the specified configuration is: " + averageScore.toString());
    }
    
}
