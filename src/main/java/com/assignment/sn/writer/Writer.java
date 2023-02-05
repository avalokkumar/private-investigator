package com.assignment.sn.writer;

import org.springframework.stereotype.Component;

public interface Writer {
    void write(String outputFilePath, String input);
}
