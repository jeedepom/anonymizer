/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jeedepom.anonymizer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Someone
 */
public class DirectoryRenamer extends SimpleFileVisitor<Path> {
    
    private final Map<String, String> properties;

    public DirectoryRenamer(Map<String, String> properties){
        this.properties=properties;
    }
    
    @Override
    public FileVisitResult visitFile(Path file,
            BasicFileAttributes attr) {
        if (attr.isSymbolicLink()) {
            System.out.format("Symbolic link: %s ", file);
        } else if (attr.isRegularFile()) {
            System.out.format("Regular file: %s ", file);
        } else {
            System.out.format("Other: %s ", file);
        }
        System.out.println("(" + attr.size() + "bytes)");
        return FileVisitResult.CONTINUE;
    }

    // Print each directory visited.
    @Override
    public FileVisitResult postVisitDirectory(Path dir,
            IOException exc) {
        System.out.format("Directory: %s%n", dir);
        
        String newName=properties.get(dir.getFileName().toString());
        if(StringUtils.isNotEmpty(newName)){
            try {
                Files.move(dir, dir.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(DirectoryRenamer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return FileVisitResult.CONTINUE;
    }

    // If there is some error accessing
    // the file, let the user know.
    // If you don't override this method
    // and an error occurs, an IOException 
    // is thrown.
    @Override
    public FileVisitResult visitFileFailed(Path file,
            IOException exc) {
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
    }
}
