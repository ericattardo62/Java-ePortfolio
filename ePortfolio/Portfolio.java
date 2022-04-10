package ePortfolio;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

/* */
public class Portfolio{

/* */
    public static void main(String args[]){
        Scanner key = new Scanner(System.in);
        HashMap<String, ArrayList<Integer>> words = new HashMap<String, ArrayList<Integer>>();
        
      
        String input;

        // Used to track values for setters throughout code
        String investType;
        String investSymbol;
        String investName;
        double investPrice;
        int investQuantity;

        // Will hold position of index in list to change
        int updateVal = 0;

        ArrayList<Investment> investments = new ArrayList<Investment>();
        
        try {
            File file = new File(args[0]);
            Scanner fileReader = new Scanner(file);
            investments = loadFromFile(fileReader);
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Error loading file...");
        }
        
        words = makeHash(investments);


        System.out.println("Please Enter: Buy, Sell, Update, getGain, Search or Quit");
        input = key.nextLine();

        while(!input.equalsIgnoreCase("quit") && !input.equalsIgnoreCase("q")){

            // Will be used to see if matches are found within lists
            boolean match = false;
            // Check if investment is stock or mutual
            boolean isStock = false;
            // Check if needs to break
            boolean breakOut = false;



            while(!input.equalsIgnoreCase("buy") && !input.equalsIgnoreCase("sell") && !input.equalsIgnoreCase("update") 
            && !input.equalsIgnoreCase("getGain") && !input.equalsIgnoreCase("search")){
                System.out.println("Input Invalid... Please try again");
                System.out.println("Please Enter: Buy, Sell, Update, getGain, Search or Quit");
                input = key.nextLine();
            }

            if(input.equalsIgnoreCase("buy")){
                System.out.println("Please state whether you are purchasing Stock or MutualFund:");
                investType = key.nextLine();

                while(!investType.equalsIgnoreCase("Stock") && !investType.equalsIgnoreCase("MutualFund")){
                    System.out.println("Invalid entry...");
                    System.out.println("Please state whether you are purchasing Stock or MutualFund:");
                    investType = key.nextLine();
                }

                if(investType.equalsIgnoreCase("stock")){
                    isStock = true;
                }
                System.out.println("Please Enter the symbol for the Investment:");
                investSymbol = key.nextLine();

                for(int i = 0; i < investments.size(); i++){
                    // Check if symbol exists, store position and flip match to true
                    if(investments.get(i).getSymbol().equalsIgnoreCase(investSymbol)){
                        updateVal = i;
                        match = true;
                    }
                }

                if(updateVal < 0){
                    updateVal = 0;
                }

                if(isStock){
                    if(investments.size() != 0 && investments.get(updateVal) instanceof MutualFund && match){
                        System.out.println("Mutual Fund with this symbol exists...");
                        breakOut = true;
                    }

                }

                else{
                    if(investments.size() != 0 && investments.get(updateVal) instanceof Stock && match){
                        System.out.println("Stock with this symbol exists...");
                        breakOut = true;
                    }
                }
                // Symbol in system
                if(match && !breakOut){
                                 
                    System.out.println("Match found for symbol: " + investSymbol);
                    System.out.println("Please enter purchase quantity:");
                     
                    // Input Validation
                    String tempString = key.nextLine();
                    investQuantity = verifyIntInput(tempString);
                    
                    while(investQuantity <= 0){
                        System.out.print("Please enter a numerical quantity above 0: ");
                        tempString = key.nextLine();
                        investQuantity = verifyIntInput(tempString);
                    }
                    
                    System.out.println("Please enter price:");
                   
                    // Input Validation
                    tempString = key.nextLine();
                    investPrice = verifyDoubleInput(tempString);
                    
                    while(investPrice <= 0){
                        System.out.print("Please enter a numerical value above 0: ");
                        tempString = key.nextLine();
                        investPrice = verifyDoubleInput(tempString);
                    }

                    // Update existing quantity and price
                    investments.get(updateVal).setQuantity(investments.get(updateVal).getQuantity() + investQuantity);
                    investments.get(updateVal).setPrice(investPrice);                    
                }
                
                // Symbol not found
                else if(!match){
                    updateVal = investments.size() - 1;
                    System.out.println("Please enter the name of investment:");
                    investName = key.nextLine();
                
                    
                    System.out.println("Please enter purchase quantity:");
                    // Input Validation
                    String tempString = key.nextLine();
                    investQuantity = verifyIntInput(tempString);
                    
                    while(investQuantity <= 0){
                        System.out.print("Please enter a numerical quantity above 0: ");
                        tempString = key.nextLine();
                        investQuantity = verifyIntInput(tempString);
                    }
                    
                    System.out.println("Please enter price:");
                    // Input Validation
                    tempString = key.nextLine();
                    investPrice = verifyDoubleInput(tempString);
                    
                    while(investPrice <= 0){
                        System.out.print("Please enter a numerical value above 0: ");
                        tempString = key.nextLine();
                        investPrice = verifyDoubleInput(tempString);
                    }
                    
                    // Create new stock object to be added to list
                    if(isStock){
                        Stock stock = new Stock(investSymbol, investName, investQuantity, investPrice, investPrice * investQuantity);
                        investments.add(stock);
                    }
                    else{
                        MutualFund mutual = new MutualFund(investSymbol, investName, investQuantity, investPrice, investPrice * investQuantity);
                        investments.add(mutual);
                    }
                    
                    
                }

                words = makeHash(investments);
            } // End of buy

            else if(input.equalsIgnoreCase("sell")){
                System.out.println("Enter symbol of investment to sell: ");
                investSymbol = key.nextLine();
                
                // Check if symbol exists within mutuals ArrayList
                for(int i = 0; i < investments.size(); i++){
                    if(investments.get(i).getSymbol().equalsIgnoreCase(investSymbol)){
                         updateVal = i;
                         match = true;
                     }
                 }

                if(match){
                    System.out.println("Enter the quantity to sell:");
                    // Input Validation
                    String tempString = key.nextLine();
                    investQuantity = verifyIntInput(tempString);
                    
                    while(investQuantity <= 0){
                        System.out.print("Please enter a numerical quantity above 0: ");
                        tempString = key.nextLine();
                        investQuantity = verifyIntInput(tempString);
                    }

                    System.out.println("Enter price to sell at: ");
                    // Input Validation
                    tempString = key.nextLine();
                    investPrice = verifyDoubleInput(tempString);
                    
                    while(investPrice <= 0){
                        System.out.print("Please enter a numerical value above 0: ");
                        tempString = key.nextLine();
                        investPrice = verifyDoubleInput(tempString);
                    }

                    // If sell quantity is greater then owned quantity
                    if(investQuantity > investments.get(updateVal).getQuantity()){
                        System.out.println("Quantity too great to sell...");
                    }

                    // If 100% sold remove from list
                    else if(investQuantity == investments.get(updateVal).getQuantity()){
                        System.out.println("All units sold...");
                        System.out.printf("Payment received: $%.2f%n", investQuantity * investPrice - 45.00);
                        investments.remove(updateVal);
                    }

                    // Update Book Value and Quantity for under owned quantity
                    else{
                        System.out.println(investQuantity + " units sold...");
                        System.out.printf("Payment received: $%.2f%n", investQuantity * investPrice - 45.00);
                        investments.get(updateVal).setBookValue(investments.get(updateVal).getBookValue() * (investments.get(updateVal).getQuantity() - investQuantity)/investments.get(updateVal).getQuantity());
                        investments.get(updateVal).setQuantity(investments.get(updateVal).getQuantity() - investQuantity);
                    }
                }

                // Not found in either list
                else{
                    System.out.println("Symbol not found...");
                }
            
                words = makeHash(investments);
            
            } // End of Sell

            else if(input.equalsIgnoreCase("update")){

                // Loop through both lists and prompt to enter new prices
                for(int i = 0; i < investments.size(); i++){
                    System.out.println((i+1) + ": Enter the price for " + investments.get(i).getSymbol());
                    // Input Validation
                    String tempString = key.nextLine();
                    investPrice = verifyDoubleInput(tempString);
                    
                    while(investPrice <= 0){
                        System.out.print("Please enter a numerical value above 0: ");
                        tempString = key.nextLine();
                        investPrice = verifyDoubleInput(tempString);
                    }

                    investments.get(i).setPrice(investPrice);
                }
            }

            else if(input.equalsIgnoreCase("getGain")){
                double totalGain = 0;
                double payment; 

                for(int i = 0; i < investments.size(); i++){
                    payment = investments.get(i).getPrice() * investments.get(i).getQuantity() - 9.99;
                    totalGain += payment - investments.get(i).getBookValue();
                }
                
                
                System.out.printf("The total gain for this portfolio is: $%.2f%n", totalGain);
            }

            else if(input.equalsIgnoreCase("search")){
                String priceRange;
                String keyWords;

                System.out.print("Enter symbol to search: ");
                investSymbol = key.nextLine();
                System.out.print("Enter key words to search: ");
                keyWords = key.nextLine();
                System.out.print("Enter price range to search: ");
                priceRange = key.nextLine();

                search(investments, investSymbol, keyWords, priceRange);
            }    

            else if(input.equalsIgnoreCase("quit")){
                System.exit(0);
            }

            printInvestments(investments);
            key.nextLine();
            System.out.println("\nPlease Enter: Buy, Sell, Update, getGain, Search or Quit");
            input = key.nextLine();
        }

    try {
        FileWriter fileWrite = new FileWriter(args[0]);
        writeToFile(investments, fileWrite);
        fileWrite.close();
    } catch (Exception e) {
        System.out.println("Error writing to file...");
    }
        key.close();
    }

