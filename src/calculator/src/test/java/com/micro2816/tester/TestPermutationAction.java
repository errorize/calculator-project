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

package com.micro2816.tester;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.util.IOUtils;
import com.micro2816.action.DefaultPermutationIteratorHandler;
import com.micro2816.action.PermutationAction;
import com.micro2816.action.PermutationIteratorBean;
import com.micro2816.frame.MainFrame;

/**
 * @see 
 * @author Micro
 * @since 2019年10月18日 下午4:17:41
 * @version V1.0
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 *
 */
public class TestPermutationAction {
    
    @Before
    public void before() {
//        E:\workspace\calculator\log4j.properties
        String filePath = "D:\\workspace\\java_projects\\log4j.properties";
        if (!(new File(filePath).exists())) {
            filePath = System.getProperty("user.dir") + File.separator + "log4j.properties";
        }
        MainFrame.initLog4j(filePath);
        BasicConfigurator.configure();
        PropertyConfigurator.configure(filePath);
    }
    
    @Test
    public void testFunction() {
        Map<Integer, char[]> digitMap = new HashMap<Integer, char[]>(8);
        digitMap.put(2, new char[]{'A', 'B', 'C'});
        digitMap.put(3, new char[]{'D', 'E', 'F'});
        digitMap.put(4, new char[]{'G', 'H', 'I'});
        digitMap.put(5, new char[]{'J', 'K', 'L'});
        digitMap.put(6, new char[]{'M', 'N', 'O'});
        digitMap.put(7, new char[]{'P', 'Q', 'R', 'S'});
        digitMap.put(8, new char[]{'T', 'U', 'V'});
        digitMap.put(9, new char[]{'W', 'X', 'Y', 'Z'});
        
        char []chArray1 = digitMap.get(2);
        char []chArray2 = digitMap.get(3);
        for (int i = 0; i < chArray1.length; ++i) {
            for (int j = 0; j < chArray2.length; ++j) {
                System.err.print(chArray1[i] + "" + chArray2[j] + " ");
            }
            
        }
        System.err.println();
        
        PermutationAction pa = new PermutationAction();
        int []intArray = {9};
        PermutationIteratorBean bean = new PermutationIteratorBean();
        bean.digitMap = digitMap;
        bean.intArray = intArray;
        bean.handler = new DefaultPermutationIteratorHandler();
        pa.iterator(bean);
        
    }

    @Test
    public void testLoadingSystemProperties() {
        Logger logger = org.apache.log4j.Logger.getLogger(TestPermutationAction.class);
        String userDir = System.getProperty("user.dir");
        logger.info(userDir);
        
        String filePath = userDir + File.separator + "system.properties";
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            String fromStr = props.getProperty("from", "0");
            if (!fromStr.matches("[0-9]+")) { fromStr = "0"; }
            String toStr = props.getProperty("to", "9");
            if (!toStr.matches("[0-9]+")) { toStr = "9"; }
            int from = Integer.parseInt(fromStr);
            int to = Integer.parseInt(toStr);
            for (int i = from; i < to; ++i) {
                String str = props.getProperty(String.valueOf(i));
                if (str == null || (str = str.trim()).length() == 0) {
                    continue;
                }
                String []strArray = StringUtils.split(str, ',');
                for (String tempStr : strArray) {
                    if (tempStr.length() != 1) {
                        String errorMsg = StringUtils.join("the key ", String.valueOf(i),
                                "system.properties contains illegal value!");
                        throw new IllegalArgumentException(errorMsg);
                    }
                    
                }
            }
            
        } catch (IOException ex) {
            logger.error("failed in loading system.properties", ex);
        } finally {
            IOUtils.close(in);
        }
    }
    
    @Test
    public void testMatcher() {
        String str = "21d 3";
        System.err.println(str.matches("[ 0-9]+"));
        Matcher matcher = PermutationAction.DIGIT_PATTERN.matcher(str);
//        List<Integer> intList = new LinkedList<Integer>();
        System.err.println(matcher.groupCount());
        str = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        System.err.println(str.length());
    }
}
