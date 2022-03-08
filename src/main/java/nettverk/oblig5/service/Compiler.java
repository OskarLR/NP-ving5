package nettverk.oblig5.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@Service
public class Compiler {

    public ResponseEntity<Object> compiler() throws Exception {

        return null;
    }

    public String compile(Map<String, String> code) throws IOException, InterruptedException {

        FileService fileService = new FileService();

        File myFile = new File("src/docker/temp/code.py");

        fileService.writeToFile(myFile, code.get("code"));

        // BufferedWriter br = new BufferedWriter(new FileWriter(myFile));
        // br.write(code);
        // br.flush();


        // Runtime.getRuntime().exec("docker rmi cpp-compiler").waitFor();
        Process process = Runtime.getRuntime().exec("docker build ./src/docker/ -t python-compiler");
        String buildError = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("builderror: " + buildError);

        Process run = Runtime.getRuntime().exec("docker run --rm python-compiler");

        String runError = new String(run.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        String Output = new String(run.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        System.out.println("output: " + Output);

        if (!runError.isBlank()) {
            return buildError + "\n" + runError;
        }
        return runError + "\n" + Output;
    }
}