package com.esiljak.helpers;

import com.esiljak.exceptions.IllegalQueryException;
import com.esiljak.models.IntergalacticConversion;

import java.util.Scanner;

public class ProgramHelper {
    private static final String SUCCESSFUL_ADDITION = "";
    private static final Scanner scanner = new Scanner(System.in);
    private static final IntergalacticConversion conversion = new IntergalacticConversion();

    private static void invalidQuery(){
        System.out.println("Invalid query, please try again\n");
    }

    private static void endProgram(){
        System.out.println("Closing the program....");
    }

    private static String sentenceInput(){
        System.out.println("Enter your query for adding new entry");
        System.out.println("or");
        System.out.println("Enter your query for program to answer");
        System.out.println("or");
        System.out.println("Press enter to exit the program");
        System.out.print("> ");
        return scanner.nextLine();
    }

    private static String processSentence(String sentence) throws IllegalQueryException {
        if (sentence.trim().isEmpty())
            return null;

        try {
            conversion.addEntry(sentence);
            return SUCCESSFUL_ADDITION;
        } catch (Exception ignored) {}

        try {
            conversion.addSellingItem(sentence);
            return SUCCESSFUL_ADDITION;
        } catch (Exception ignored) {}

        return conversion.query(sentence);
    }

    public static void startProgramFlow(){
        while (true){
            String input = sentenceInput();
            String answer;
            try {
                answer = processSentence(input);
            } catch (IllegalQueryException e) {
                invalidQuery();
                continue;
            }

            if (answer == null){
                endProgram();
                break;
            }else if(!answer.equals(SUCCESSFUL_ADDITION)){
                System.out.println("> " + answer + "\n");
            }
        }
    }
}
