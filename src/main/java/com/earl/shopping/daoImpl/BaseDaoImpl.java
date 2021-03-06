package com.earl.shopping.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.earl.shopping.dao.BaseDao;
import com.earl.util.HibernateHelper;

/**
 * 这里用到工具类 HibernateHelper
 * 
 * @author 黄祥谦
 * @time 2015/7/16
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

	@SuppressWarnings("rawtypes")
	Class clazz; // 用来存储BaseDaoImpl泛型T的实际类型
	// 这个日志工具暂时没有必要,主要用于记录日志，在构造方法中进行实例化
	Logger logger = LogManager.getLogger(this.getClass());
	SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	public BaseDaoImpl() {
		logger.debug("进入baseDaoImpl构造方法");
		long begintime = System.currentTimeMillis();
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type genType = parameterizedType.getActualTypeArguments()[0];
		System.out.println(genType + "-----baseDaoImpl");
		this.clazz = (Class) genType;
		logger.debug("泛型类型 ： " + clazz.getSimpleName());
		long endtime = System.currentTimeMillis();
		long spend = endtime-begintime;
		logger.debug("退出baseDaoImpl方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
	}

	public Session getCurrentSession() {
		logger.debug("进入getCurrentSession方法");
		long begintime = System.currentTimeMillis();
		
		Session currentSession = HibernateHelper.getSessionFactory().getCurrentSession();
		long endtime = System.currentTimeMillis();
		long spend = endtime-begintime;
		logger.debug("退出getCurrentSession方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
		return currentSession;
	}

	// 插入对象
	/**
	 * @author Administrator
	 * 
	 * @Param T object
	 * @Result void
	 */
	public void save(T t) {
		logger.debug("进入save方法");
		long begintime = System.currentTimeMillis();
		Transaction tran = getCurrentSession().beginTransaction();
		try {
			getCurrentSession().save(t);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}finally{
			long endtime = System.currentTimeMillis();
			long spend = endtime-begintime;
			logger.debug("退出save方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
		}
	}

	// 更新对象
	public boolean update(T t) {
		logger.debug("进入update方法");
		long begintime = System.currentTimeMillis();
		Transaction tran = getCurrentSession().beginTransaction();
		try {
			getCurrentSession().update(t);
			tran.commit();
			return true;
		} catch (Exception e) {
			tran.rollback();
			return false;
		}finally{
			long endtime = System.currentTimeMillis();
			long spend = endtime-begintime;
			logger.debug("退出update方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
		}
	}

	// 根据ID删除对象
	public void deleteById(int id) {
		delete(get(id));
	}

	@SuppressWarnings("unchecked")
	public T get(int id) {
		logger.debug("进入get方法");
		long begintime = System.currentTimeMillis();
		Transaction tran = getCurrentSession().beginTransaction();
		try {

			T object = (T) getCurrentSession().get(clazz, id);
			tran.commit();
			return object;
		} catch (Exception e) {
			tran.rollback();
			return null;
		}finally{
			long endtime = System.currentTimeMillis();
			long spend = endtime-begintime;
			logger.debug("退出get方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
		}
	}

	// 查找该表中的所有记录，
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		logger.debug("进入findAll方法");
		long begintime = System.currentTimeMillis();
		Transaction tran = getCurrentSession().beginTransaction();
		try {

			String hql = "from " + clazz.getSimpleName();
			List<T> list = getCurrentSession().createQuery(hql).list();
			tran.commit();
			return list;
		} catch (Exception e) {
			tran.rollback();
			return null;
		}finally{
			long endtime = System.currentTimeMillis();
			long spend = endtime-begintime;
			logger.debug("退出findAll方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
		}
	}

	// 删除所有对象
	public void deleteAll() {
		logger.debug("进入deleteAll方法");
		long begintime = System.currentTimeMillis();
		Transaction tran = getCurrentSession().beginTransaction();
		try {

			String hql = "delete from " + clazz.getName();
			getCurrentSession().createQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}finally{
			long endtime = System.currentTimeMillis();
			long spend = endtime-begintime;
			logger.debug("退出deleteAll方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
		}
	}

	// 通过对象来进行删除
	public void delete(T persistentInstance) {
		logger.debug("进入delete方法");
		long begintime = System.currentTimeMillis();
		Transaction tran = getCurrentSession().beginTransaction();
		try {

			getCurrentSession().delete(persistentInstance);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}finally{
			long endtime = System.currentTimeMillis();
			long spend = endtime-begintime;
			logger.debug("退出delete方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
		}
	}

	// 通过给定条件进行查询
	public List<T> findByGivenCriteria(T object) {
		logger.debug("进入findByGivenCriteria方法");
		long begintime = System.currentTimeMillis();
		// 业务逻辑开始
		Transaction tran = getCurrentSession().beginTransaction();
		try {
			Map<String, Object> notNullParam = null;
			// Method readMethod = beanMap.getWriteMethod("id");
			notNullParam = getNotNullProperties(object);
			Criteria criteria = HibernateHelper.getSessionFactory()
					.getCurrentSession().createCriteria(clazz);
			for (String key : notNullParam.keySet()) {
				criteria.add(Restrictions.eq(key, notNullParam.get(key)));
			}

			@SuppressWarnings("unchecked")
			List<T> listObject = criteria.list();

			// 业务逻辑结束
			HibernateHelper.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
			return listObject;
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
			return null;
		}finally{
			long endtime = System.currentTimeMillis();
			long spend = endtime-begintime;
			logger.debug("退出findByGivenCriteria方法,毫秒数: "+spend+"毫秒;耗费时间：" + spend/1000 + "秒");
		}

	}

	// 得到object中的非空属性
	private Map<String, Object> getNotNullProperties(T object) {

		Map<String, Object> notNullParam = null;
		BeanMap beanMap = new BeanMap(object);
		// Method readMethod = beanMap.getWriteMethod("id");
		notNullParam = new HashMap<String, Object>();
		Iterator<Object> keyIterator = beanMap.keySet().iterator();
		String propertyName = null;
		while (keyIterator.hasNext()) {
			propertyName = (String) keyIterator.next();

			// beanMap.get(propertyName)!= ""可以加上去
			if (!propertyName.equals("class")
					&& beanMap.get(propertyName) != null) {
				notNullParam.put(propertyName, beanMap.get(propertyName));
			}
		}
		return notNullParam;
	}

}