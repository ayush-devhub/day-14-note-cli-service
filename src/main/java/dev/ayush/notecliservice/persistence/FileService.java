package dev.ayush.notecliservice.persistence;

import dev.ayush.notecliservice.model.Note;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    // TODO: validate filePath
    // TODO: validate notes list
    // TODO:
    public void saveNotes(String filePath, List<Note> notes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Note note : notes) {
                writer.write(note.getId() + "," + "," + note.getCreatedAt() + "," + note.getTitle() + "," + note.getContent() + "," + note.getUpdatedAt());
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found at: " + filePath);
        }
    }

    public List<Note> loadNotes(String filePath) throws FileNotFoundException {
        List<Note> notes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Note newNote = parseLine(line);
                notes.add(newNote);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found at: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return notes;
    }

    private Note parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new IllegalStateException("Invalid number of fields in line: " + line);
        }
        int id = Integer.parseInt(parts[0]);
        LocalDateTime createdAt = LocalDateTime.parse(parts[1]);
        String title = parts[2];
        String content = parts[3];
        LocalDateTime updatedAt = LocalDateTime.parse(parts[4]);
        return new Note(id, createdAt, title, content, updatedAt);
    }
}
