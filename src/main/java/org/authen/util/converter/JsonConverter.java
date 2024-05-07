package org.authen.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonConverter {
	public static void convertToJsonAndSave(Object object) {

		File file = new File("./src/main/resources/data.json");
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			objectMapper.writeValue(file, object);
		} catch (IOException e) {
			throw new RuntimeException("Failed to convert and save object to JSON", e);
		}
	}

//
//	public static <T> T fromJson(String json, Class<T> clazz) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			return objectMapper.readValue(json, clazz);
//		} catch (IOException e) {
//			throw new RuntimeException("Failed to convert JSON to object", e);
//		}
//	}

//	public static <T> List<T> fromJsonToList(String path, Class<T> clazz) {
//		if (!new File(path).exists()) {
//			throw new RuntimeException("File not found");
//		}
//
//		if (Objects.isNull(clazz)) {
//			throw new RuntimeException("Class is null");
//		}
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			return objectMapper.readValue(new File(path), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
//		} catch (IOException e) {
//			throw new RuntimeException("Failed to convert JSON to object", e);
//		}
//	}


	public static <T> T convertJsonToObject(String json, Class<T> clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException("Failed to convert JSON to object", e);
		}
	}


	public static String convertToJsonString(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new RuntimeException("Failed to convert object to JSON string", e);
		}
	}

}
