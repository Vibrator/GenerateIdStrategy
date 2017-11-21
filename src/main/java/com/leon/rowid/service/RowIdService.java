package com.leon.rowid.service;

import net.sf.ehcache.CacheException;

public interface RowIdService {

	//根据id类型获取对应的id
	public String getRowId(String idType) throws Exception;
	
//	public Id generateRowIdCache() throws CacheException;
}
