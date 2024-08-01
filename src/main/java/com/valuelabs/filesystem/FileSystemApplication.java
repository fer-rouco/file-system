package com.valuelabs.filesystem;

import com.valuelabs.filesystem.model.BaseFileSystemModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.TreeMap;

@SpringBootApplication
public class FileSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileSystemApplication.class, args);
	}

	@Bean
	public Map<String, BaseFileSystemModel> inMemoryFileSystem() {
		return new TreeMap<>();
	}

}
