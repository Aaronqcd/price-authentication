/**
 * 
 */
package com.pemng.serviceSystem.base.test;

import javax.sql.DataSource;

import org.springframework.test.AbstractTransactionalSpringContextTests;

/**
 * @author Lay
 * 
 */
public class BaseTxSpringContextTest extends
		AbstractTransactionalSpringContextTests {

	protected DataSource dataSource;

	protected String[] getConfigLocations() {
		return new String[] { "resources\\spring\\applicationContext.xml" };
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
