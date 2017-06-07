package com.orasi.utils.dataProviders;

import static com.orasi.utils.TestReporter.logTrace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.orasi.exception.AutomationException;
import com.orasi.utils.io.FileLoader;

public class CSVDataProvider {
    private static String delimiter = ",";

    /**
     * This gets the test data from a csv file. It returns all the data
     * as a 2d array
     *
     * @param filePath
     *            the file path of the CSV file
     * @version 12/18/2014
     * @author Jessica Marshall
     * @return 2d array of test data
     */
    public static Object[][] getTestScenarioData(String filePath) {
        logTrace("Entering CSVDataProvider#getTestScenarioData");
        BufferedReader bufferedReader = null;
        String line = "";
        String[][] dataArray = null;
        List<String> csvRowList = new ArrayList<String>();
        String[] rowSplit;
        int columnCount = 0;
        int rowCount = 0;
        // open the CSV and add each line into a list
        try {
            bufferedReader = FileLoader.openTextFileFromProject(filePath);
            logTrace("Successfully loaded FileReader object into BufferedReader");

            logTrace("Read in file and load each line into a List");
            while ((line = bufferedReader.readLine()) != null) {
                csvRowList.add(line);
            }
            logTrace("Successfully read in [ " + csvRowList.size() + " ] lines from file");
        } catch (IOException e) {
            throw new AutomationException("Failed to read in CSV file", e);
        } finally {
            try {
                logTrace("Closing BufferedReader");
                bufferedReader.close();
                logTrace("Successfully closed BufferedReader");
            } catch (IOException throwAway) {
            }
        }

        logTrace("Determining column count based on delimiter [ " + delimiter + " ]");
        columnCount = csvRowList.get(0).split(delimiter).length;
        logTrace("Found [ " + columnCount + " ] columns");

        logTrace("Determining row count");
        rowCount = csvRowList.size() - 1;
        logTrace("Found [ " + rowCount + " ] rows");

        logTrace("Attempting to create an array based on rows and columns. Array to built [" + rowCount + "][" + columnCount + "]");
        dataArray = new String[rowCount][columnCount];

        // transform the list into 2d array
        // start at row 1 since, first row 0 is column headings
        logTrace("Transferring data to Array");
        for (int rowNum = 1; rowNum <= rowCount; rowNum++) {

            // take the row which is a string, and split it
            rowSplit = csvRowList.get(rowNum).split(delimiter);

            for (int colNum = 0; colNum < columnCount; colNum++) {
                dataArray[rowNum - 1][colNum] = rowSplit[colNum];
            }
        }

        logTrace("Exiting CSVDataProvider#getTestScenarioData");
        return dataArray;
    }

    public static Object[][] getTestScenarioData(String filePath, String delimiterValue) {
        logTrace("Overriding default delimiter of [ , ] to be [ " + delimiter + " ]");
        delimiter = delimiterValue;
        return getTestScenarioData(filePath);
    }

    public static Object[][] getData(String filePath) {
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        String line = "";
        String[][] dataArray = null;
        List<String> csvRowList = new ArrayList<String>();
        String[] rowSplit;
        int columnCount = 0;
        int rowCount = 0;

        // Get the file location from the project main/resources folder
        if (!filePath.contains(":")) {
            filePath = CSVDataProvider.class.getResource(filePath).getPath();
        }

        // in case file path has a %20 for a whitespace, replace with actual
        // whitespace
        filePath = filePath.replace("%20", " ");

        logTrace("File path of CSV to open [ " + filePath + " ]");
        // open the CSV and add each line into a list
        try {
            logTrace("Loading file into FileReader");
            fileReader = new FileReader(filePath);
            logTrace("Successfully loaded file into FileReader");

            logTrace("Loading FileReader object into BufferedReader");
            bufferedReader = new BufferedReader(fileReader);
            logTrace("Successfully loaded FileReader object into BufferedReader");

            logTrace("Read in file and load each line into a List");
            while ((line = bufferedReader.readLine()) != null) {
                csvRowList.add(line);
            }
            logTrace("Successfully read in [ " + csvRowList.size() + " ] lines from file");
        } catch (IOException e) {
            throw new AutomationException("Failed to read in CSV file", e);
        } finally {
            try {
                logTrace("Closing BufferedReader");
                bufferedReader.close();
                logTrace("Successfully closed BufferedReader");

                logTrace("Closing FileReader");
                fileReader.close();
                logTrace("Successfully closed FileReader");
            } catch (IOException throwAway) {
            }
        }

        logTrace("Determining column count based on delimiter [ " + delimiter + " ]");
        columnCount = csvRowList.get(0).split(delimiter).length;
        logTrace("Found [ " + columnCount + " ] columns");

        logTrace("Determining row count");
        rowCount = csvRowList.size();
        logTrace("Found [ " + rowCount + " ] rows");

        logTrace("Attempting to create an array based on rows and columns. Array to built [" + rowCount + "][" + columnCount + "]");
        dataArray = new String[rowCount][columnCount];

        // transform the list into 2d array
        // start at row 1 since, first row 0 is column headings
        logTrace("Transferring data to Array");
        for (int rowNum = 0; rowNum <= rowCount - 1; rowNum++) {

            // take the row which is a string, and split it
            rowSplit = csvRowList.get(rowNum).split(delimiter);

            for (int colNum = 0; colNum < columnCount; colNum++) {
                dataArray[rowNum][colNum] = rowSplit[colNum];
            }
        }

        logTrace("Exiting CSVDataProvider#getTestScenarioData");
        return dataArray;
    }

    public static Object[][] getData(String filePath, String delimiterValue) {
        logTrace("Overriding default delimiter of [ , ] to be [ " + delimiter + " ]");
        delimiter = delimiterValue;
        return getData(filePath);
    }
}
