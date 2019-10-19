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

package com.micro2816.action;

import java.util.Map;

import com.micro2816.api.PermutationIteratorHandler;
import com.micro2816.frame.MainFrame;

/**
 * @see Bean. Pass parameter.
 * @author Micro
 * @since 2019年10月18日 上午10:00:21
 * @version V1.0
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 *
 */
public class PermutationIteratorBean {
    // integers array
    public int []intArray;
    
    // the mapping between digits and letters
    public Map<Integer, char[]> digitMap;
    
    // self-defined handler
    public PermutationIteratorHandler handler;
    
    // window UI
    public MainFrame mainFrame;
    
    // limit number
    public int limitNumber;
}
