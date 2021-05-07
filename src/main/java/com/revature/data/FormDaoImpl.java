package com.revature.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.models.CoverageType;
import com.revature.models.Form;
import com.revature.models.Form.GradingFormat;
import com.revature.util.CassUtil;

public class FormDaoImpl implements FormDao {
	private static final Logger log = LogManager.getLogger(UserDaoImpl.class);
	private CqlSession session = CassUtil.getInstance().getSession();

	@Override
	public void addForm(Form f) {
		log.debug("" + f);
		StringBuilder sb = new StringBuilder("Insert into project1.forms")
				.append("(coverageType, userId, dateOfSubmission,")
				.append("eventId, gradingFormat, status, requestedamount,")
				.append("bencoApproval, dhapproval, dsApproval, denialcomment)")
				.append("values( ?, ?, ?, ")
				.append("?, ?, ?, ?,")
				.append("?, ?, ?, ?);");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss).bind(
				f.getCoverage().name(), f.getUserID(), f.getDateOfSubmission(), 
				f.getEventID(), f.getGradingFormat().name(), f.getStatus().name(), f.getRequestedAmount(),
				false, false, false, "");
		session.execute(bs);
	}

	@Override
	public List<Form> getForms() {
		List<Form> fList = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder("select * from project1.forms");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		ResultSet rs = session.execute(ss);
		rs.forEach(data ->{
			Form f = new Form();
			f.setCoverage(CoverageType.valueOf(data.getString("coverageType")));
			f.setEventID(data.getInt("eventId"));
			f.setUserID(data.getInt("userId"));
			f.setDateOfSubmission(data.getLocalDate("dateOfSubmission"));
			f.setDhApproval(data.getBoolean("dhApproval"));
			f.setDsApproval(data.getBoolean("dhApproval"));
			f.setBenCoApproval(data.getBoolean("BenCoApproval"));
			f.setDenialComment(data.getString("denialComment"));
			f.setGradingFormat(GradingFormat.valueOf(data.getString("gradingFormat")));
			f.setRequestedAmount(data.getFloat("requestedamount"));
			f.setStatus(Form.Status.valueOf(data.getString("status")));
			fList.add(f);
		});
		return fList;
	}

	@Override
	public void updateDSApproval(Form f) {
		StringBuilder sb = new StringBuilder("update project1.forms ").append("set ").append("dsApproval = ?,")
				.append("denialComment = ?")
				.append(" where ")
				.append("CoverageType = ? ")
				.append(" and ")
				.append("eventid = ? and userid = ?");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss).bind(
				f.isDsApproval(), 
				f.getDenialComment(), 
				f.getCoverage().name(), 
				f.getEventID(), 
				f.getUserID());
		session.execute(bs);
	}

	@Override
	public void updateDHApproval(Form f) {
		StringBuilder sb = new StringBuilder("update project1.forms ").append("set ")
				.append("dsApproval = ?,")
				.append("denialComment = ?,")
				.append(" where ")
				.append("CoverageType = ? ")
				.append(" and ")
				.append("eventid = ? and userid = ?");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss).bind(
				f.isDhApproval(), 
				f.getDenialComment(), 
				f.getCoverage().name(), 
				f.getEventID(), 
				f.getUserID());
		session.execute(bs);
	}

	@Override
	public void updateBCApproval(Form f) {
		StringBuilder sb = new StringBuilder("update project1.forms ")
				.append("set ")
				.append("benCoApproval = ?,")
				.append("denialComment = ?,")
				.append("requestedamount = ?,")
				.append(" where ")
				.append("CoverageType = ? ")
				.append(" and ")
				.append("eventid = ? and userid = ?");
		SimpleStatement ss = new SimpleStatementBuilder(sb.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bs = session.prepare(ss).bind(
				f.isBenCoApproval(), 
				f.getDenialComment(), 
				f.getCoverage().name(), 
				f.getEventID(), 
				f.getUserID());
		session.execute(bs);
	}
}
