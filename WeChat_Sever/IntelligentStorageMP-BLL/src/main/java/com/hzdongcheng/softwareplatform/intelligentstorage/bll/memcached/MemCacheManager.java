package com.hzdongcheng.softwareplatform.intelligentstorage.bll.memcached;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
 
import com.hzdongcheng.components.memcached.IMemCache;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.front.server.model.terminal.ELocker;
import com.hzdongcheng.front.server.model.terminal.MEMCACHE_CONTEXT;
import com.hzdongcheng.front.server.model.terminal.TerminalCntInfo;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.TBTerminal;


public class MemCacheManager {
	protected final Log4jUtils logger = Log4jUtils.createInstanse(this.getClass());
	
	
    private IMemCache memcachedService;
    @Resource
	public void setMemcachedService(IMemCache memcachedService){
	    this.memcachedService = memcachedService;
	}
	/**
	 * 私有默认构造函数
	 */
	private MemCacheManager() {
	}

	private static class SingletonHolder {
		private static final MemCacheManager instance = new MemCacheManager();
	}

	/**
	 * 静态工厂方法，返还此类的惟一实例
	 * 
	 * @return a GuotongManager instance
	 */
	public static MemCacheManager getInstance() {
		return SingletonHolder.instance;
	}
	
	public boolean isOnline(String terminalNo){
		TerminalCntInfo cntInfo= getTerminalCntInfo(terminalNo);
		if(cntInfo != null ){
			return cntInfo.isOnline();
		}
		return false;
	}
	
	/**
	 * 取设备连接信息
	 * @param terminalNo
	 * @return
	 */
	public TerminalCntInfo getTerminalCntInfo(String terminalNo){
	    StringBuffer keyBuff = new StringBuffer(MEMCACHE_CONTEXT.MEM_CNT_PREFIX);
	    String key = keyBuff.append(terminalNo).toString();
	    Object obj= memcachedService.get(key);
	    return (TerminalCntInfo) obj;
	}
	/**
	 * 取设备运行状态信息
	 * @param terminalNo
	 * @return
	 */
	public ELocker getELocker(String terminalNo){
	    StringBuffer keyBuff = new StringBuffer(MEMCACHE_CONTEXT.MEM_INFO_PREFIX);
	    String key = keyBuff.append(terminalNo).toString();
	    Object obj= memcachedService.get(key);
        return (ELocker) obj;
	}
	
	/**
	 * 取设备详细信息
	 * @param terminalNo
	 * @return
	 */
	public TBTerminal getTerminalDetail(String terminalNo){
	    StringBuffer keyBuff = new StringBuffer(MEMCACHE_CONTEXT.MEM_DETAIL_PREFIX);
        String key = keyBuff.append(terminalNo).toString();
        Object obj= memcachedService.get(key);
        if(obj == null){
            obj = new TBTerminal();
        }
        return (TBTerminal) obj;
	}
	/**
	 * 更新设备详细信息
	 * @param terminalNo
	 * @param terminal
	 */
	public void setTerminalDetail(String terminalNo, TBTerminal terminal){
        StringBuffer keyBuff = new StringBuffer(MEMCACHE_CONTEXT.MEM_DETAIL_PREFIX);
        String key = keyBuff.append(terminalNo).toString();
        memcachedService.saveAndUpdate(key, terminal);
        return;
    }
	/**
	 * 添加设备锁：同一设备同一时间只能处理一条推送业务
	 * @param terminalNo
	 * @return
	 */
	public String getPushLock(String terminalNo){
	    StringBuffer keyBuff = new StringBuffer(MEMCACHE_CONTEXT.MEM_PUSH_PREFIX);
	    String key = keyBuff.append(terminalNo).toString();
	    //
        String lock = UUID.randomUUID().toString();
        
        //logger.debug(key+":"+memcachedService.get(key));
        boolean isFree = false;
        int count = 5;
        
        try {
        	do{
        		Object obj = memcachedService.get(key);
                if(obj !=null){
                    //正在处理业务，等待一次，重新添加
                    Thread.sleep(100);
                }
                isFree = memcachedService.add(key, lock, 3);//设置锁定的超时时间为3秒。add成功，返回true
                if(isFree){
                	break;
                }
            }while(count-- > 0);
        }catch (InterruptedException e) {}

        if(!isFree){
            //logger.debug(key+":"+memcachedService.get(key));
            lock = "";
        }
        return lock;
    }
	public void delPushLock(String terminalNo, String lock){
	    if(StringUtils.isEmpty(lock)){
	        return;
	    }
	    StringBuffer keyBuff = new StringBuffer(MEMCACHE_CONTEXT.MEM_PUSH_PREFIX);
        String key = keyBuff.append(terminalNo).toString();
        String lockLocal = (String)memcachedService.get(key);
        if(StringUtils.isNotEmpty(lockLocal) && lockLocal.equals(lock)){
            memcachedService.delete(key);
        }
    }
}
