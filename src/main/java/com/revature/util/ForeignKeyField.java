package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.ForeignKey;

public class ForeignKeyField {

    private Field field;

    public ForeignKeyField(Field field) {
        if (field.getAnnotation(ForeignKey.class) == null) {
            throw new IllegalStateException("Cannot create ForeignKeyField object! Provided field, " + getName() + "is not annotated with @JoinColumn");
        }
        this.field = field;
    }

    public String getName() {
        return field.getName();
    }
    
	public Field getField() {
		return this.field;
	}

    public Class<?> getType() {
        return field.getType();
    }
    

    public String getColumnName() {
        return field.getAnnotation(ForeignKey.class).columnName();
    }

}