    private static void printInvestments(ArrayList<Investment> investments){
        int counter = 1;
        System.out.println("\n-------Printing Stocks-----------");
        for(int i = 0; i < investments.size(); i++){
            if(investments.get(i) instanceof Stock){
                System.out.printf("%d: %s %s %d %.2f %.2f%n", counter, investments.get(i).getSymbol(),
                investments.get(i).getName(), investments.get(i).getQuantity(), investments.get(i).getPrice(),
                investments.get(i).getBookValue());
            }
            counter++;
        }
        System.out.println("-------Printing Mutuals-----------");
        counter = 1;
        for(int i = 0; i < investments.size(); i++){
            if(investments.get(i) instanceof MutualFund){
                System.out.printf("%d: %s %s %d %.2f %.2f%n", counter, investments.get(i).getSymbol(),
                investments.get(i).getName(), investments.get(i).getQuantity(), investments.get(i).getPrice(),
                investments.get(i).getBookValue());
            }
            counter++;
        }
    }

    private static void search(ArrayList<Investment> investments, String symbol, String keywords, String priceRange){

        boolean searchKeyword = false;
        boolean searchSymbol = false;
        boolean searchRange = false;
        boolean match = false;
        boolean isSplit = false;
        String []ranges = null;
        double lowRange = -1;
        double highRange = -1;

        if(!symbol.equals("")){
            searchSymbol = true;
        }

        if(!keywords.equals("")){
            searchKeyword = true;
        }

        if(!priceRange.equals("")){
            searchRange = true;

            if(priceRange.contains("-")){
                ranges = priceRange.split("-");
                if(!ranges[0].equals("")){
                    lowRange = Double.parseDouble(ranges[0]);
                }
                
                try {
                    if(!ranges[1].equals("")){
                        highRange = Double.parseDouble(ranges[1]);
                    }
                } catch (Exception e) {
                    highRange = -1;
                }
                

                isSplit = true;
            }

            else{
                // Not full range, check if equal
                lowRange = Double.parseDouble(priceRange);
            }
        }    

        if(searchSymbol || searchKeyword || searchRange){
            for(int i = 0; i < investments.size(); i++){

                boolean rangeMatch = false;
                boolean keywordMatch = false;
                boolean symbolMatch = false;
    
                if(searchRange){
                    rangeMatch = searchInvestmentPriceRange(investments.get(i), lowRange, highRange, isSplit);
                }
    
                else{
                    rangeMatch = true;
                }
    
                if(searchKeyword){
                    keywordMatch = searchInvestmentKeywords(investments.get(i), keywords);
                }
    
                else{
                    keywordMatch = true;
                }
    
                if(searchSymbol){
                    symbolMatch = investments.get(i).getSymbol().equalsIgnoreCase(symbol.toLowerCase());
                }
    
                else{
                    symbolMatch = true;
                }
    
                if(rangeMatch && keywordMatch && symbolMatch){
                    System.out.printf("Investment found: %s %s %d %.2f %.2f%n", investments.get(i).getSymbol(),
                    investments.get(i).getName(), investments.get(i).getQuantity(), investments.get(i).getPrice(),
                    investments.get(i).getBookValue());
                    match = true;
                }
            }

            if(!match){
                System.out.println("No matching investments found...");
            }
    
        }

        else{
            System.out.println("No values received through input...");
        }
    }

