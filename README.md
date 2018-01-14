
**Swagger-mutlifile-yaml-merger**

Write swagger specification file in multiple files and  merge into one file for code generation. 
(c) Copyright 2018 Devesh Pujari. This code is available under the Apache License, version 2: http://www.apache.org/licenses/LICENSE-2.0.html

**Problem**

Swagger provides a way to define the APIs in yaml file and using this api the skeleton code can be generated. 
This is a single file. This file can become very big as more APIs are added in the spec file by team members. 
This provide a way to distribute the yaml files in different directory and then merge the yaml file into one big yaml file.
Then the generated yaml file can be used to generate the code or load into the swagger editor. 

**Example**

Look at the sample swagger spec files in the directory test/resources/swagger/

The swagger specification main file is  test/resources/swagger/index.yaml .
From this file references are added to different files using JSON references ( $ref).
$ref can point to local file or external file. 
If $ref starts with # then it is local ref. If it starts with ./ then it is external file. 
    
    $ref: "#/definitions/ErrorResponse"  ( This points to the local file )
    $ref: ./info/index.yaml  ( this points to the external file in the info directory)
  
if you run this :
> mvn clean install 

Then the generated yaml spec file will be generated in the path - target/generated-sources/genapi.yaml
Use this file to generated the code or load into the swagger online editor.   
  



**Adding to your project**

Download this code and add the code as a module in your project. 
In your main project module add the below plugin to generate yaml file. 
Add the following properties.
 swagger-src-api-file - Source main index file with full path. 
 swagger-gen-api-file - generated yaml file name with full path
 
       <plugin>
          <groupId>org.codehaus.mojo</groupId>
          <artifactId>exec-maven-plugin</artifactId>
          <executions>
             <execution>
                <id>merge-yaml</id>
                <phase>generate-sources</phase>
                <goals>
                   <goal>java</goal>
                </goals>
                <configuration>
                   <skip />
                   <includePluginDependencies>true</includePluginDependencies>
                   <includeProjectDependencies>false</includeProjectDependencies>
                   <mainClass>com.swagger.yaml.Main</mainClass>
                   <commandlineArgs>${swagger-src-api-file} ${swagger-gen-api-file}</commandlineArgs>
                </configuration>
             </execution>
          </executions>
          <dependencies>
             <dependency>
                <groupId>com.swagger.yaml</groupId>
                <artifactId>multifile-yaml-merger</artifactId>
                <version>1.0-SNAPSHOT</version>
                <scope>compile</scope>
             </dependency>
          </dependencies>
       </plugin>
   
   Once the yaml file is generated then that file can be used to generate the code using the swagger codegen. 
   
   