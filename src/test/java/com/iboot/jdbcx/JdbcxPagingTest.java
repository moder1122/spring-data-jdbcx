package com.iboot.jdbcx;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.iboot.jdbcx.dialect.SelectSqlUtils;

import net.turnbig.jdbcx.modal.Member;

public class JdbcxPagingTest {
	@Test
	public void testQueryPagedSql() {
//		String sql = " select a.a1, c.c1 from (select * from a_dual order by z) a, "
//				+ "  (select c1,c2,c3 from c_dual order by yy) c "
//				+ "  where a.id=b.id order by c2";
		//Pageable pageable = PageRequest.of(2, 10,Sort.by("AA"));
		//String sortSql = SelectSqlUtils.addSort(sql, pageable.getSort());
			
		
		Pageable pageable = PageRequest.of(2, 10,Sort.by("AA"));
		
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT * FROM (  ");
	    sqlStr.append(" SELECT  ");
	    sqlStr.append("     MAX(PARTNER) AS PARTNER, ");
	    sqlStr.append("     MAX(E.SUBVALUE2) AS PARTNERNAME, ");
	    sqlStr.append("     MAX(COUNTYNAME) AS COUNTYNAME, ");
	    sqlStr.append("     MAX(TXNDATE) AS TXNDATE, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('81','85') THEN CNT ELSE '0' END) AS CNT81, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('81','85') THEN AMT ELSE '0' END) AS AMT81, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('89') THEN CNT ELSE '0' END) AS CNT89, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('89') THEN AMT ELSE '0' END) AS AMT89, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('30') THEN CNT ELSE '0' END) AS CNT30, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('30') THEN AMT ELSE '0' END) AS AMT30, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('39') THEN CNT ELSE '0' END) AS CNT39, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('39') THEN AMT ELSE '0' END) AS AMT39, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('42') THEN CNT ELSE '0' END) AS CNT42, ");
	    sqlStr.append("     SUM(CASE WHEN TXNTYPE IN ('42') THEN AMT ELSE '0' END) AS AMT42 ");
	    sqlStr.append(" FROM ( ");
	    sqlStr.append("   SELECT B.PARTNER,B.TXNDATE,C.COUNTYNAME,B.TXNTYPE, COUNT(*) AS CNT,SUM(TXNAMT) AS AMT FROM ");
	    sqlStr.append("     (  ");
	    sqlStr.append("         SELECT  ");
	    sqlStr.append("           A.PARTNER, A.TXNTYPE, A.TXNDATE, A.TXNAMT, A.SHOPID ");
	    sqlStr.append("         FROM DB.TBXVTXN A ");
	    sqlStr.append("         WHERE  ");
	    sqlStr.append("           A.PARTNER IN ('A11','A00') AND A.TXNDATE BETWEEN '20200101' AND  '20200101' AND A.TXNVALIDFLAG IN ('0','1') ");
	    sqlStr.append("         UNION ALL ");
	    sqlStr.append("         SELECT  ");
	    sqlStr.append("           A.SYSID||A.SPID, A.TXNTYPE, A.TXNDATE, A.TXNAMT, A.SHOPID ");
	    sqlStr.append("         FROM DB.TBXATXN A ");
	    sqlStr.append("         WHERE  ");
	    sqlStr.append("           A.SYSID||A.SPID IN ('A11','A00') AND A.TXNDATE BETWEEN '20200101' AND  '20200101' AND A.TXNFLAG IN ('0','1') ");
	    sqlStr.append("     ) B  ");
	    sqlStr.append("     LEFT JOIN  ");
	    sqlStr.append("     ( ");
	    sqlStr.append("       SELECT  ");
	    sqlStr.append("          S.PARTNER, S.SHOPID, S.COUNTY, P.SUBVALUE2 AS COUNTYNAME FROM DB.TBXMSHOPCODE S ,  ");
	    sqlStr.append("          (SELECT DISTINCT SUBVALUE1, SUBVALUE2 FROM DB.TBXMP02 WHERE REFCODE = 'XM021') P ");
	    sqlStr.append("       WHERE  ");
	    sqlStr.append("           S.PARTNER IN ('A11','A00') AND S.COUNTY = P.SUBVALUE1  ");
	    sqlStr.append("     ) C ON B.PARTNER = C.PARTNER AND B.SHOPID = C.SHOPID ");
	    sqlStr.append("       WHERE C.COUNTY='TW' ");
	    sqlStr.append("     GROUP BY B.PARTNER,B.TXNDATE,C.COUNTYNAME,B.TXNTYPE ");
	    sqlStr.append("  ) LEFT JOIN DB.TBXVP02 E ON PARTNER = E.SUBVALUE1 AND E.REFCODE='PARTNER' AND E.SUBVALUE1 in ('A11','A00') ");
	    sqlStr.append(" GROUP BY PARTNER,E.SUBVALUE2,COUNTYNAME,TXNDATE ");
	    sqlStr.append(" ORDER BY PARTNER, COUNTYNAME, TXNDATE ");
	    sqlStr.append(" ) ");
	   // sqlStr.append(" WITH UR ");
	    
	    
	    String sqlStr2 = "SELECT a FROM mytable order by xxx "
	    		+ "        UNION " 
	    		+ "       SELECT b FROM mytable2 order by yyy";

	    
	    //System.out.println(sqlStr.toString());
	    ///String sortSql = SelectSqlUtils.addSort(sqlStr3, pageable.getSort());
		
	    System.out.println(SelectSqlUtils.getCountSql(sqlStr2));
		System.out.println(SelectSqlUtils.getPageableSqlWithFtechOffset(sqlStr2, pageable));
	}
}
