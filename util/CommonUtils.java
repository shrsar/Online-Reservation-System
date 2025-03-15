package com.example.util;

import java.util.Objects;
import java.util.function.Predicate;

public class CommonUtils {

    public static final Predicate<Object> isNotNull = Objects::nonNull;

    public static final Predicate<Object> isNull = Objects::isNull;
}