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
		String sql = "select a.a1, c.c1 from (select * from a_dual) a, (select c1,c2,c3 from c_dual) c where a.id=b.id order by c2";
		Pageable pageable = PageRequest.of(2, 10,Sort.by("AA"));
		//String sortSql = SelectSqlUtils.addSort(sql, pageable.getSort());
				
		System.out.println(SelectSqlUtils.getPageableSqlWithFtechOffset(sql, pageable));
	}
}
