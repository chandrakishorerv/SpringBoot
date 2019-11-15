package org.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Set;

import org.bridgelabz.fundoonotes.dto.LabelDTO;
import org.bridgelabz.fundoonotes.model.Label;
import org.bridgelabz.fundoonotes.model.Note;

public interface LabelService {

	boolean lableCreation(LabelDTO newLabel, String token);

	void createNoteLabel(int labelid, int noteId);

	List<Label> getAllLables(String token);

	void lableUpdation(Label labelobject, String token);

	void lableDeletion(Label labelobject, String token);

	Set<Note> getAllNotesOnLables(int labelid);

}
