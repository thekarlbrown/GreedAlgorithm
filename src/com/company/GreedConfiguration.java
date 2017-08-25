package com.company;

import java.util.Scanner;

public class GreedConfiguration {
    public boolean RerollThree2;
    public double RerollThree2PointMaximum;
    public boolean RerollThree3;
    public double RerollThree3PointMaximum;

    public boolean PrioritizeRerollingMoreDiceWithOneSpareDice;
    public double RerollOne1Two5PointMaximumWithOneSpareDice;
    public double RerollTwo5PointMaximumWithOneSpareDice;
    public double RerollOne1One5PointMaximumWithOneSpareDice;
    public double RerollOne5PointMaximumWithOneSpareDice;
    public double RerollOne1PointMaximumWithOneSpareDice;

    public boolean PrioritizeRerollingMoreDiceWithTwoSpareDice;
    public double RerollTwo5PointMaximumWithTwoSpareDice;
    public double RerollOne1One5PointMaximumWithTwoSpareDice;
    public double RerollOne5PointMaximumWithTwoSpareDice;
    public double RerollOne1PointMaximumWithTwoSpareDice;

    public double RerollOne5PointMaximumWithThreeSpareDice;
    public double RerollOne1PointMaximumWithThreeSpareDice;

    public double RerollFiveDicePointMaximum;
    public double RerollFourDicePointMaximum;
    public double RerollThreeDicePointMaximum;
    public double RerollTwoDicePointMaximum;
    public double RerollOneDicePointMaximum;
    
    public int NumberOfRounds;

    private static Scanner input = new Scanner(System.in);
    private static final String YesOrNoPrompt = "Y(es) or N(o): ";
    private static final String DecimalScorePrompt = "Please enter a score in decimal form in increments of 0.5: ";
    private static final String IntegerPrompt = "Please enter a positive whole number: ";

    private static boolean ObtainBooleanFromUser()
    {
        String currentInput = input.nextLine();
        while (!currentInput.equalsIgnoreCase("Y")
                && !currentInput.equalsIgnoreCase("N")
                && !currentInput.equalsIgnoreCase("Yes")
                && !currentInput.equalsIgnoreCase("No"))
        {
            System.out.print("Please enter " + YesOrNoPrompt);
            currentInput = input.nextLine();
        }
        return currentInput.equalsIgnoreCase("Yes") || currentInput.equalsIgnoreCase("Y");
    }

    private static double ObtainScoreFromUser()
    {
        //TODO I question the efficiency of this, as well as the re-instantiation of Scanner. This should be revisited.
        double nextDouble = -0.6;
        while (nextDouble % 0.5 != 0.0)
        {
            try
            {
                nextDouble = input.nextDouble();
                if (nextDouble % 0.5 != 0.0)
                {
                    System.out.print(DecimalScorePrompt);
                }
            }
            catch (Exception ex)
            {
                System.out.print(DecimalScorePrompt);
                input = new Scanner(System.in); // Pretty sure this is a hack
            }
        }

        return nextDouble;
    }

    private static int ObtainIntegerFromUser()
    {
        //TODO I question the efficiency of this, as well as the re-instantiation of Scanner. This should be revisited.
        int nextInteger = -1;
        while (nextInteger < 1)
        {
            try
            {
                nextInteger = input.nextInt();
                if (nextInteger < 1)
                {
                    System.out.print(IntegerPrompt);
                }
            }
            catch (Exception ex)
            {
                System.out.print(IntegerPrompt);
                input = new Scanner(System.in); // Pretty sure this is a hack
            }
        }

        return nextInteger;
    }

