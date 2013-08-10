package fr.jeedepom.anonymizer;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class Anonymizer {

    public static void main(String[] args) {

        // create Options object
        Options options = new Options();


        options.addOption("i", false, "invert anonymizer");
        options.addOption("h", false, "print this help");
        // create the parser
        CommandLineParser parser = new BasicParser();
        CommandLine line;
        try {
            // parse the command line arguments
            line = parser.parse(options, args);

            // has the buildfile argument been passed?
            if (line.hasOption("h")) {
                // initialise the member variable
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("anonymizer", options);
            }
        } catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Command line args parsing failed.  Reason: " + exp.getMessage());
        }
    }
}