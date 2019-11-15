package org.bridgelabz.fundoonotes.controller;

import java.util.List;
import java.util.Set;

import org.bridgelabz.fundoonotes.dto.LabelDTO;
import org.bridgelabz.fundoonotes.model.Label;
import org.bridgelabz.fundoonotes.model.Note;
import org.bridgelabz.fundoonotes.response.Response;
import org.bridgelabz.fundoonotes.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/label")
@Slf4j
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class LableController {
	@Autowired
	private LabelService labelService;

	@PostMapping("/createlable")
	public ResponseEntity<Response> createNote(@RequestBody String newLabel, @RequestHeader("token") String token) {
		log.info("entered into label save controller");
		LabelDTO newLabel1 =new LabelDTO();
		newLabel1.setLabelName(newLabel);
	labelService.lableCreation(newLabel1, token);
		
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("newLabel created", HttpStatus.CREATED.value(), null));
		
	}
	@PostMapping("/updatelable")
	public ResponseEntity<Response> updateLabel(@RequestBody Label labelobject, @RequestHeader("token") String token) {
		log.info("entered into label save controller");
		
	labelService.lableUpdation(labelobject, token);
		
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("label updated", HttpStatus.CREATED.value(), null));
		
	}
	@PostMapping("/deletelable")
	public ResponseEntity<Response> deleteLabel(@RequestBody Label labelobject, @RequestHeader("token") String token) {
		log.info("entered into label delete controller");
		
	labelService.lableDeletion(labelobject, token);
		
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("label deleted", HttpStatus.CREATED.value(), null));
		
	}
	
	@PostMapping("/createlabelnote")
	public ResponseEntity<Response> createNoteLabel(@RequestParam int labelid,@RequestParam int noteId,@RequestHeader("token") String token) {
		log.info("entered into label save controller");
	labelService.createNoteLabel(labelid, noteId);
		
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("newLabel created", HttpStatus.CREATED.value(), null));
		
	}
	
	
	
	
	
	@GetMapping("/getlables")
	public List<Label> getNote(
			@RequestHeader("token")String token
			) {
//	String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6MX0.Ssmr98VRvjqOG5ouDOPaj3Tx4_3wIBzkiZFGmBjSddnKqVHasd3Y-O3CB8n1BvmAANwSG0k6IE9XOBTRGdLQtw";
		log.info("entered into  get label controller"+token);
		List<Label> listoflables = labelService.getAllLables(token);
		if (listoflables!=null) {
			return listoflables;
		} else {
			return null;
		}
	}
	
	@GetMapping("/getnotesonlables")
	public Set<Note> getNotesOnLabel(@RequestParam int labelid) {
		Set<Note> listofnotes = labelService.getAllNotesOnLables(labelid);
		
		return listofnotes;
	}

}
