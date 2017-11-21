package com.leon.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.leon.rowid.model.Sequence;

/**
 * 获取id工具类
 * @author Leon
 *
 */
public class SingletonIdUtil {

	private static volatile SingletonIdUtil instance;
	
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 构造方法
	 */
	private SingletonIdUtil(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
		this.sequenceIdList = new ArrayList<String>();
		this.sequenceIdListBuffer = new ArrayList<String>();
		this.sequenceRowId = getCurrentInfo("S_SEQUENCE_S");
		generateRowId(this.sequenceRowId.getCurrentValue(),this.sequenceRowId.getStep(),this.sequenceRowId.getIncrement(),this.sequenceIdList);
//		this.sequenceRowId = null;
	}
	
	/**
	 * 获取SingletonIdUtil实例
	 * @return
	 */
	public static SingletonIdUtil getInstance(JdbcTemplate jdbcTemplate){
		if(instance == null){
			synchronized (SingletonIdUtil.class){
				if(instance == null){
					instance = new SingletonIdUtil(jdbcTemplate);
				}
			}
		}
		return instance;
	}
	
	private Sequence sequenceRowId;//S_SEQUENCE_S
	
	private Sequence sequenceOrder;//S_ORDER_SEQUENCE_S
	
	private Sequence sequenceUsername;//S_USERNAME_SEQUENCE_S
	
	private Sequence sequenceLog;//S_SEQUENCE_LOG
	
	private Sequence sequenceSeibleLineId;//SIEBEL_STATS_LINE_ID_S
	
	private Sequence sequenceAccnt2Code;//S_ACCNT_2CODE_S
	
	private Sequence sequenceSerReq;//LNK_SER_REQ_RECORD_SEQ_NUM
	//----------------------------------------------------------------//
	private List<String> sequenceIdList;// = new ArrayList<String>();
	
	private List<String> sequenceIdListBuffer;// = new ArrayList<String>();
	
	/** 模式一.单线程处理缓冲id
	 * 根据seqname即sequence的类型来获取id
	 * @param idType
	 * @return
	 */

