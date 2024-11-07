import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A program to analyze a list of countries by various criteria, writing the results to a file.
 * The program reads from a source file, performs operations like filtering and sorting,
 * and then writes the results to an output file.
 */
public class CountryLab {

    /**
     * The main method reads from an input file, performs various operations on country names,
     * and writes results to an output file.
     *
     * @param args command-line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(final String[] args) throws IOException {

        final Path textPath;
        final Path data;
        final String directoryName;
        final Path dirPath;
        final int MAX_TEN_CHARS;
        final int FIVE_CHAR_LENGTH;
        final int THREE_CHAR_LENGTH;
        final String united;
        final String letterA;
        final String letterZ;
        final String land;
        final int startIndex;

        letterA = "A";
        letterZ = "Z";
        land = "land";
        startIndex = 0;
        THREE_CHAR_LENGTH = 3;
        MAX_TEN_CHARS = 10;
        FIVE_CHAR_LENGTH = 5;
        united = "United";
        directoryName = "matches";
        textPath = Paths.get("src/week8countries.txt");
        data = Paths.get("src/data.txt");
        Path target = Paths.get("src/matches");

        // Check if the "matches" directory exists; if not, create it and move it to the target location
        try
        {
            dirPath = Paths.get(directoryName);
            if(Files.notExists(dirPath))
            {
                Files.createDirectory(dirPath);
                Files.move(dirPath, target);
            }
        }
        catch(final Exception e)
        {
            System.out.println("An error occurred while checking or creating the directory: " + e.getMessage());
            e.printStackTrace();
        }

        List<String> countries;

        countries = Files.readAllLines(textPath);

        // Filter and write countries with names longer than 10 characters
        Files.writeString(data, "Country names longer than 10 characters: \n", StandardOpenOption.APPEND);
        List<String> countriesLengthOverTen;
        countriesLengthOverTen = countries.stream()
                .filter(word -> word != null)
                .filter(word -> word.length() > MAX_TEN_CHARS)
                .toList();
        Files.write(data, countriesLengthOverTen, StandardOpenOption.APPEND);

        // Filter and write countries with names shorter than 5 characters
        Files.writeString(data, "\nCountry names shorter than 5 characters: \n", StandardOpenOption.APPEND);
        List<String> countriesLengthLessThanFive;
        countriesLengthLessThanFive = countries.stream()
                .filter(word -> word != null)
                .filter(word -> word.length() < FIVE_CHAR_LENGTH)
                .toList();
        Files.write(data, countriesLengthLessThanFive, StandardOpenOption.APPEND);

        // Filter and write country names that start with "A"
        Files.writeString(data, "\nCountry names that start with \"A\": \n", StandardOpenOption.APPEND);
        List<String> wordsWithA;
        wordsWithA = countries.stream()
                .filter(word -> word != null)
                .filter(word -> word.startsWith(letterA))
                .toList();
        Files.write(data, wordsWithA, StandardOpenOption.APPEND);

        // Filter and write country names that end with "land"
        Files.writeString(data, "\nCountry names that end with \"land\": \n", StandardOpenOption.APPEND);
        List<String> endsWithLand;
        endsWithLand = countries.stream()
                .filter(word -> word != null)
                .filter(word -> word.endsWith(land))
                .toList();
        Files.write(data, endsWithLand, StandardOpenOption.APPEND);


        // Filter and write country names that contain "United"
        Files.writeString(data, "\nCountry names that contain \"United\": \n", StandardOpenOption.APPEND);
        List<String> containsUnited;
        containsUnited = countries.stream()
                .filter(word -> word != null)
                .filter(word -> word.contains(united))
                .toList();
        Files.write(data, containsUnited, StandardOpenOption.APPEND);

        // Sort and write country names in alphabetical order
        Files.writeString(data, "\nCountry names in alphabetical order: \n", StandardOpenOption.APPEND);
        List<String> alpha;
        alpha = countries.stream()
                .filter(word -> word != null)
                .sorted(Comparator.comparing(String::toString))
                .toList();
        Files.write(data, alpha, StandardOpenOption.APPEND);

        // Sort and write country names in descending alphabetical order
        Files.writeString(data, "\nCountry names in descending alphabetical order: \n", StandardOpenOption.APPEND);
        List<String> beta;
        beta = countries.stream()
                .filter(word -> word != null)
                .sorted(Comparator.comparing(String::toString).reversed())
                .toList();
        Files.write(data, beta, StandardOpenOption.APPEND);

        // List unique first letters of all country names
        Files.writeString(data, "\nThe following characters appear as the first letter of country names: \n", StandardOpenOption.APPEND);
        List<String> firstChar;
        firstChar = countries.stream()
                .map(str -> String.valueOf(str.charAt(startIndex)))
                .distinct()
                .toList();
        Files.write(data, firstChar, StandardOpenOption.APPEND);

        // Write the total count of countries
        Files.writeString(data, "\nTotal amount of countries in week8countries.txt: ", StandardOpenOption.APPEND);
        Files.write(data, String.valueOf(countries.size()).getBytes(), StandardOpenOption.APPEND);

        // Find and write the country with the longest name
        Files.writeString(data, "\nThe country with the longest name is: \n", StandardOpenOption.APPEND);
        String longestCountry;
        longestCountry = countries.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        Files.writeString(data, longestCountry, StandardOpenOption.APPEND);

        // Find and write the country with the shortest name
        Files.writeString(data, "\nThe country with the shortest name is: \n", StandardOpenOption.APPEND);
        String shortestCountry;
        shortestCountry = countries.stream()
                .min(Comparator.comparingInt(String::length))
                .orElse("");
        Files.writeString(data, shortestCountry, StandardOpenOption.APPEND);

        // Write country names in uppercase
        Files.writeString(data, "\nCountry names in uppercase: \n", StandardOpenOption.APPEND);
        countries.stream().map(String::toUpperCase).forEach(country -> {
            try {
                Files.writeString(data, country + "\n", StandardOpenOption.APPEND);
            }
            catch(final IOException e)
            {
                throw new RuntimeException(e);
            }
        });

        // Filter and write country names with more than one word
        Files.writeString(data, "\nCountry names with more than one word: \n", StandardOpenOption.APPEND);
        List<String> moreThanOneWord;
        moreThanOneWord = countries.stream()
                .filter(word -> word.contains(" "))
                .peek(p -> System.out.println("peeking at " + p))
                .toList();
        Files.write(data, moreThanOneWord, StandardOpenOption.APPEND);

        // Write country names along with character count
        Files.writeString(data, "\nCountry names with their character counts: \n", StandardOpenOption.APPEND);
        List<String> charCount;
        charCount = countries.stream()
                .filter(word -> !word.trim().isEmpty())
                .map(word -> word + ": " + word.length())
                .toList();
        Files.write(data, charCount, StandardOpenOption.APPEND);

        // Check if any country name starts with "Z"
        Files.writeString(data, "\nDo any countries start with \"Z\":", StandardOpenOption.APPEND);
        boolean startsWithZ;
        startsWithZ = countries.stream().filter(p -> p != null).anyMatch(word -> word.startsWith(letterZ));
        Files.writeString(data, " " + startsWithZ, StandardOpenOption.APPEND);

        // Check for country names are longer than 3 characters
        Files.writeString(data, "\nAre all country names longer than 3 characters:", StandardOpenOption.APPEND);
        boolean longerThan3;
        longerThan3 = countries.stream().filter(p -> p != null).allMatch(word -> word.length() > THREE_CHAR_LENGTH);
        Files.writeString(data, " " + longerThan3, StandardOpenOption.APPEND);
    }
}
