package org.bridgelabz.fundoonotes.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.bridgelabz.fundoonotes.Utility.TokenImpl;
import org.bridgelabz.fundoonotes.configuration.UserConfiguration;
import org.bridgelabz.fundoonotes.dto.NoteDTO;
import org.bridgelabz.fundoonotes.model.Label;
import org.bridgelabz.fundoonotes.model.Note;
import org.bridgelabz.fundoonotes.model.User;
import org.bridgelabz.fundoonotes.repository.LabelRepository;
import org.bridgelabz.fundoonotes.repository.NoteRepository;
import org.bridgelabz.fundoonotes.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class NoteServiceImpl implements NoteService {
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConfiguration userConfiguration;
	
//	 @Autowired
//	    private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private ElasticService elasticService;
	
	@Autowired
	private LabelRepository labelRepository;

	@Override
	@Transactional
	public boolean noteCreation(NoteDTO note, String token) {
		
//		redistemplate.opsForValue().set(token, note.getTitle());
		ModelMapper modelMapper = userConfiguration.getModelMapper();
		Note notemodel = modelMapper.map(note, Note.class);
		notemodel.setUserId(TokenImpl.parseJWT(token));
		notemodel.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		noteRepository.createNote(notemodel);
		List<Note> notes=noteRepository.getNoteId(notemodel.getTitle());
		System.out.println(notes.toString());
		Note noteobject=notes.get(0);
		elasticService.save(noteobject);
		return true;
	}

	

	@Override
	@Transactional
	public boolean noteUpdation(int noteId,Note modifynote, String token) {
		modifynote.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		log.info("at service of note updation "+modifynote.toString());
		noteRepository.updateNote(modifynote);
//		elasticService.update(noteId,modifynote);
		return false;
	}

	@Override
	@Transactional
	public List<Note> getAllNotes(String token) {
		
		
	
		//List<String> list = convertSetToList(hash_Set);
	int id = TokenImpl.parseJWT(token);
	List<User> users=userRepository.getListOfUsers();
	log.info(users.toString());
	users = users.stream().filter(user ->user.getUserId()==id).collect(Collectors.toList());
//Set<Note> userlistofnotes= users.get(0).getListofnotes();
List<Note> listofnotes=noteRepository.getAllNotes(TokenImpl.parseJWT(token));
//log.info(userlistofnotes.size()+"   value");
//for (Note t : userlistofnotes) 
//	listofnotes.add(t); 
	
//if(userlistofnotes.size()>0) {
//	
//for(int i=0;i<userlistofnotes.size();i++) {
//	log.info(userlistofnotes.get(i)+"   value");
//	listofnotes.add(userlistofnotes.get(i));
//}}
		log.info("id at "+TokenImpl.parseJWT(token));
		return listofnotes;
	}

	@Override
	@Transactional
	public List<Note> getAllNotes(int noteId) {
		return noteRepository.getNote(noteId);
	}

	@Override
	@Transactional
	public void pinNotes(int noteId, boolean ispinned) {
		
	log.info("entered into service :is pinned   "+ispinned);
		noteRepository.pinNote(noteId,ispinned);		
	}

	@Override
	@Transactional
	public List<Note> sortNoteByDate(String token) {
		List<Note> noteList = noteRepository.getAllNotes(TokenImpl.parseJWT(token));
		return  noteList.stream().sorted((e1, e2) -> e2.getCreatedOn().compareTo(e1.getCreatedOn())).collect(Collectors.toList());
	}	
 
	@Override
	@Transactional
	public List<Note> sortNoteByName(String token) {
		List<Note> noteList = noteRepository.getAllNotes(TokenImpl.parseJWT(token));
		return noteList.stream().sorted((e1, e2) -> e2.getTitle().compareToIgnoreCase(e1.getTitle())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteNote(Note modifynote) {
		Note note =noteRepository.getNoteById(modifynote);
		if(note!=null) {
			note.setInTrash(!note.isInTrash());
			noteRepository.updateNote(note);
		}
		elasticService.deleteNote(modifynote);
	}

	@Override
	@Transactional
	public List<Note> searchNotes(String token, String keyword, String field) {
		int id = TokenImpl.parseJWT(token);
		List<Note> notes= elasticService.search(keyword, field);
		return notes;
	}

	@Override
	@Transactional
	public List<Note> getTashNotes(String token) {
		int id = TokenImpl.parseJWT(token);
		return noteRepository.getTashNotes(id);
	}

	@Override
	@Transactional
	public void unTashNotes(Note modifynote) {
		int noteId=modifynote.getNoteId();
		modifynote.setInTrash(false);
		noteRepository.updateNote(modifynote);
		
	}

	@Override
	@Transactional
	public void archiveNote(Note note) {
		note.setArchive(!note.isArchive());
		noteRepository.updateNote(note);
		
	}

	@Override
	@Transactional
	public List<Note> getArchiveNotes(String token) {
		int id = TokenImpl.parseJWT(token);
		return noteRepository.getArchiveNotes(id);
	}



	@Override
	@Transactional
	public void updateReminder(Note modifynote) {
		
		noteRepository.updateNote(modifynote);
	}



	@Override
	@Transactional
	public List<Note> getReminderNotes(String token) {
		List<Note> notesList=	noteRepository.getAllNotes(TokenImpl.parseJWT(token));
		notesList = notesList.stream().filter(notecheck -> notecheck.getRemainder() != null)
				.collect(Collectors.toList());
		return notesList;
	}



	@Override
	@Transactional
	public List<Note> getAlltheNotes(String token) {
		int id = TokenImpl.parseJWT(token);
		return noteRepository.getAllTheNote(id);
	}



	@Override
	@Transactional
	public void CollabarateNote(int noteId, String email) {
//		int id = TokenImpl.parseJWT(token);
		List<User> users=userRepository.getListOfUsers();
		users = users.stream().filter(user ->user.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList());
		log.info("user add "+users.toString());
		Note note=noteRepository.getNote(noteId).get(0);
		log.info("note add "+note.toString());
		note.getListOfusers().add(users.get(0));
	}



	@Override
	@Transactional
	public Set<User> getCollabaratedUsers(int noteId) {
		Note note=noteRepository.getNote(noteId).get(0);
		log.info("note add "+note.toString());
	return	note.getListOfusers();
	}



	@Override
	@Transactional
	public void deleteNoteOnLabel(int noteId, int labelid) {
		Note note=noteRepository.getNote(noteId).get(0);
		List<Label> labels = labelRepository.getLabelsBasedId(labelid);
		Label label=labels.get(0);
		label.getListOfNotes().remove(note);
	}



	@Override
	@Transactional
	public void deleteCollabaratedNote(int noteId, String email) {
		Note note=noteRepository.getNote(noteId).get(0);
List<User> users=userRepository.getListOfUsers();
		users = users.stream().filter(user ->user.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList());
		note.getListOfusers().remove(users.get(0));
	}
	
	

}
