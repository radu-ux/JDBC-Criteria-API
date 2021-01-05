package frsd;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.*;

public class CityFilter implements Predicate{
	private int lo;
	private int hi;
	private int columnNo = -1;
	private String columnName = null;
	
	
	public CityFilter(int lo, int hi, int columnNumber) {
		this.lo = lo;
		this.hi = hi;
		this.columnNo = columnNumber;
		this.columnName = null;
	}
	
	public CityFilter(int lo, int hi, String columnName) {
		this.lo = lo;
		this.hi = hi;
		this.columnNo = -1;
		this.columnName = columnName;
	}
	
	@Override
	public boolean evaluate(RowSet rs) {
		boolean evaluation = false;
		if (rs == null)
			evaluation = false;
		try {
			int columnValue = -1;
			if (this.columnNo > 0) {
				columnValue = rs.getInt(this.columnNo);
			} else if (this.columnName != null ) {
				columnValue = rs.getInt(this.columnName);
			} else {
				evaluation = false;
			}
			
			if(columnValue >= this.lo && columnValue <= this.hi) {
				evaluation = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return evaluation;
	}

	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		if (this.columnNo == column) {
			int columnValue = ((Integer)value).intValue();
			if(columnValue >= this.lo && columnValue <= this.hi) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean evaluate(Object value, String columnName) throws SQLException {
		if (this.columnName.equalsIgnoreCase(columnName)) {
			int columnValue = ((Integer)value).intValue();
			if(columnValue >= this.lo && columnValue <= this.hi) {
				return true;
			}
		}
		return false;
	}
	
}