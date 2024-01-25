package org.mql.java.controller.processing;

import java.util.HashSet;
import java.util.Set;

import org.mql.java.controller.core.ClassAnalyzer;
import org.mql.java.models.ModMethod;

public class MethodParser {

	public Set<ModMethod> parseMethodsAndConstructors(Class<?> targetClass) {
		Set<ModMethod> methods = new HashSet<>();
		methods.addAll(ClassAnalyzer.parseConstructors(targetClass));
		methods.addAll(ClassAnalyzer.parseMethods(targetClass));
		return methods;
	}
}