    private static boolean searchInvestmentPriceRange(Investment investment, double lowRange, double highRange, boolean isSplit){

        // If string is split
        if(isSplit){
            // If a proper range has been provided XX-XX
            if(lowRange != -1 && highRange != -1){
                if(investment.getPrice() >= lowRange && investment.getPrice() <= highRange){
                    return true;
                }
            }

            // Higher than value given XX-
            else if(lowRange != -1 && highRange == -1){
                if(investment.getPrice() >= lowRange){
                    return true;
                }
            }

            // Lower than value given -XX
            else if(lowRange == -1 && highRange != -1){
                if(investment.getPrice() <= highRange){
                    return true;
                }
            }
        }

        // Range was not split meaning checking for exact price
        else{
            if(investment.getPrice() == lowRange){
                return true;
            }
        }

        // No match for given price range
        return false;
    }

    private static boolean searchInvestmentKeywords(Investment investment, String keywords){
        String [] keySplit = keywords.split(" ");
        boolean keyFound = true;

        for(int i = 0; i < keySplit.length; i++){
            if(!investment.getName().toLowerCase().contains(keySplit[i].toLowerCase())){
                keyFound = false;
            }
        }

        return keyFound;
    }

    private static ArrayList<Investment> loadFromFile(Scanner file){
        ArrayList<Investment> investments = new ArrayList<Investment>();
        String inputLine;
        String []splitInput;

        String investType;
        String investSymbol;
        String investName;
        String investNum;
        int investQuantity;
        double investPrice;
        double investBookValue;
        
        while(file.hasNextLine()){
            inputLine = file.nextLine();
            splitInput = inputLine.split("\"");
            investType = splitInput[1];

            inputLine = file.nextLine();
            splitInput = inputLine.split("\"");
            investSymbol = splitInput[1];

            inputLine = file.nextLine();
            splitInput = inputLine.split("\"");
            investName = splitInput[1];

            inputLine = file.nextLine();
            splitInput = inputLine.split("\"");
            investNum = splitInput[1];
            investQuantity = Integer.parseInt(investNum);

            inputLine = file.nextLine();
            splitInput = inputLine.split("\"");
            investNum = splitInput[1];
            investPrice = Double.parseDouble(investNum);

            inputLine = file.nextLine();
            splitInput = inputLine.split("\"");
            investNum = splitInput[1];
            investBookValue = Double.parseDouble(investNum);

            if(file.hasNextLine()){
                file.nextLine();
            }

            if(investType.equalsIgnoreCase("stock")){
                Stock stock = new Stock(investSymbol, investName, investQuantity, investPrice, investBookValue);
                investments.add(stock);
            }
            else{
                MutualFund mutuals = new MutualFund(investSymbol, investName, investQuantity, investPrice, investBookValue);
                investments.add(mutuals);
            }
        }

        System.out.println("File loaded succesfully...");
        return investments;
    }