    public void SetGreedConfigurationForRerolling()
    {
        System.out.print("Will you have this program re-roll three 2's? " + YesOrNoPrompt);
        RerollThree2 = ObtainBooleanFromUser();
        if (RerollThree2)
        {
            System.out.print("What is the point maximum at which you stop re-rolling three 2's? " + DecimalScorePrompt);
            RerollThree2PointMaximum = ObtainScoreFromUser();
        }

        System.out.print("Will you have this program re-roll three 3's? " + YesOrNoPrompt);
        RerollThree3 = ObtainBooleanFromUser();
        if (RerollThree3)
        {
            System.out.print("What is the point maximum at which you stop re-rolling three 2's? " + DecimalScorePrompt);
            RerollThree3PointMaximum = ObtainScoreFromUser();
        }
    }

    public void SetGreedConfigurationForOneSpareDice()
    {
        System.out.print("Do you want to prioritize re-rolling the most dice possible when you have one spare dice? " + YesOrNoPrompt + " No will re-roll the least dice possible. ");
        PrioritizeRerollingMoreDiceWithOneSpareDice = ObtainBooleanFromUser();

        System.out.print("What is the point maximum at which you would re-roll one 1 and two 5's with one spare dice?: " + DecimalScorePrompt);
        RerollOne1Two5PointMaximumWithOneSpareDice = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll two 5's with one spare dice?: " + DecimalScorePrompt);
        RerollTwo5PointMaximumWithOneSpareDice = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll one 1 and one 5's with one spare dice?: " + DecimalScorePrompt);
        RerollOne1One5PointMaximumWithOneSpareDice = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll one 5 with one spare dice?: " + DecimalScorePrompt);
        RerollOne5PointMaximumWithOneSpareDice = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll one 1 with one spare dice?: " + DecimalScorePrompt);
        RerollOne1PointMaximumWithOneSpareDice = ObtainScoreFromUser();
    }

    public void SetGreedConfigurationForTwoSpareDice()
    {
        System.out.print("Do you want to prioritize re-rolling the most dice possible when you have two spare dice? " + YesOrNoPrompt + " No will re-roll the least dice possible. " );
        PrioritizeRerollingMoreDiceWithTwoSpareDice = ObtainBooleanFromUser();

        System.out.print("What is the point maximum at which you would re-roll two 5's with two spare dice?: " + DecimalScorePrompt);
        RerollTwo5PointMaximumWithTwoSpareDice = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll one 1 and one 5 with two spare dice?: " + DecimalScorePrompt);
        RerollOne1One5PointMaximumWithTwoSpareDice = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll one 5 with two spare dice?: " + DecimalScorePrompt);
        RerollOne5PointMaximumWithTwoSpareDice = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll one 1 with two spare dice?: " + DecimalScorePrompt);
        RerollOne1PointMaximumWithTwoSpareDice = ObtainScoreFromUser();
    }

    public void SetGreedConfigurationForThreeSpareDice()
    {
        System.out.print("What is the point maximum at which you would re-roll one 5 with two spare dice?: " + DecimalScorePrompt);
        RerollOne5PointMaximumWithThreeSpareDice = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll one 1 with two spare dice?: " + DecimalScorePrompt);
        RerollOne1PointMaximumWithThreeSpareDice = ObtainScoreFromUser();
    }

    public void SetGreedConfigurationForRerollingAvailableDice()
    {
        System.out.print("What is the point maximum at which you would re-roll five available dice?: " + DecimalScorePrompt);
        RerollFiveDicePointMaximum = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll four available dice?: " + DecimalScorePrompt);
        RerollFourDicePointMaximum = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll three available dice?: " + DecimalScorePrompt);
        RerollThreeDicePointMaximum = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll two available dice?: " + DecimalScorePrompt);
        RerollTwoDicePointMaximum = ObtainScoreFromUser();

        System.out.print("What is the point maximum at which you would re-roll one available dice?: " + DecimalScorePrompt);
        RerollOneDicePointMaximum = ObtainScoreFromUser();
    }
    
    public void SetGreedConfigurationForNumberOfRounds()
    {
        System.out.print("How many rounds do you want the computer to play Greed for with the current configuration? " + IntegerPrompt);
        NumberOfRounds = ObtainIntegerFromUser();
    }
    
}
