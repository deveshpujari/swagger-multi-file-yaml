package com.swagger.yaml;

import com.swagger.yaml.utils.RefUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dpujari on 11/11/16.
 */
public class MergeYaml {

    Path sourceFilePath;
    Path destFilePath;

    public MergeYaml() {
    }

    public MergeYaml(final Path sourceFile, Path destinationFile) {
        this.sourceFilePath = sourceFile;
        this.destFilePath = destinationFile;
    }

    /**
     * Merge the yaml files starting with sourceFilePath
     *
     * @return
     */
    public void merge() throws IOException {
        //check if dest file exists

        Path destParentDir = destFilePath.getParent();
        //create missing directories
        Files.createDirectories(destParentDir);

        Files.deleteIfExists(destFilePath);
        Files.createFile(destFilePath);

        Path parentDir = sourceFilePath.getParent();
        FileWriter fw = new FileWriter(destFilePath.toFile());
        resolve(parentDir, sourceFilePath, fw, "");
        fw.close();

    }

    /**
     * start reading the source file line by line
     * if line starts with $ref.
     * get the parentDir and file name
     * call resolve()
     * else
     * write to dest file.
     *
     * @param parentDir
     * @param sourceFilePath
     */
    private void resolve(Path parentDir, Path sourceFilePath, final FileWriter fw, String indentation) throws IOException {
        Files.lines(sourceFilePath).forEach(line -> {
            //String indent="";
            if (RefUtil.isStartWithRef(line) && RefUtil.isRemoteFilepath(line)) {
                String indent = indentation + RefUtil.getIndentation(line);

                //get the abs file path
                String remoteRefValue = RefUtil.getRemoteRelativeFilePath(line);
                Path remoteFilePath = getAbsolutePath(parentDir, remoteRefValue);
                //get the new parent Directory
                Path newParentDir = remoteFilePath.getParent();
                try {
                    resolve(newParentDir, remoteFilePath, fw, indent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    //fw.write(line, 0, line.length());
                    fw.write(String.format("%s%s%n", indentation, line));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * this method returns the absolute path for the remote value.
      * @param parentDir
     * @param remoteRefValue
     * @return
     */
    private Path getAbsolutePath(Path parentDir, String remoteRefValue ) {
        String absFileStr = parentDir.toString() + remoteRefValue;
        return Paths.get(absFileStr);
    }



}
