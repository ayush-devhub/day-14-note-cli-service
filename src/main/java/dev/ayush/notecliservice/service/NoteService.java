package dev.ayush.notecliservice.service;

import dev.ayush.notecliservice.model.Note;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NoteService {
    private final AtomicInteger COUNTER = new AtomicInteger(1);
    private final List<Note> notes = new ArrayList<>();

    public Note createNote(String title, String content) {
        int id = COUNTER.getAndIncrement();
        Note newNote = new Note(id, LocalDateTime.now(), title, content, LocalDateTime.now());
        notes.add(newNote);
        return newNote;
    }

    public List<Note> listNotes() {
        return Collections.unmodifiableList(notes);
    }

    public Note updateNotes(int id, String newTitle, String newContent) {
        Note note = getNoteWithId(id);
        note.setTitle(newTitle);
        note.setContent(newContent);
        return note;
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
