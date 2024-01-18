package org.mql.java.controller;


import org.mql.java.models.ModMethod;

import java.util.HashSet;
import java.util.Set;

public class MethodParser {

    public Set<ModMethod> parseMethodsAndConstructors(Class<?> targetClass) {
        Set<ModMethod> methods = new HashSet<>();
        methods.addAll(ClassAnalyzer.parseConstructors(targetClass));
        methods.addAll(ClassAnalyzer.parseMethods(targetClass));
        return methods;
    }
}