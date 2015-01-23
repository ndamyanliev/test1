package com.bulpros.stuff;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseJSON {
	
	public static void main(String[] args) {
	    System.out.println("start");
	    try {
	    	File file = new File("D:\\Downloads\\batch3.json");
	    	String[] batch = new String[]{"389864065", "391218900", "284233394", "291778246"};
	    	HashMap<String,String> gents = new HashMap<String,String>();
			JsonFactory f = new JsonFactory();
			System.out.println();
			JsonParser jp = f.createParser(file);
			System.out.println();
			// read the record into a tree model,
            // this moves the parsing position to the end of it
			JsonToken current;
			

		    current = jp.nextToken();
		    if (current != JsonToken.START_OBJECT) {
		      System.out.println("Error: root should be object: quiting.");
		      System.exit(1);
		    }

		    while (jp.nextToken() != JsonToken.END_OBJECT) {
		      String fieldName = jp.getCurrentName();
		      // move from field name to field value
		      current = jp.nextToken();
		      if (fieldName.equals("players")) {
		        if (current == JsonToken.START_ARRAY) {
		          // For each of the records in the array
		          while (jp.nextToken() != JsonToken.END_ARRAY) {
		            // And now we have random access to everything in the object
		          	ObjectMapper mapper = new ObjectMapper();
		        	JsonNode node = mapper.readTree(jp);
		        	gents.put(node.get("id").asText(), node.get("gentleness").asText());
		            System.out.println("gentleness: " + node.get("gentleness").asText());
		            System.out.println("id: " + node.get("id").asText());
		          }
		        } else {
		          System.out.println("Error: records should be an array: skipping.");
		          jp.skipChildren();
		        }
		      } else {
//		        System.out.println("Unprocessed property: " + fieldName);
		        jp.skipChildren();
		      }
		    }
	    	for (int i=0;i<batch.length;i++) {
	    		System.out.println(batch[i] + ": " + gents.get(batch[i]));
	    	}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
	}

}
