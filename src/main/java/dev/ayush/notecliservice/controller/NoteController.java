package dev.ayush.notecliservice.controller;

import dev.ayush.notecliservice.model.Note;
import dev.ayush.notecliservice.persistence.FileService;
import dev.ayush.notecliservice.service.NoteService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class NoteController {
    private static final String FILE_PATH = "data/notes.csv";

    NoteService noteService;
    FileService fileService;

    public NoteController(NoteService noteService, FileService fileService) {
        this.noteService = noteService;
        this.fileService = fileService;
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        ;
        while (true) {
            System.out.println("Enter 'help' to get command list.");
            System.out.print("Enter command (e.g., GET /notes): ");
            String input = scanner.nextLine().trim();
            handleInput(input, noteService, scanner, fileService);
        }
    }

    private void handleInput(String input, NoteService noteService, Scanner scanner, FileService fileService) throws IOException {
        String[] parts = input.split(" ");
        String command = parts[0].toUpperCase();
        switch (command) {
            case "HELP" -> {
                printHelp();
            }
            case "GET" -> {
                List<Note> notes = noteService.listNotes();
                for (Note note : notes) {
                    System.out.println(note);
                }
            }
            case "POST" -> {
                System.out.print("Title: ");
                String title = getValidStringInput(scanner);
                System.out.println("Content: ");
                String content = getValidStringInput(scanner);
                Note note = noteService.createNote(title, content);
                System.out.println("Note created with ID: " + note.getId());
            }
            case "PUT" -> {
                int id = Integer.parseInt(parts[1].split("/")[2]);
                System.out.print("New Title: ");
                String newTitle = getValidStringInput(scanner);
                System.out.println("New Content: ");
                String newContent = getValidStringInput(scanner);
                noteService.updateNote(id, newTitle, newContent);
                System.out.println("Note updated.");
            }
            case "DELETE" -> {
                int id = Integer.parseInt(parts[1].split("/")[2]);
                noteService.deleteNote(id);
                System.out.println("Note deleted successfully.");
            }
            case "EXIT" -> {
                System.out.println("Goodbye.");
                fileService.saveNotes(FILE_PATH, noteService.listNotes());
                System.exit(0);
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                POST /notes
                GET /notes
                PUT /notes/{id}
                DELETE /notes/{id}
                EXIT
                """);
    }

    private String getValidStringInput(Scanner scanner) {
        String input = "";
        input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println("Field can't be empty. Try again.");
            input = scanner.nextLine().trim();
        }
        return input;
    }
}
