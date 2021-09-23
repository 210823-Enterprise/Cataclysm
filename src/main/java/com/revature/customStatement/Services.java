package com.revature.customStatement;

import com.revature.dummymodels.Test;
import com.revature.util.MetaModel;

public class Services {
	
	public static void main(String[] args) {
		Where where = addById(Test.class, 9);
		System.out.println(where);
	}
	
	public static String getById(Class clazz, int id) {
		
		
		
		return "";
	}
	
	protected static Where addById(Class clazz, int id) {
		
		MetaModel<?> model = MetaModel.of(clazz);
		Where where = new Where();
		where.name = model.getPrimaryKey().getColumnName();
		where.operator = Operators.EQUAL;
		where.value = String.valueOf(id);
		where.valueDataType = int.class;
		
		
		return where;
	}

}
