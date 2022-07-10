package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserCSVItemProcessor implements ItemProcessor<User, User> {

	private static final Map<String, String> deptMap = new HashMap<>();

	public UserCSVItemProcessor() {
		deptMap.put("100", "Technology");
		deptMap.put("200", "Finance");
		deptMap.put("300", "Marketing");
	}

	@Override
	public User process(User item) throws Exception {
		String deptCode = item.getDept();
		String deptDesc = deptMap.get(deptCode);
		if (deptDesc != null) {
			item.setDept(deptDesc);
		} else {
			item.setDept("Invalid Dept Code");
		}
		return item;
	}

}
