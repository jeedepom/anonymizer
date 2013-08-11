/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jeedepom.anonymizer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
            System.out.format("Symbolic link: %s will not be treated", file);
        } else if (attr.isRegularFile()) {
            
            replaceInFile(file);
        } else {
            System.out.format("Other: %s ", file);
        }
        return FileVisitResult.CONTINUE;
    }

    // Print each directory visited.
    @Override
    public FileVisitResult postVisitDirectory(Path dir,
            IOException exc) {
        
        String newName=properties.get(dir.getFileName().toString());
        if(StringUtils.isNotEmpty(newName)){
            try {
                Files.move(dir, dir.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
                System.out.format("Directory's name changed : %s%n", dir);
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

    private boolean replaceInFile(Path path) {
        boolean result=false;
        try {
            Charset charset = StandardCharsets.UTF_8;

            String content = new String(Files.readAllBytes(path), charset);
            for(String key : properties.keySet()){
                if(content.indexOf(key)!=-1){
                    content = content.replaceAll(key, properties.get(key));
                    System.out.format("Text %s replaced in : %s%n", key, path.toString());
                }
            }
            Files.write(path, content.getBytes(charset));
            result=true;
        } catch (IOException ex) {
            Logger.getLogger(DirectoryRenamer.class.getName()).log(Level.SEVERE, "impossible to write text in file "+path.toString() , ex);
        }
        return result;
    }
}
