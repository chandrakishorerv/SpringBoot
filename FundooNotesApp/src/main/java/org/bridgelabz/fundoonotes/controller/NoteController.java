package org.bridgelabz.fundoonotes.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.bridgelabz.fundoonotes.dto.NoteDTO;
import org.bridgelabz.fundoonotes.model.Note;
import org.bridgelabz.fundoonotes.model.User;
import org.bridgelabz.fundoonotes.response.Response;
import org.bridgelabz.fundoonotes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/note")
@Slf4j
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class NoteController {
	@Autowired
	private NoteService noteService;
	
	 @Autowired
	    private RedisTemplate<String, String> redisTemplate;

	@PostMapping("/createnote")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO newNote, @RequestHeader("token") String token) {
		log.info("entered into note registration controller");
		boolean isUserPrensent = noteService.noteCreation(newNote, token);
		if (isUserPrensent) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Note created", HttpStatus.CREATED.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Unable to create the Note ", HttpStatus.CREATED.value(), null));
		}
	}

	@PostMapping("/updatenote")
	public ResponseEntity<Response> updateNote(@RequestBody Note modifynote, @RequestHeader("token") String token) {
		log.info("entered into note update controller");
		noteService.noteUpdation(modifynote.getNoteId(), modifynote, token);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note updated", HttpStatus.CREATED.value(), null));
	}