	public String getGenerateRowId(String idType){
		String id = "noMatchId";
		if(idType == "S_SEQUENCE_S" || "S_SEQUENCE_S".equals(idType)){
			if(this.sequenceIdList.size() > 0){
				id = this.sequenceIdList.get(0);
				this.sequenceIdList.remove(0);
			}else if(this.sequenceIdListBuffer.size() > 0){
				id = this.sequenceIdListBuffer.get(0);
				this.sequenceIdListBuffer.remove(0);
				this.sequenceIdList = this.sequenceIdListBuffer;
				this.sequenceIdListBuffer = new ArrayList<String>();
			}else{
				this.sequenceRowId = getCurrentInfo("S_SEQUENCE_S");
				generateRowId(this.sequenceRowId.getCurrentValue(),this.sequenceRowId.getStep(),this.sequenceRowId.getIncrement(),this.sequenceIdList);
			}
			
			if((this.sequenceIdList.size() < (this.sequenceRowId.getStep() * 0.9)) && this.sequenceIdListBuffer.size() <= 0){
				System.out.println("开始生成备用缓冲id时间："+DateUtil.getHHMMSS_SSS());
				this.sequenceRowId = getCurrentInfo("S_SEQUENCE_S");
				generateRowId(this.sequenceRowId.getCurrentValue(),this.sequenceRowId.getStep(),this.sequenceRowId.getIncrement(),this.sequenceIdListBuffer);
				System.out.println("生成备用缓冲id结束时间："+DateUtil.getHHMMSS_SSS());
//				this.sequenceRowId = null;
			}
			System.out.println("当前id队列长度："+this.sequenceIdList.size());
			System.out.println("buffer id队列长度："+this.sequenceIdListBuffer.size());
		}
		
		return id;
	}

	
	/** 模式二.多线程处理缓冲id
	 * 根据seqname即sequence的类型来获取id
	 * @param idType
	 * @return
	 */
	/*
	public String getGenerateRowId(String idType){
		String id = "noMatchId";
		if(idType == "S_SEQUENCE_S" || "S_SEQUENCE_S".equals(idType)){
			if(this.sequenceIdList.size() > 0){
				id = this.sequenceIdList.get(0);
				this.sequenceIdList.remove(0);
			}else if(this.sequenceIdListBuffer.size() > 0){
				id = this.sequenceIdListBuffer.get(0);
				this.sequenceIdListBuffer.remove(0);
				this.sequenceIdList = this.sequenceIdListBuffer;
				this.sequenceIdListBuffer = new ArrayList<String>();
			}else{
				this.sequenceRowId = getCurrentInfo("S_SEQUENCE_S");
				generateRowId(this.sequenceRowId.getCurrentValue(),this.sequenceRowId.getStep(),this.sequenceRowId.getIncrement(),this.sequenceIdList);
			}
			if((this.sequenceIdList.size() < (this.sequenceRowId.getStep() * 0.9)) && this.sequenceIdListBuffer.size() <= 0){
//			if((this.sequenceIdList.size() < (this.sequenceRowId.getStep() - 1)) && this.sequenceIdListBuffer.size() <= 0){//测试用
				new Thread(){
					public void run(){
						System.out.println("支线程开始时间："+DateUtil.getHHMMSS_SSS());
						sequenceRowId = getCurrentInfo("S_SEQUENCE_S");
						generateRowId(sequenceRowId.getCurrentValue(),sequenceRowId.getStep(),sequenceRowId.getIncrement(),sequenceIdListBuffer);
						System.out.println("支线程结束时间："+DateUtil.getHHMMSS_SSS());
					}
				}.start();
			}
			System.out.println("主线程");
			System.out.println("当前id队列长度："+this.sequenceIdList.size());
			System.out.println("buffer id队列长度："+this.sequenceIdListBuffer.size());
		}
		
		return id;
	}
	*/
	/**
	 * 生成row_id的方法，其它生成策略可以自定
	 * @param currentId
	 * @param step
	 * @param increment
	 * @param seqList
	 */
	private void generateRowId(String currentId,Integer step,String increment,List<String> seqList) {
		String res = "";
		Long currentTmp = new Long(currentId);
		Integer incre;
		if(increment.equals("") || increment == null){
			incre = 1;
		}else{
			incre = Integer.valueOf(increment);
		}
		
		for (int i=0;i<step;i=i+incre){
			res = "W-" + Long.toHexString(currentTmp).toUpperCase();
			seqList.add(res);
			currentTmp = currentTmp + incre;
		}
	}
	
	/**
	 * 根据idType获取相关记录，并取得id、sequence类型、当前值、增量、步长等信息
	 * @param idType
	 * @return
	 */
	private Sequence getCurrentInfo(String idType){
		Sequence seq = null;
		
		String sql1 = "begin;";
		String sql2 = "update lnk_seq set currentvalue = CONVERT(currentvalue,signed) + CONVERT(step,signed) where seqname = '" + idType + "';";
		String sql3 = "select seqname seqName,currentvalue currentValue,increment increment,step step,row_id id from lnk_seq where seqname = '" + idType + "' limit 1;";
		String sql4 = "commit;";
		String sql5 = "rollback;";
		
		this.jdbcTemplate.execute(sql1);
		int updateFlag = jdbcTemplate.update(sql2);
//		System.out.println("updateFlag:"+updateFlag);
		if(updateFlag > 0){
			List seqList = jdbcTemplate.queryForList(sql3);
			this.jdbcTemplate.execute(sql4);
			for(int i=0;i<seqList.size();i++){
				Map sequenceMap = (Map)seqList.get(i);
				seq = new Sequence();
				seq.setId(sequenceMap.get("id").toString());
				seq.setSeqName(sequenceMap.get("seqName").toString());
				seq.setCurrentValue(sequenceMap.get("currentValue").toString());
				seq.setIncrement(sequenceMap.get("increment").toString());
				seq.setStep((Integer)sequenceMap.get("step"));
			}
		}else{
			this.jdbcTemplate.execute(sql5);
		}
		
		
		return seq;
	}
}
