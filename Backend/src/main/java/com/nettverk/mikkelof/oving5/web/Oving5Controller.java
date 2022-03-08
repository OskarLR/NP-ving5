package com.nettverk.mikkelof.oving5.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;

@RestController
@CrossOrigin
public class Oving5Controller {
    @PostMapping("/compileAndRun")
    public void compileAndRun(@RequestBody String code) throws IOException, InterruptedException {
        FileWriter writer = new FileWriter("../code.py");
        String codeToFile = code.substring(1, code.length() - 1);
        writer.write(codeToFile);
        writer.close();
        Runtime.getRuntime().exec("docker rmi python-boi").waitFor();
        Runtime.getRuntime().exec("docker build -t python-boi ./NP-ving5");
        Runtime.getRuntime().exec("docker run --rm python-boi");
        Runtime.getRuntime().exec("docker cp python-boi:/output.txt ./NP-ving5/output.txt");
    }
}
