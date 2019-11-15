package org.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.bridgelabz.fundoonotes.Utility.TokenImpl;
import org.bridgelabz.fundoonotes.configuration.UserConfiguration;
import org.bridgelabz.fundoonotes.dto.LabelDTO;
import org.bridgelabz.fundoonotes.model.Label;
import org.bridgelabz.fundoonotes.model.Note;
import org.bridgelabz.fundoonotes.repository.LabelRepository;
import org.bridgelabz.fundoonotes.repository.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LabelServiceImpl implements LabelService {

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private UserConfiguration userConfiguration;

	@Autowired
	private NoteRepository noteRepository;
	
	@Override
	public boolean lableCreation(LabelDTO newLabel, String token) {

		ModelMapper modelMapper = userConfiguration.getModelMapper();
		Label labelmodel = modelMapper.map(newLabel, Label.class);
		labelmodel.setUserId(TokenImpl.parseJWT(token));
		
		labelRepository.save(labelmodel);

		return false;
	}
	@Transactional
	 @Override
	public void createNoteLabel(int labelid, int noteId) {
//		 ModelMapper modelMapper = userConfiguration.getModelMapper();
//			Label labelmodel = modelMapper.map(labelDto, Label.class);
	//if (userRepository.isValidUser(id)) {
	List<Note> note = noteRepository.getNotesBasedId(noteId);
	System.out.println(note.size()  +"         note size ");
	Note notes = note.get(0);
	log.info("ok note list is retrived");
	List<Label> labels = labelRepository.getLabelsBasedId(labelid);
	Label label=labels.get(0);
	label.getListOfNotes().add(notes);
	
//     notes.setListOfLabels(labels);
//	label.addNote(notes);
	log.info("entered into save label    1 " );
	labelRepository.saveNoteLabels(notes);
	log.info("entered into save label   2" );
	//} 
	}

	@Override
	public List<Label> getAllLables(String token) {
		log.info("entered into label service");
		return labelRepository.getData(TokenImpl.parseJWT(token));
		
	}
	@Transactional
	@Override
	public void lableUpdation(Label labelobject, String token) {
		labelRepository.updateData(labelobject);
		
	}

	@Override
	@Transactional
	public void lableDeletion(Label labelobject, String token) {
		labelRepository.deleteData(labelobject);
		
	}

	
	@Override
	@Transactional
	public Set<Note> getAllNotesOnLables(int labelid) {
		List<Label> labelslist= labelRepository.getLabelsBasedId(labelid);
		
		Set<Note>  notes=  labelslist.get(0).getListOfNotes();
		return notes;
	}

}
