package com.pm.authservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Scanner;

public class PasswordHasher {

    public static String hashPassword(String rawPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

    public static void main(String[] args) {
        // Option 1: Hardcoded password
        // String rawPassword = "test123";

        // Option 2: Read from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter raw password: ");
        String rawPassword = scanner.nextLine();

        // Generate the hash
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        // Print it
        System.out.println("BCrypt Hashed Password: " + hashedPassword);

        scanner.close();
    }
}
