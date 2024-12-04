package com.example.splitwise;

import com.example.splitwise.CommandExecutor.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@SpringBootApplication
@EnableJpaAuditing
public class SplitWiseApplication implements CommandLineRunner {

	private final Scanner scanner = new Scanner(System.in);
	private final CommandExecutor commandExecutor;

	@Autowired
	public SplitWiseApplication(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	public static void main(String[] args) {
		SpringApplication.run(SplitWiseApplication.class, args);
		System.out.println("Application started");
	}

	@Override
	public void run(String... args) throws Exception {
		while (true) {
			String input = scanner.nextLine();
			commandExecutor.execute(input);
		}

	}
}
