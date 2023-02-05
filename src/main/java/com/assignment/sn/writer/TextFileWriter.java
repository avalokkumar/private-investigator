package com.assignment.sn.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
public class TextFileWriter implements Writer {

    @Override
    public void write(String outputFilePath, String input) {
        try {
            Files.write(Paths.get(outputFilePath), input.getBytes());
        } catch (IOException e) {
            log.error("Exception while writing to file {}", e.getMessage());
        }
    }
}
