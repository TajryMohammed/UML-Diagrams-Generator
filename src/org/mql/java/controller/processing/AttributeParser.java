package org.mql.java.controller.processing;

import org.mql.java.controller.core.ClassAnalyzer;
import org.mql.java.models.ModEntity;

public class AttributeParser {

	public void parseAttributes(Class<?> targetClass, ModEntity model) throws Exception {
		model.setEntityAttributes(ClassAnalyzer.parseAttributes(targetClass));
	}
}