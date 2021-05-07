package com.revature.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.revature.data.EventDaoImpl;

@RunWith(MockitoJUnitRunner.class)
public class EventDaoTest {
	
	@Mock
	CqlSession session;
	
	@InjectMocks
	EventDaoImpl ed = new EventDaoImpl();
	
	@Before
	public void setUp() {
		
	}
	
	@Test(expected = Exception.class)
	public void addEvent() {
		Exception e = new Exception();
		
		CqlSession session = mock(CqlSession.class);
		SimpleStatement ss = mock(SimpleStatement.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		BoundStatement bs = mock(BoundStatement.class);
		
		when(session.prepare(Mockito.anyString())).thenReturn(ps);
		when(ps.bind(ss)).thenReturn(bs);


		when(session.prepare(Mockito.any(String.class))).thenThrow(e);
		ed.addEvent(null);
	}

}
