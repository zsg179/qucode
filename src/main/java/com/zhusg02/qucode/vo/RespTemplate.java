package com.zhusg02.qucode.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class RespTemplate {

	@JSONField
	private String code;
	@JSONField
	private String message;
	@JSONField
	private String data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}



}
