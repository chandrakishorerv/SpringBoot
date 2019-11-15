package org.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bridgelabz.fundoonotes.model.Note;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class ElasticServiceImpl implements ElasticService{

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper mapper;

	private static final String index = "notes";

	private static final String type = "notes_data";


	@Override
	public void save(Note note) {
		try {
			System.out.println("entered into elastc service");
			Map<String, String> noteMap = mapper.convertValue(note, Map.class);
			IndexRequest request = new IndexRequest(index, type).id("" + note.getNoteId()).source(noteMap);
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
			log.info(response.status().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(int noteId, Note modifynote) {
		
		Map<String, String> noteMap = mapper.convertValue(modifynote, Map.class);
		UpdateRequest request = new UpdateRequest(index, type, modifynote.getNoteId()+"");
		request.doc(noteMap);
		try {
			UpdateResponse response=client.update(request, RequestOptions.DEFAULT);
			log.info(response.status().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteNote(Note modifynote) {
		DeleteRequest request=new DeleteRequest(index, type, modifynote.getNoteId()+"");
		try {
			DeleteResponse response=client.delete(request, RequestOptions.DEFAULT);
			log.info(response.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Note> search(String title, String description) {
		SearchSourceBuilder builder=new SearchSourceBuilder();
		builder.query(QueryBuilders.termQuery(title, description));
		SearchRequest request=new SearchRequest("notes");
		request.source(builder);
		MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder(title, description);
		matchQueryBuilder.fuzziness(Fuzziness.AUTO); 
		matchQueryBuilder.prefixLength(3); 
		matchQueryBuilder.maxExpansions(10);
		builder.query(matchQueryBuilder);
		List<Note> notes= new ArrayList<Note>();
		try {
			SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
			searchResponse.getHits().spliterator().forEachRemaining(note-> {
				try {
					notes.add(mapper.readValue(note.getSourceAsString(), Note.class));
				} catch (JsonParseException|JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
e.printStackTrace();
		}
		return notes;
	}

}
