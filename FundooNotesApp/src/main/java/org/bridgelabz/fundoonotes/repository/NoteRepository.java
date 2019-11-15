package org.bridgelabz.fundoonotes.repository;

import java.util.ArrayList;
import java.util.List;

import org.bridgelabz.fundoonotes.model.Note;

public interface NoteRepository {

	void createNote(Note notemodel);

	void deleteNote(Note notemodel);

	List<Note> getNote(int noteid);

	List<Note> getAllNotes(int userid);

	void updateNote( Note notemodel);

	void pinNote(int noteId, boolean ispinned);

	List<Note> getNoteId(String title);

	List<Note> getTashNotes(int id);

	void unTashNotes(int noteId);

	Note getNoteById(Note modifynote);

	List<Note> getArchiveNotes(int id);

	List<Note> getNotesBasedId(int noteId);

	List<Note> getAllTheNote(int id);

	


}
