package com.packtpub.infinispan.chapter7.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.packtpub.infinispan.chapter7.domain.Guest;

public class GuestListImporter {

	public List<Guest> uploadGuestFile(String filePath) throws Exception {
		List<Guest> list = new ArrayList<Guest>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		while ((line = br.readLine()) != null) {
			list.add(parseCSV(line));
		}				
		return list;
	}
	
	private Guest parseCSV(String line) throws ParseException{
		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		Guest guest = new Guest();
		guest.setID(Integer.getInteger(tokenizer.nextToken())); // ID Column
        guest.setFirstName(tokenizer.nextToken()); // First Name
        guest.setLastName(tokenizer.nextToken()); // Last Name
        guest.setDocumentNumber(Long.getLong(tokenizer.nextToken())); // Document Number
        guest.setBirthDate(formatDate(tokenizer.nextToken())); // Birth Date
		return guest;
	}
	
	private Date formatDate(String stringDate) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");		
		return formatter.parse(stringDate);
	}

}
