package com.leon.rowid.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.leon.core.util.SingletonIdUtil;
import com.leon.rowid.model.Id;

import net.sf.ehcache.CacheException;

@Service
@CacheConfig(cacheNames = {"id"})
public class RowIdServiceImpl implements RowIdService {

	private static final Logger logger = LogManager.getLogger(RowIdServiceImpl.class);
	
//	private static final String SEQUENCE_ROW_ID = "sequenceRowId";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Set<Id> ids = new HashSet<Id>();

	@Override
	public String getRowId(String idType) throws Exception {
//		jdbcTemplate.execute("select 1");
		SingletonIdUtil idUtil = SingletonIdUtil.getInstance(jdbcTemplate);
//		String id = idUtil.getGenerateRowId("S_SEQUENCE_S");
		String id = idUtil.getGenerateRowId(idType);
		return id;
	}
	
//	@Override 
//	@Cacheable(/*value = SEQUENCE_ROW_ID ,*/key = "idType")
//	public Id getRowId(String idType) throws Exception {
//		Id id = new Id();
//		generateRowIdCache(id);
////		List<String> idList;
//		for(Id idTemp:ids){
//			if(id.getIdType().equals(idType)){
//				id = idTemp;
//				break;
//			}
//		}
//		String currentVal = id.getGenerateId().get(0);
//		id.setCurrentVal(currentVal);
//		id.getGenerateId().remove(0);
//		return id;
//	}
//
//	@CachePut(/*value = SEQUENCE_ROW_ID,*/key = "#id.idType")
//    public Id generateRowIdCache(Id id) throws CacheException{
//		id.setIdType("sequenceRowId");
//        List<String> idList = new ArrayList<String>();
//        for(int i=0;i<10;i++){
//        	idList.set(i, new Integer(i+1).toString());
//        }
//        id.setGenerateId(idList);
//        ids.remove(id);
//        ids.add(id);
//        return id;
//    }
}
