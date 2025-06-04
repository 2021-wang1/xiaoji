package com.controller;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.entity.pojo.JudgeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;


import com.entity.ZuoyedianpingEntity;
import com.entity.view.ZuoyedianpingView;

import com.service.ZuoyedianpingService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import org.springframework.web.bind.annotation.*;


/**
 * 作业点评
 * 后端接口
 * @author 
 * @email 
 * @date
 */
@RestController
@RequestMapping("/zuoyedianping")
public class ZuoyedianpingController {
    @Autowired
    private ZuoyedianpingService zuoyedianpingService;



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, ZuoyedianpingEntity zuoyedianping,
				  HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			zuoyedianping.setXuehao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("jiaoshi")) {
			zuoyedianping.setGonghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ZuoyedianpingEntity> ew = new EntityWrapper<ZuoyedianpingEntity>();
		PageUtils page = zuoyedianpingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zuoyedianping), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ZuoyedianpingEntity zuoyedianping,
		HttpServletRequest request){
        EntityWrapper<ZuoyedianpingEntity> ew = new EntityWrapper<ZuoyedianpingEntity>();
		PageUtils page = zuoyedianpingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zuoyedianping), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ZuoyedianpingEntity zuoyedianping){
       	EntityWrapper<ZuoyedianpingEntity> ew = new EntityWrapper<ZuoyedianpingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( zuoyedianping, "zuoyedianping")); 
        return R.ok().put("data", zuoyedianpingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ZuoyedianpingEntity zuoyedianping){
        EntityWrapper< ZuoyedianpingEntity> ew = new EntityWrapper< ZuoyedianpingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( zuoyedianping, "zuoyedianping")); 
		ZuoyedianpingView zuoyedianpingView =  zuoyedianpingService.selectView(ew);
		return R.ok("查询作业点评成功").put("data", zuoyedianpingView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ZuoyedianpingEntity zuoyedianping = zuoyedianpingService.selectById(id);
        return R.ok().put("data", zuoyedianping);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ZuoyedianpingEntity zuoyedianping = zuoyedianpingService.selectById(id);
        return R.ok().put("data", zuoyedianping);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZuoyedianpingEntity zuoyedianping, HttpServletRequest request){
    	zuoyedianping.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(zuoyedianping);
        zuoyedianpingService.insert(zuoyedianping);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ZuoyedianpingEntity zuoyedianping, HttpServletRequest request){
    	zuoyedianping.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(zuoyedianping);
        zuoyedianpingService.insert(zuoyedianping);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ZuoyedianpingEntity zuoyedianping, HttpServletRequest request){
        //ValidatorUtils.validateEntity(zuoyedianping);
        zuoyedianpingService.updateById(zuoyedianping);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        zuoyedianpingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<ZuoyedianpingEntity> wrapper = new EntityWrapper<ZuoyedianpingEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			wrapper.eq("xuehao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("jiaoshi")) {
			wrapper.eq("gonghao", (String)request.getSession().getAttribute("username"));
		}

		int count = zuoyedianpingService.selectCount(wrapper);
		return R.ok().put("count", count);
	}


	private static final String JUDGE0_URL = "https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=false&wait=true&fields=*";
	private static final String RAPIDAPI_KEY = "xx"; // 替换为你的 RapidAPI 密钥
	private static final String RAPIDAPI_HOST = "judge0-ce.p.rapidapi.com";

	@Autowired
	private ObjectMapper objectMapper;


	@PostMapping("/run-code")
	public ResponseEntity<String> judgeCode(@RequestParam String code) throws IOException {
		// 构造请求体
		JudgeRequest requestBody = new JudgeRequest();
		requestBody.setLanguage_id(71); // 设置语言为python
		requestBody.setSource_code(code); // 设置源代码
		requestBody.setStdin(""); // 设置标准输入（如果需要的话）

		// 使用 OkHttp 发送 POST 请求
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json"); // 设置请求体的媒体类型为 JSON
		okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, objectMapper.writeValueAsString(requestBody)); // 将请求体转换为 JSON 字符串
		Request request = new Request.Builder() //构建请求
				.url(JUDGE0_URL)
				.post(body)
				.addHeader("x-rapidapi-key", RAPIDAPI_KEY)
				.addHeader("x-rapidapi-host", RAPIDAPI_HOST)
				.addHeader("Content-Type", "application/json")
				.build();

		// 发送请求并获取响应
		try (Response response = client.newCall(request).execute()) {
			// 检查响应是否成功
			if (!response.isSuccessful()) {
				// 如果响应不成功，返回错误信息
				return ResponseEntity.status(response.code()).body(response.body() != null ? response.body().string() : "error");
			}
			// 返回响应体内容
			return ResponseEntity.ok(response.body() != null ? response.body().string() : "");
		}
	}

	//转义 JSON 字符串中的特殊字符
	private String escapeJson(String input) {
		return input.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
	}



}
