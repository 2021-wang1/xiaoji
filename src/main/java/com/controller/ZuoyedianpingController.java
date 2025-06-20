package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.entity.pojo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;


import com.entity.ZuoyedianpingEntity;
import com.entity.view.ZuoyedianpingView;

import com.service.ZuoyedianpingService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import org.springframework.util.ResourceUtils;
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
	private static final String RAPIDAPI_KEY = "7bf88cfa73mshcafa5c6284bc584p14aec4jsnf3c65f675779"; // 替换为你的 RapidAPI 密钥
	private static final String RAPIDAPI_HOST = "judge0-ce.p.rapidapi.com";

	@Autowired
	private ObjectMapper objectMapper;

	private static final OkHttpClient client;

	static {
		client = new OkHttpClient();
	}


	@PostMapping("/run-code")
	public ResponseEntity<String> judgeCode(@RequestBody JudgeRequest dto) throws IOException {
		// 使用 OkHttp 发送 POST 请求
		MediaType mediaType = MediaType.parse("application/json"); // 设置请求体的媒体类型为 JSON
		okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, objectMapper.writeValueAsString(dto)); // 将请求体转换为 JSON 字符串
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

	private static final String API_URL = "https://api.siliconflow.cn/v1/chat/completions";
	private static final String API_TOKEN = "sk-ertqufmwrljhhslttijvsyzmbwslvhywzrriaeymcyhgxgov";

	@PostMapping("/ai-score")
	public ChatVO getAiScore(@RequestBody ChatDTO dto) throws IOException {
		//设置提示词和问题
		String content = "我需要你帮我给主观题打分,我发给你的数据格式为"+ChatDTO.getFormat()
				+"你需要根据题目和学生给出的答案,客观的给出得分,比如现在这个score为10,你需要给出0-10的分值" +
				",可以有0.5分,你输出的结果需要符合"+ ChatVO.getFormat()+"判题不要过于严格,如果学生未给出作答，则直接0分;现在开始;;;"
				+objectMapper.writeValueAsString(dto);
		//设置模型
		Model model = new Model();
		model.setMessages(Collections.singletonList(new Model.Messages(content)));
		String jsonBody = objectMapper.writeValueAsString(model);
		//构建请求
		okhttp3.RequestBody body = okhttp3.RequestBody.create(jsonBody, MediaType.parse("application/json"));
		Request httpRequest = new Request.Builder()
				.url(API_URL)
				.post(body)
				.addHeader("Authorization", "Bearer " + API_TOKEN) //设置key
				.addHeader("Content-Type", "application/json")
				.build();
		//发起请求
		try (Response response = client.newCall(httpRequest).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			//解析结果
			String responseBody = response.body().string();
			ModelResponse modelResponse = objectMapper.readValue(responseBody, ModelResponse.class);
			String c = modelResponse.getChoices().get(0).getMessage().getContent();
			//返回结果
			return objectMapper.readValue(c, ChatVO.class);
		}
	}

	@PostMapping("/getWorkScore")
	public ChatVO getWorkScore(@RequestParam String fileName) throws Exception {
		// 1. 定位 upload 目录
		File path = new File(ResourceUtils.getURL("classpath:static").getPath());
		if (!path.exists()) {
			path = new File("");
		}
		File upload = new File(path.getAbsolutePath(), "/upload/");
		if (!upload.exists()) {
			upload.mkdirs();
		}

		// 2. 查找目标文件
		File file = new File(upload.getAbsolutePath(), fileName);
		if (!file.exists()) {
			throw new FileNotFoundException("文件不存在：" + file.getAbsolutePath());
		}

		// 3. 检查文件后缀名
		String lowerName = file.getName().toLowerCase();
		String textContent;

		if (lowerName.endsWith(".docx")) {
			try (FileInputStream fis = new FileInputStream(file);
				 XWPFDocument docx = new XWPFDocument(fis);
				 XWPFWordExtractor extractor = new XWPFWordExtractor(docx)) {
				textContent = extractor.getText();
			}
		} else if (lowerName.endsWith(".doc")) {
			try (FileInputStream fis = new FileInputStream(file);
				 HWPFDocument doc = new HWPFDocument(fis);
				 WordExtractor extractor = new WordExtractor(doc)) {
				textContent = extractor.getText();
			}
		} else {
			throw new IllegalArgumentException("仅支持 .doc 或 .docx 文件。当前文件：" + file.getName());
		}
		String content = "我需要你帮我给学生作业打分,我发给你的数据格式为学生上传的word文档文本,你需要根据题目和学生给出的答案,客观的给出得分" +
				",题目边上一般会有分值,如果同时有多题,则每题的得分都需要符合给出的分值;最终返回的分值为所有题目得分的总和;可以有0.5分,你输出的结果需要符合"+ ChatVO.getFormat()+"判题不要过于严格" +
				",如果学生未给出作答，则直接0分;如果有多个题目,则给出每题的评分原因;现在开始;;;"+textContent;
		Model model = new Model();
		model.setMessages(Collections.singletonList(new Model.Messages(content)));
		String jsonBody = objectMapper.writeValueAsString(model);
		//构建请求
		okhttp3.RequestBody body = okhttp3.RequestBody.create(jsonBody, MediaType.parse("application/json"));
		Request httpRequest = new Request.Builder()
				.url(API_URL)
				.post(body)
				.addHeader("Authorization", "Bearer " + API_TOKEN) //设置key
				.addHeader("Content-Type", "application/json")
				.build();
		try (Response response = client.newCall(httpRequest).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			//解析结果
			String responseBody = response.body().string();
			ModelResponse modelResponse = objectMapper.readValue(responseBody, ModelResponse.class);
			String c = modelResponse.getChoices().get(0).getMessage().getContent();
			//返回结果
			return objectMapper.readValue(c, ChatVO.class);
		}
	}

}
