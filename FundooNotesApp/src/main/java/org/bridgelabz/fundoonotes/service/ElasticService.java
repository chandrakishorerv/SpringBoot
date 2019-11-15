package org.bridgelabz.fundoonotes.service;

import java.util.List;

import org.bridgelabz.fundoonotes.dto.NoteDTO;
import org.bridgelabz.fundoonotes.model.Note;

public interface ElasticService {

	List<Note> search(String keyword, String field);

	void save(Note note);

	void update(int noteId, Note modifynote);

	void deleteNote(Note modifynote);

}
