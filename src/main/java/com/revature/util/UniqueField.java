package com.revature.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.revature.annotations.Column;
import com.revature.annotations.Nullable;

public class UniqueField {
	//@Column
	//private String name; (// how do I determine if this is a VARCHAR or NUMERIC or SERIAL PRIMARY KEY?

	private Field field;
	
	public UniqueField(Field field) {
		
		if (field.getAnnotation(Nullable.class) == null) {
			throw new IllegalStateException("Cannot create NullableField Object! Provided field " + getName() + " is not annotated with @Nullable");
		}
		
		this.field = field;
		
	}
	
	public Field getField() {
		return this.field;
	}
	
	public String getName() {
		return field.getName();
	}
	
	public Class<?> getType() {
		return field.getType();
	}
	
	public String getColumnName() {
		return field.getAnnotation(Column.class).columnName();
		
	}
	

	
	public Method get() {
		Method getter = null;
		try {
			getter = new PropertyDescriptor(getName(), field.getClass(), "is" + Character.toUpperCase(getName().charAt(0)) + getName().substring(1), null).getReadMethod();
			System.out.println(getName());
			return getter;
			
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return getter;
	}
	
	
}