package com.pm.authservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Scanner;

public class PasswordHasher {

    public static void main(String[] args) {
        // Create an instance of Spring's BCrypt password encoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Option 1: Hardcoded password
        // String rawPassword = "test123";

        // Option 2: Read from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter raw password: ");
        String rawPassword = scanner.nextLine();

        // Generate the hash
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // Print it
        System.out.println("BCrypt Hashed Password: " + hashedPassword);

        scanner.close();
    }
}
