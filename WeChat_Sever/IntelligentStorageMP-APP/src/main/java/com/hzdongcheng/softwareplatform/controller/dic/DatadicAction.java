package com.hzdongcheng.softwareplatform.controller.dic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxSizeEntityDao;


@Controller
@RequestMapping("/datadicAction")
public class DatadicAction {
	@Autowired
	BoxSizeEntityDao boxSizedao;
	
	@RequestMapping(value="/insertBoxSize.do")
    @ResponseStatus(HttpStatus.OK)
	//增加箱体尺寸	
		public String insertBoxSize(HttpServletRequest request, HttpServletResponse response) throws Exception {		
			request.setCharacterEncoding("utf-8");
			//类型  宽 高 深
			String genre = request.getParameter("genre");
			String kuan = request.getParameter("kuan");
			Integer width = Integer.parseInt(kuan);
			String gao = request.getParameter("gao");
			Integer height = Integer.parseInt(gao);
			String shen = request.getParameter("shen");
			Integer depth = Integer.parseInt(shen);
		
			BoxSizeEntity box = new BoxSizeEntity();
			box.setBoxtypename(genre);
			box.setWidth(width);
			box.setHeight(height);
			box.setDepth(depth);
			
			boxSizedao.insert(box);		
			return "web/dic/boxType";
		}
	//查询箱体信息
	public String SelectBoxSize(@RequestParam BoxSizeEntityExample example) throws Exception {				
		List<BoxSizeEntity> boxSizeList = boxSizedao.selectByExample(example);
		String json = JsonUtils.toJSONNoFeatures(boxSizeList);
		return json;
	}
	
}
