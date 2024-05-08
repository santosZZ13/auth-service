package org.authen.util.error;

import lombok.Getter;

import java.util.*;

@Getter
public class ErrorCode {

	private final Map<String, List<String>> errorMsg;
	private final List<FieldErrorWrapper> fieldErrorWrappers;

	public ErrorCode() {
		this.fieldErrorWrappers = new ArrayList<>();
		this.errorMsg = new LinkedHashMap<>();
	}

	public int errorCount() {
		return this.errorMsg.keySet().size();
	}


	public void addErrorField(String errorCode, String field, String message) {
		FieldErrorWrapper fieldErrorWrapper = new FieldErrorWrapper();
		fieldErrorWrapper.setErrorCode(errorCode);
		fieldErrorWrapper.setField(field);
		fieldErrorWrapper.setMessage(message);
		this.fieldErrorWrappers.add(fieldErrorWrapper);
	}

	public void addError(String errorCode, String errorMsg) {
		assert errorCode != null;
		assert errorMsg != null;
		if (this.errorMsg.containsKey(errorCode)) {
			this.errorMsg.get(errorCode).add(errorMsg);
		} else {
			this.errorMsg.put(errorCode, new ArrayList<>() {
				{
					add(errorMsg);
				}
			});
		}
	}

	public Map<String, String> getErrorListFromMap() {
		Map<String, String> errorList = new LinkedHashMap<>();
		Set<String> keySet = this.errorMsg.keySet();
		for (String key : keySet) {
			List<String> values = this.errorMsg.get(key);
			for (String value : values) {
				errorList.put(key, value);
			}
		}
		return errorList;
	}

	public List<String> getErrorList() {
		List<String> errorList = new ArrayList<>();
		Map<String, String> errorListFromMap = this.getErrorListFromMap();
		for (String key : errorListFromMap.keySet()) {
			errorList.add(errorListFromMap.get(key));
		}
		return errorList;
	}

}
