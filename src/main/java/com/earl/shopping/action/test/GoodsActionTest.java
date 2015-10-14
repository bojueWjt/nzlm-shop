package com.earl.shopping.action.test;

import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsJUnit4TestCase;
import org.junit.Test;

import com.earl.shopping.action.GoodsAction;

public class GoodsActionTest extends StrutsJUnit4TestCase<GoodsAction> {

	Logger logger = LogManager.getLogger(this.getClass());

	@Test
	public void testAddGoodsAction() throws UnsupportedEncodingException, ServletException {
		request.addParameter("goodsname", "园林工具 黑胶柄 五齿耙 草耙 松土工具农具 阳台盆栽花盆耙子");
		request.addParameter("goodsattributes", "材质_铁;类别_耙子;");
		request.addParameter("goodsprice", "25.0");
		request.addParameter("goodspic", "img/asd2.jpg");
		request.addParameter("ishot", "true");
		request.addParameter("goodslabel", "农具_RAYKA_耙子");
		request.addParameter("typeId", "26");
		logger.debug("pathInfo "+request.getPathInfo());
		String res = executeAction("/goods_addGoods.action");
		System.out.println(res);
		System.out.println("添加完成");
			
	}

	@Test
	public void testDeleGoodsAction() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByIdAction() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAttrbutes() throws UnsupportedEncodingException, ServletException {
		request.addParameter("goodstype2", "U盘");
//		request.addParameter("goodstype3", "u盘");
		logger.debug("pathInfo "+request.getPathInfo());
		String res = executeAction("/goods_getAttrbutes.action");
	}

	@Test
	public void testQueryByPrice() throws UnsupportedEncodingException, ServletException {
		request.addParameter("minPrice", "12.0");
		request.addParameter("maxPrice", "16.0");
		logger.debug("pathInfo "+request.getPathInfo());
		String res = executeAction("/goods_queryByPrice.action");
	}

	@Test
	public void testQueryByAttributes() throws UnsupportedEncodingException, ServletException {
		request.addParameter("goodsattributes", "颜色_金色");
		request.addParameter("goodsattributes", "容量_32G");
		request.addParameter("goodsattributes", "品牌_金士顿");
		logger.debug("RequestURI "+request.getRequestURI());
		String res = executeAction("/goods_queryByAttributes.action");
	}

	@Test
	public void testQueryByType() throws UnsupportedEncodingException, ServletException {
//		request.addParameter("goodstype2", "u盘");
		request.addParameter("goodstype3", "u盘");
		logger.debug("pathInfo "+request.getPathInfo());
		String res = executeAction("/goods_queryByType.action");
	}

	@Test
	public void testQueryHot() throws UnsupportedEncodingException, ServletException {
		logger.debug(request.getPathInfo());
		String res = executeAction("/goods_queryHot.action");
	}

	@Test
	public void testQueryKeyWord() throws UnsupportedEncodingException,
			ServletException {

		request.addParameter("keyWord", "u盘");
		logger.debug(request.getPathInfo());
		String res = executeAction("/goods_queryKeyWord.action");

	}

	@Test
	public void testGetCategory() throws UnsupportedEncodingException,
			ServletException {
		String res = executeAction("/goods_getCategory.action");
	}

}
