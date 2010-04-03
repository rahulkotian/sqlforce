/*******************************************************************************
 * Copyright (c) 2010 Gregory Smith (gsmithfarmer@gmail.com)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package com.aslan.sdfc.extract.h2.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import com.aslan.sfdc.extract.DefaultExtractionMonitor;
import com.aslan.sfdc.extract.ExtractionManager;
import com.aslan.sfdc.extract.IDatabaseBuilder;
import com.aslan.sfdc.extract.IExtractionMonitor;
import com.aslan.sfdc.extract.ansi.SQLEmitterDatabaseBuilder;
import com.aslan.sfdc.extract.h2.H2DatabaseBuilder;
import com.aslan.sfdc.partner.LoginManager;
import com.aslan.sfdc.partner.test.SfdcTestEnvironment;

import junit.framework.TestCase;

/**
 * Verify that an H2 Schema can be build from Salesforce.
 * 
 * @author greg
 * 
 */
public class SchemaBuilderTest extends TestCase {

	IExtractionMonitor monitor = new DefaultExtractionMonitor() {

		@Override
		public void reportMessage(String msg) {
				System.err.println(msg);
		}
		
	};
	
	private void rmdir( File dir ) {
		if( dir.isDirectory()) {
			for( File file : dir.listFiles()) {
				rmdir( file );
			}
		}
		dir.delete();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		Class.forName("org.h2.Driver");
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExtractAllTables() throws Exception {
		Connection connection = null;
		File baseDatabaseName = File.createTempFile("TestH2Database", "");
		baseDatabaseName.deleteOnExit();

		try {
			connection = DriverManager.getConnection("jdbc:h2:"
					+ baseDatabaseName.getAbsolutePath(), "sa", "fred");

			//LoginManager.Session session = SfdcTestEnvironment.getTestSession();
			LoginManager.Session session = SfdcTestEnvironment.getSession("readonly");
			IDatabaseBuilder builder = new H2DatabaseBuilder( connection );
			
			ExtractionManager mgr = new ExtractionManager(session, builder);
			
			//mgr.extractSchema( "Attachment", monitor);
			//mgr.extractData("Attachment", monitor);
			
			mgr.extractSchema(monitor );
			mgr.extractData(monitor);
		} finally {
			if (null != connection) {
				connection.close();
			}
			File dbFile = new File( baseDatabaseName.getAbsolutePath() + ".h2.db");
			dbFile.delete();
			File lobDir = new File(baseDatabaseName.getAbsolutePath() + ".lobs.db" );
			rmdir(lobDir);
			System.err.println("Database was: " + baseDatabaseName);
		}

	}
	

}
