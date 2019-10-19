/*
 * JDK's License:
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * The program's License:
 * Since the program obeys to Apache License. Please see more details about Apache License.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.micro2816.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @see the utilities for command
 * @author Micro
 * @since 2019年10月18日 上午9:50:06
 * @version V1.0
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 *
 */

public class CommandUtils {
    public static Map<String, String> parseArguments(String []args) {
        if (args == null || args.length == 0) { return new HashMap<String, String>(0); }
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < args.length; ++i) {
            if (args[i].startsWith("--")) {
                if (i + 1 < args.length && (!args[i + 1].startsWith("--"))) {
                    map.put(args[i], args[i + 1]);
                    ++i;
                } else {
                    map.put(args[i], "");
                }
            }
        }
        return map;
    }
}
