package com.wen.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * JackSon 工具类.
 * 
 * @author huwenwen
 * @param <T>
 *
 */
public class JsonUtil<T> {

	/**
	 * 
	 * @param model
	 * @return
	 */
	public static <T> String toJson(T model) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(model);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public static <T> byte[] toJsonByte(T model) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsBytes(model);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static <T> T fromModel(String json, Class<T> classOfT) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return (T) objectMapper.readValue(json, classOfT);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 将json字符串转化为对象数组
	 * @param json
	 * @param classOfT
	 * @return List<classOfT>
	 */
	public static <T> List<T> toModelArr(String json, Class<T> classOfT) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			TypeFactory typeFactory = objectMapper.getTypeFactory();
			return objectMapper.readValue(json, typeFactory.constructCollectionType(List.class, classOfT));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static <T> T fromModelUseByte(byte[] userBytes, Class<T> classOfT) {
		try {
			return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), classOfT);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> fromMap(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, Map.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}