//	@PostMapping("/deletenote/{noteId}")
//	public ResponseEntity<Response> deleteNote(@PathVariable int noteId, @RequestHeader("token") String token) {
//		log.info("entered into note delete controller");
//		noteService.noteDeletion(noteId, token);
//			return ResponseEntity.status(HttpStatus.CREATED)
//					.body(new Response("Note deleted", HttpStatus.CREATED.value(), null));
//	}

	@PostMapping("/getallnote")
	public List<Note> getALLNote(@RequestHeader("token") String token) {
		log.info("entered into  get all notes controller");
		List<Note> listofnotes = noteService.getAllNotes(token);
		if (listofnotes != null) {
			return listofnotes;
		} else {
			log.info("entered into  get null");
			return null;
		}
	}

	@PostMapping("/deletenote")
	public ResponseEntity<Response> deletenote(@RequestBody Note modifynote, @RequestHeader("token") String token) {
		log.info("entered into delete controller");
		noteService.deleteNote(modifynote);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response("Note is sent to bin", HttpStatus.OK.value(), null));
	}

	@GetMapping("/getnotes")
	public List<Note> getNote(@RequestHeader("token") String token) {
		log.info("entered into  get note controller   " + token);
		List<Note> listofnotes =null;
		if(token.equalsIgnoreCase(redisTemplate.opsForValue().get("token"))) {
			log.info("token got from Redis and verified");
		 listofnotes = noteService.getAllNotes(token);
//	Response response1 = new Response("required notes are",200,null,listofnotes);
		System.out.println(listofnotes);
//	return ResponseEntity.status(HttpStatus.CREATED)
//			.body(new Response("Note is pinned", HttpStatus.CREATED.value(), null, response1));
		}
		return listofnotes;
	}

	@GetMapping("/getallthenotes")
	public List<Note> getAllTheNote(@RequestHeader("token") String token) {
		log.info("entered into  get note controller   " + token);
		List<Note> listofnotes = noteService.getAlltheNotes(token);
		if (listofnotes != null) {
			System.out.println(listofnotes.toString());
			return listofnotes;
		} else {
			return null;
		}
	}

	@PostMapping("/pinnote/{noteId}")
	public ResponseEntity<Response> pinNote(@RequestParam("noteId") int noteId,
			@RequestParam("ispinned") boolean ispinned) {
		log.info("entered into  get note controller");
		noteService.pinNotes(noteId, ispinned);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note is pinned", HttpStatus.CREATED.value(), null));
	}

	@PostMapping("/sortingnotebydate")
	public List<Note> sortNoteByDate(@RequestHeader("token") String token) {
		log.info("entered into  get sorting notes on date based,in controller");
		return noteService.sortNoteByDate(token);
	}

	@PostMapping("/sortingnotebyname")
	public List<Note> sortNoteByName(@RequestHeader("token") String token) {
		log.info("entered into  sorting notes on name based,in controller");
		return noteService.sortNoteByName(token);
	}

	@PostMapping("/search")
	public List<Note> searchNotes(@RequestHeader("token") String token, @RequestBody String titleordescription) {
		String title = titleordescription;
		String description = titleordescription;
		return noteService.searchNotes(token, title, description);
	}

	@GetMapping("/trash")
	public List<Note> getTrashNotes(@RequestHeader("token") String token) {
		log.info("entered into trash notes on userid in controller");
		return noteService.getTashNotes(token);
	}

	@PostMapping("/untrash")
	public ResponseEntity<Response> unTrashNotes(@RequestBody Note modifynote, @RequestHeader("token") String token) {
		log.info("entered into untrash notes on userid in  controller");
		noteService.unTashNotes(modifynote);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note created", HttpStatus.CREATED.value(), null));
	}

	@PostMapping("/archivenote")
	public ResponseEntity<Response> archiveNote(@RequestBody Note modifynote, @RequestHeader("token") String token) {
		log.info("entered into archivenote controller");
		noteService.archiveNote(modifynote);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note is Archived", HttpStatus.OK.value(), null));
	}

	@GetMapping("/archive")
	public List<Note> geNotes(@RequestHeader("token") String token) {
		log.info("entered into trash notes on userid in controller");
		return noteService.getArchiveNotes(token);
	}

	@PostMapping("/addcolour")
	public ResponseEntity<Response> addColourToNotes(@RequestParam("colour") String colour,
			@RequestBody Note modifynote, @RequestHeader("token") String token) {
		modifynote.setColour(colour);
		log.info("entered into colour notes on userid in  controller     " + modifynote.getColour());

		noteService.noteUpdation(modifynote.getNoteId(), modifynote, token);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note created", HttpStatus.CREATED.value(), null));
	}

	@PutMapping("/reminder")
	public ResponseEntity<Response> updateReminder(
			@RequestParam("reminder") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminder,
			@RequestBody Note modifynote, @RequestHeader String token) {
		System.out.println(reminder);
		modifynote.setRemainder(reminder);
		noteService.updateReminder(modifynote);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Reminder created", HttpStatus.CREATED.value(), null));
	}

	@GetMapping("/getremindernotes")
	public List<Note> getReminders(@RequestHeader String token) {
		return noteService.getReminderNotes(token);
	}

	@PostMapping("/deleteremainder")
	public ResponseEntity<Response> deleteReminders(@RequestBody Note modifynote) {
		modifynote.setRemainder(null);
		noteService.updateReminder(modifynote);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Reminder updated ", HttpStatus.CREATED.value(), null));
	}

	@PostMapping("/addcollabarator")
	public ResponseEntity<Response> addCollabarator(@RequestParam("noteId") int noteId,
			@RequestParam("email") String email, @RequestHeader String token) {
		noteService.CollabarateNote(noteId, email);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Collabarator added ", HttpStatus.CREATED.value(), null));
	}

	@PostMapping("/removecollabarator")
	public ResponseEntity<Response> removeCollabarator(@RequestParam("noteId") int noteId,
			@RequestParam("userId") int userId, @RequestHeader String token) {
//		noteService.CollabarateNote(noteId,userId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Collabarator added ", HttpStatus.CREATED.value(), null));
	}

	@GetMapping("/getcollabarator")
	public Set<User> getCollabarator(@RequestParam("noteId") int noteId, @RequestHeader String token) {
		return noteService.getCollabaratedUsers(noteId);
	}

	@PostMapping("/deletenoteonlabel")
	public ResponseEntity<Response>   deleteNoteOnLabel(@RequestParam("noteId") int noteId,@RequestParam("labelid") int labelid)
	{
				noteService.deleteNoteOnLabel(noteId,labelid);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new Response("Note on label  deleted ", HttpStatus.CREATED.value(), null));
	}
	
	@PostMapping("/deletenoteonuser")
	public ResponseEntity<Response> deleteNoteOnUser(@RequestParam("noteId") int noteId,
			@RequestParam("email") String email) {
		noteService.deleteCollabaratedNote(noteId, email);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note on label  deleted ", HttpStatus.CREATED.value(), null));
	}
	

}
