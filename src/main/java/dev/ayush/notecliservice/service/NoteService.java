package dev.ayush.notecliservice.service;

import dev.ayush.notecliservice.model.Note;
import dev.ayush.notecliservice.persistence.FileService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NoteService {
    private static final String FILE_PATH = "data/notes.csv";

    private final AtomicInteger COUNTER = new AtomicInteger(1);
    private final List<Note> notes;

    public NoteService(FileService fileService) throws FileNotFoundException {
        this.notes = new ArrayList<>(fileService.loadNotes(FILE_PATH));
    }

    public Note createNote(String title, String content) {
        int id = COUNTER.getAndIncrement();
        Note newNote = new Note(id, LocalDateTime.now(), title, content, LocalDateTime.now());
        notes.add(newNote);
        return newNote;
    }

    public List<Note> listNotes() {
        return Collections.unmodifiableList(notes);
    }

    public void updateNote(int id, String newTitle, String newContent) {
        Note note = getNoteWithId(id);
        note.setTitle(newTitle);
        note.setContent(newContent);
    }

    public boolean deleteNote(int id) {
        Note note = getNoteWithId(id);
        return (notes.remove(note));
    }

    // TODO: throw custom exception instead of null
    private Note getNoteWithId(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

}
