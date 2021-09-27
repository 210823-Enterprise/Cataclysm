package com.revature.customStatement;

import com.revature.cataclysm.Cataclysm;
import com.revature.dummymodels.Test;
import com.revature.util.Configuration;

public class CustomDemo {
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		
		Configuration cfg = new Configuration();	
		Cataclysm cataclysm = new Cataclysm();
		
		// Create table with FK's, different constraints, etc	
		CustomColumn c = new CustomColumn("acc_owner")
				.datatype("SERIAL")
				.primaryKey(true);
		
		CustomColumn c2 = new CustomColumn("partner")
				.datatype("INTEGER")
				.constraint("NOT NULL")
				.reference("user_table(user_id)")
				.deleteCascade(true);
		
		CustomColumn c3 = new CustomColumn("username")
				.datatype("VARCHAR(50)")
				.constraint("UNIQUE");
		
		
		Create table = new Create("new_table")
				.column(c)
				.column(c2)
				.column(c3);
		
//		cataclysm.customCreate(table.toString());
		
		// INSERT ROW
		Insert i = new Insert("new_table")
				.set("partner", "1")
				.set("username", "'newaccount'");
	
//		cataclysm.customInsert(i.toString());
		
		
		// UPDATE
		Update u = new Update("new_table")
				.where("partner = 1")
				.set("username = 'jmligz'");
		
//		cataclysm.customUpdate(u.toString());
		
		// DELETE
		Delete d = new Delete("new_table")
				.where("username = 'jmligz'");
		
//		cataclysm.customDelete(d.toString());
		
		
		Select select = new Select("user_table")
				.column("username")
				.column("password")
				.where("age = 25");
		
		cataclysm.customSelect(User.class, select.toString());
		
	}

	
	
	
	
	

}
