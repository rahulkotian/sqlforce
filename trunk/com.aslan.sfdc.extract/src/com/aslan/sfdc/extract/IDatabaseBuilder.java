/*******************************************************************************
 * Copyright (c) 2010 Gregory Smith (gsmithfarmer@gmail.com)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package com.aslan.sfdc.extract;

import java.util.List;

import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;

/**
 * Core interface that does the physical creation of a database from extracted Salesforce data.
 * 
 * @author greg
 *
 */
public interface IDatabaseBuilder {

	static final String FIELDTYPE_ID = "id";
	static final String FIELDTYPE_STRING = "string";
	static final String FIELDTYPE_TEXTAREA = "textarea";
	static final String FIELDTYPE_PHONE = "phone";
	static final String FIELDTYPE_EMAIL = "email";
	static final String FIELDTYPE_BOOLEAN = "boolean";
	static final String FIELDTYPE_REFERENCE = "reference";
	static final String FIELDTYPE_DATETIME = "datetime";
	static final String FIELDTYPE_PICKLIST = "picklist";
	static final String FIELDTYPE_MULTIPICKLIST = "multipicklist";
	static final String FIELDTYPE_URL = "url";
	static final String FIELDTYPE_CURRENCY = "currency";
	static final String FIELDTYPE_INT = "int";
	static final String FIELDTYPE_DATE = "date";
	static final String FIELDTYPE_DOUBLE = "double";
	static final String FIELDTYPE_ANYTYPE = "anyType";
	static final String FIELDTYPE_COMBOBOX = "combobox";
	static final String FIELDTYPE_BASE64 = "base64";
	static final String FIELDTYPE_TIME = "time";
	static final String FIELDTYPE_PERCENT = "percent";
	
	
	/**
	 * Create a table from a Saleforce SObject (DO NOT CREATE Foreign Keys!)
	 * 
	 * @param sfdcTable description of a Salesforce Object
	 * @throws Exception if anythig goes wrong.
	 */
	void createTable( DescribeSObjectResult sfdcTable ) throws Exception;
	
	/**
	 * Insert data into a table.
	 * 
	 * @param sfdcTable insert into this table
	 * @param fields the fields to insert.
	 * @param dataRows rows to insert. data in the same order as fields in table.
	 * @throws Exception if anything fails.
	 */
	void insertData( DescribeSObjectResult sfdcTable, Field[] fields, List<String[]> dataRows ) throws Exception;
		
}