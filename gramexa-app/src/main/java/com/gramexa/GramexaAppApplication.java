package com.gramexa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class GramexaAppApplication {

	public static void main(String[] args) {
		ensureDatabaseExists();
		SpringApplication.run(GramexaAppApplication.class, args);
	}

	private static void ensureDatabaseExists() {
		String databaseUrl = System.getenv().getOrDefault("DATABASE_URL", "jdbc:mysql://localhost:3306/gramexa_db");
		String databaseUser = System.getenv().getOrDefault("DATABASE_USERNAME", "root");
		String databasePassword = System.getenv().getOrDefault("DATABASE_PASSWORD", "root");
		String databaseName = extractDatabaseName(databaseUrl);

		String adminUrl = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		try (Connection connection = DriverManager.getConnection(adminUrl, databaseUser, databasePassword);
			 Statement statement = connection.createStatement()) {
			statement.executeUpdate(
					"CREATE DATABASE IF NOT EXISTS `" + databaseName + "` " +
					"CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"
			);
		} catch (Exception exception) {
			throw new IllegalStateException(
					"Failed to create or verify database '" + databaseName + "' on localhost:3306",
					exception
			);
		}
	}

	private static String extractDatabaseName(String jdbcUrl) {
		try {
			String remainder = jdbcUrl.substring(jdbcUrl.indexOf("3306/") + 5);
			int queryIndex = remainder.indexOf('?');
			if (queryIndex >= 0) {
				remainder = remainder.substring(0, queryIndex);
			}
			if (remainder.isBlank()) {
				return "gramexa_db";
			}
			return remainder;
		} catch (Exception exception) {
			return "gramexa_db";
		}
	}

}
