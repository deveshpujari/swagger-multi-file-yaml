package com.swagger.yaml;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    String projectDir = System.getProperty("user.dir");

    @Test
    public void testThatYamlFileIsGenerated() {
        //get the source file path ( absolute file path)
        String sourceIndexFile = getAbsSourceFile();
        //get the target file name ( absolute file path)
        String destYamlFile = getAbsDestYamlFile();

        Assert.assertNotNull(sourceIndexFile);
        Assert.assertNotNull(destYamlFile);

        String[] files = new String[]{sourceIndexFile, destYamlFile};
        //call the merger.
        Main.main(files);

        //check that destFile exists
        Boolean exists = Files.exists(FileSystems.getDefault().getPath(destYamlFile));
        Assert.assertTrue(exists);

    }

    String getAbsSourceFile() {
        //return projectDir + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "index.yaml";
        Path path = FileSystems.getDefault().getPath(projectDir, "src", "test", "resources", "swagger" , "index.yaml");
        return path.toFile().getAbsolutePath();

    }

    String getAbsDestYamlFile() {
        return projectDir + File.separator + "target" + File.separator + "generated-sources" + File.separator + "genapi.yaml";
    }



}
