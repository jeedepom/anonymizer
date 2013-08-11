package fr.jeedepom.anonymizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    static boolean invertMode=false;
    static String target;
    

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
                formatter.printHelp("anonymizer", "Usage : anonymizer [-i|-h] project-directory ",options, "under GPL3 Licence. Report issue at www.jeedepom.fr" );
                System.exit(0);
            }
            if (line.hasOption("i")) {
                // initialise the member variable
                invertMode=true;
            }
            //fetch the directory's name
            List<String> listOfArg = line.getArgList();
            if(listOfArg.size()!=1){
                System.err.println("Command line args parsing failed.  Reason: missing <project-directory> argument");
            }
            target=listOfArg.get(0);
            
            
        } catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Command line args parsing failed.  Reason: " + exp.getMessage());
        }
        

            
        
        
        try {
            AnonymizerConfig anonymizerConfig =  new AnonymizerConfig(target);
        }catch(IOException ioe){
            System.err.println("Loading config failed.  Reason: " + ioe.getMessage());
            System.exit(1);
        }
        try {
            Files.walkFileTree(Paths.get(target), new DirectoryRenamer(AnonymizerConfig.getConfig(invertMode)) );
        } catch (IOException ex) {
            Logger.getLogger(Anonymizer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}