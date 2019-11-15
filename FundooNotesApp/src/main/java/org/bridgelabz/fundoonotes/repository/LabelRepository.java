package org.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.bridgelabz.fundoonotes.model.Label;
import org.bridgelabz.fundoonotes.model.Note;

public interface LabelRepository {

	void save(Label labelmodel);

	void saveNoteLabels(Note notes);

	List<Label> getData(int parseJWT);

	void updateData(Label labelobject);

	void deleteData(Label labelobject);

	List<Integer> getNoteIds(int labelid);
	
	List<Label> getLabelsBasedId(int labelid);

	
}
