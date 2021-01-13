/**
 *  all right reserved by Hefei Xinhuan Intelligence co., ltd
 *  contact us at https://github.com/lqian/light-lpr
 *
 */
package com.xinhuan.llpr.service.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController; 

 
@RestController
@Configuration
public class DemoAPI {
	
	static Logger logger = LoggerFactory.getLogger(DemoAPI.class.getName());
	
	@PostMapping( value = "/licensePlateInfoAction", 
			produces = "application/json;charset=UTF-8")
	@ResponseBody	
	@CrossOrigin
	public String licensePlateInfoAction(@RequestBody Map<String, Object> licensePlateInfo) {	
		
		//产品序列号
		logger.info("productNo: {}", licensePlateInfo.get("productNo"));
		//固件代号
		logger.info("firmware: {}", licensePlateInfo.get("firmware"));
		//固件版本
		logger.info("version: {}", licensePlateInfo.get("version"));
		//次版本号
		logger.info("minorVersion: {}", licensePlateInfo.get("minorVersion"));
		
		//设备名称
		logger.info("deviceName: {}", licensePlateInfo.get("deviceName"));
		//设备IP
		logger.info("ip: {}", licensePlateInfo.get("ip"));
		
		//图片文件名
		logger.info("fileName: {}", licensePlateInfo.get("fileName"));
		
		//车牌识别置信度
		logger.info("confidence: {}", licensePlateInfo.get("confidence"));
		
		//车牌号码
		logger.info("plateNo: {}", licensePlateInfo.get("plateNo"));
		
		 //车牌颜色： 未知、白、黄、蓝、黑、绿、黄绿
		logger.info("plateColor: {}", licensePlateInfo.get("plateColor"));
		
		//车牌类型
		//10 军 11 双层军牌 12 武警 13 双层武警牌 14 警 15 应急 16 新电动车
		//20 货车 21 教练车牌 22 双层黄牌 23 挂车车牌
		//30 蓝色小汽车
		//40 使馆牌 41 领馆牌 42 粤港澳牌 43 黑色小汽车
		//50 民航 51 新能源 52 农用车牌 53 电动车牌（非机动）
		//60 大型新能源
		logger.info("plateType: {}", licensePlateInfo.get("plateType"));
		
		//车牌位置 {xmin, ymin, xmax, ymax}JSON对象
		logger.info("platePosition: {}", licensePlateInfo.get("platePosition"));
		
		//时间戳，从1970-01-01开始的秒数
		logger.info("timestamp: {}", licensePlateInfo.get("timestamp"));
		
		//base64编码的图像
		String base64Image = (String) licensePlateInfo.get("base64image");
		byte[] bytes = Base64.getDecoder().decode(base64Image);
		
		String fileName = licensePlateInfo.get("fileName").toString();
		try {			
			int i = fileName.lastIndexOf("/");
			if (i != -1) {
				Path dir = Paths.get(fileName.substring(0, i));
				if (!Files.exists(dir)) {
					Files.createDirectories(dir);
				}
			}
			
			OutputStream os = Files.newOutputStream(Paths.get(fileName));
			os.write(bytes);
			os.flush();
			os.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		//返回success，告诉相机服务端处理成功，否则认为处理不成功
		return "success";
	}

}