    private static void writeToFile(ArrayList<Investment> investments, FileWriter fileWrite){
        String investType;
        
        for(int i = 0; i < investments.size(); i++){
            if(investments.get(i) instanceof Stock){
                investType = "stock";
            }
            else{
                investType = "mutualfund";
            }
            try {
                fileWrite.write("type = \"" + investType + "\"\nsymbol = \""
                + investments.get(i).getSymbol() + "\"\nname = \"" + investments.get(i).getName()
                + "\"\nquantity = \"" + investments.get(i).getQuantity() + "\"\nprice = \""
                + investments.get(i).getPrice()+  "\"\nbookValue = \"" + investments.get(i).getBookValue()
                + "\"\n\n");
            } catch (IOException e) {
                System.out.println("Could not write...");
                break;
            }
        }
    }

    private static HashMap<String, ArrayList<Integer>> makeHash(ArrayList<Investment> investments){
        HashMap<String, ArrayList<Integer>> words = new HashMap<String, ArrayList<Integer>>();
        String []splitWords;

        // Loop through each investment
        for(int i = 0; i < investments.size(); i++){
           
            String [] secondSplit;
            splitWords = investments.get(i).getName().split(" ");

            // Get each word
            for(int j = 0; j < splitWords.length; j++){
                ArrayList<Integer> index = new ArrayList<Integer>();
                if(words.containsKey(splitWords[j].toLowerCase())){
                    index = words.get(splitWords[j].toLowerCase());
                    // Get investment to compare
                    for(int k = 0; k < investments.size(); k++){
                        secondSplit = investments.get(k).getName().split(" ");
                        for(int l = 0; l < secondSplit.length; l++){
                            // If word is in both add index
                            if(splitWords[j].equalsIgnoreCase(secondSplit[l])){
                                if(!index.contains(k)){
                                    index.add(k);
                                }
                            }
                            
                        }
                    }
                }

                // If its not in the list, add it to the list with a new index
                else{
                    if(!index.contains(i)){
                        index.clear();
                        index.add(i);
                    }
                   
                }

                words.put(splitWords[j].toLowerCase(), index);
            }
            
        } // End of Hash Map initialization

        return words;
    }

    private static double verifyDoubleInput(String userInput){
        double returnVal;
        try {
            returnVal = Double.parseDouble(userInput);
        } catch (Exception e) {
            returnVal = 0;
        }
        
        return returnVal;
    }

    private static int verifyIntInput(String userInput){
        int returnVal;
        try {
            returnVal = Integer.parseInt(userInput);
        } catch (Exception e) {
            returnVal = 0;
        }
        
        return returnVal;
    }
}

    


