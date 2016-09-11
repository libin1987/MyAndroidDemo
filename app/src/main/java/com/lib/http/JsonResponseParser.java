package com.lib.http;

import java.lang.reflect.Type;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import com.alibaba.fastjson.JSON;

public class JsonResponseParser implements ResponseParser {
	// 检查服务器返回的响应头信息
	@Override
	public void checkResponse(UriRequest request) throws Throwable {
	
	}

	/**
	 * 转换result为resultType类型的对象
	 * 
	 * @param resultType
	 *            返回值类型(可能带有泛型信息)
	 * @param resultClass
	 *            返回值类型
	 * @param result
	 *            字符串数据
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
//		SuccOrError succOrError = new Gson().fromJson(result, SuccOrError.class);
//		if (succOrError.getSucc().equals("false")) {
//			throw new Throwable(succOrError.getMsg());
//		}
//		if (List.class.isAssignableFrom(resultClass)) {
//			return JSON.parseArray(result,
//					(Class<?>) ParameterizedTypeUtil.getParameterizedType(resultType, List.class, 0));
//		} else {
			return  JSON.parseObject(result, resultClass);
//		}
	}
}
