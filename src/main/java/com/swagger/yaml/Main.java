package com.swagger.yaml;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dpujari on 11/11/16.
 */
public class Main {

    /**
     * This will resolve all the remote references and merge all yaml file to a single file.
     * arguments to this file are :
     * - absolute source yaml file name.
     * - absolute destination file name
     *
     * @param args
     */
    public static void main(String[] args) {

        if(args.length != 2) {
            throw new IllegalArgumentException("invalid arguments passed: -"+ args.toString());
        }

        String sourceFile = args[0];
        String destinationFile = args[1];

        if( sourceFile == null || StringUtils.isBlank(sourceFile)) {
            throw new IllegalArgumentException("Source file is null or blank");
        }

        if( destinationFile == null || StringUtils.isBlank(destinationFile)) {
            throw new IllegalArgumentException("Destination file is null or blank");
        }

        Path sourceFilePath = Paths.get(sourceFile);
        if(! Files.exists(sourceFilePath)) {
            throw new IllegalArgumentException("sourcefile does not exists :  " +  sourceFile);
        }

        Path destFilePath = Paths.get(destinationFile);

        MergeYaml mergeYaml = new MergeYaml(sourceFilePath, destFilePath);
        try {
            mergeYaml.merge();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
