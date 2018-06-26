package org.IntelligentStorageMP.BLL;
import java.lang.reflect.InvocationTargetException;
 
import org.apache.commons.beanutils.BeanUtils;
 
public class Tests {
	
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
 
    	//T1 T1es = new T1(1,"t1",new Date());
    	T1 T1es = new T1();
    	T2 T2es = new T2(2,"t2",null);
		//BeanUtils.copyProperties(T2es,T1es);
		BeanUtils.copyProperties(T1es,T2es);
		System.out.println(" 1----   "+T2es.getName()+"   "+T2es.getId()+"   "+T2es.getDateeeeeees());
		System.out.println(" 2----   "+T1es.getName()+"   "+T1es.getId()+"   "+T1es.getClass());
 
		/*BeanUtils.copyProperties(T1es,T2es);
		System.out.println(" 2----   "+T2es.getName()+"   "+T2es.getId());  */
		
		
/*    	T2 T2es = new T2(1,"t2" ,null);
    	T1 T1es = new T1();
    	
		BeanUtils.copyProperties(T2es,T1es);
		System.out.println(" 1----   "+T1es.getName()+"   "+T1es.getId()+"   "+T1es.getDates());
 
		BeanUtils.copyProperties(T1es,T2es);
		System.out.println(" 2----   "+T1es.getName()+"   "+T1es.getId()+"   "+T1es.getDates());*/
 
		
		
	}
}
