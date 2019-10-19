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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.util.IOUtils;
import com.micro2816.common.ValidationResult;


/**
 * @see permutation for outputting letters
 * @author Micro
 * @since 2019年10月18日 上午9:50:06
 * @version V1.0
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 *
 */

public class PermutationAction {
    public static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]+");
    private Map<Integer, char[]> digitsLettersMap;
    private int from, to, limitNumber = 603979768;
	/**
	 * @see 
	 * @author Micro
	 * @since 2019年10月18日 上午9:52:27
	 * @param intArray
	 * @param digitMap
	 * @return
	 * @return int
	 */
	public int iterator(PermutationIteratorBean bean) {
	    int []intArray = bean.intArray;
	    Map<Integer, char[]> digitMap = bean.digitMap;
		if (intArray == null || intArray.length == 0) {
			throw new IllegalArgumentException("intArray is an illegal argument");
		}
		if (bean.handler == null) {
		    bean.handler = new DefaultPermutationIteratorHandler();
		}
		// store char array into memory
		final List<char[]> charList = new ArrayList<char[]>(intArray.length);
		char []temp;
		for(int i = 0; i < intArray.length; ++i) {
			temp = digitMap.get(intArray[i]);
			if (temp == null) { continue; }
			charList.add(temp);
		}

		final char []chArray = new char[charList.size()];
		iterator(bean, charList, chArray, 0);
		return 0;
	}
	
	/**
	 * @see it is a private method used in recursion.
	 * @author Micro
	 * @since 2019年10月18日 上午10:20:17
	 * @param bean pass the handler to the method.
	 * @param charList a list of mapping letters' array.
	 * @param chArray target character array at some level
	 * @param idx index, somehow it means level of the recursion.
	 * @return void
	 */
	private void iterator(PermutationIteratorBean bean, List<char[]> charList, char []chArray, int idx) {
		int less = charList.size() - 1;
		if (idx == less) {
			char []charArray = charList.get(idx);
			for(int i = 0; i < charArray.length; ++i) {
				chArray[idx] = charArray[i];
				bean.handler.handle(bean, chArray);
			}
		}
		else if (idx < less) {
			char []charArray = charList.get(idx);
			for(int i = 0; i < charArray.length; ++i) {
				chArray[idx] = charArray[i];
				iterator(bean, charList, chArray, idx + 1);
			}
		}
	}

	/**
	 * @see load system.properties and convert it to a HashMap
	 * @author Micro
	 * @since 2019年10月18日 上午11:15:47
	 * @return
	 * @throws Exception
	 * @return Map<Integer,char[]>
	 */
	public Map<Integer, char[]> loadSystemProperties(String filePath) throws Exception {
	    if (StringUtils.isBlank(filePath)) {
    	    String userDir = System.getProperty("user.dir");
            filePath = userDir + File.separator + "system.properties";
	    }
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            String fromStr = props.getProperty("from", "0");
            if (!fromStr.matches("[0-9]+")) { fromStr = "0"; }
            String toStr = props.getProperty("to", "9");
            if (!toStr.matches("[0-9]+")) { toStr = "9"; }
            from = Integer.parseInt(fromStr);
            to = Integer.parseInt(toStr);
            
            String limitNumberStr = props.getProperty("limitNumber", "603979768");
            if (!limitNumberStr.matches("[0-9]+")) { limitNumberStr = "603979768"; }
            limitNumber = Integer.parseInt(limitNumberStr);
            
            Map<Integer, char[]> returnMap = new HashMap<Integer, char[]>(to - from + 1);
            for (int i = from, j; i <= to; ++i) {
                String str = props.getProperty(String.valueOf(i));
                if (str == null || (str = str.trim()).length() == 0) {
                    continue;
                }
                String []strArray = StringUtils.split(str, ',');
                char []charArray = new char[strArray.length];
                for (j = 0; j < strArray.length; ++j) {
                    String tempStr = strArray[j];
                    if (tempStr.length() != 1) {
                        String errorMsg = StringUtils.join("the key ", String.valueOf(i),
                                "system.properties contains illegal value!");
                        throw new IllegalArgumentException(errorMsg);
                    }
                    charArray[j] = tempStr.charAt(0);
                }
                returnMap.put(i, charArray);
            }
            digitsLettersMap = returnMap;
            return returnMap;
        } catch (IOException ex) {
            throw ex;
        } finally {
            IOUtils.close(in);
        }
	}
	
	public Map<Integer, char[]> getDigitsLettersMap() {
	    return this.digitsLettersMap;
	}
	
	public int getLimitNumber() {
	    return limitNumber;
	}

	/**
	 * @see validate whether the text is ok.
	 * @author Micro
	 * @since 2019年10月19日 上午7:37:23
	 * @param text
	 * @return
	 * @return ValidationResult
	 */
    public ValidationResult validate(String text) {
        ValidationResult result = new ValidationResult();
        result.setStatus(true);
        if (StringUtils.isBlank(text)) {
            result.setStatus(false);
            result.setMessage("input message is blank!");
            return result;
        }
        if (!text.matches("[ 0-9]+")) {
            result.setStatus(false);
            result.setMessage("input message contains non-numberic character!");
            return result;
        }
        Matcher matcher = DIGIT_PATTERN.matcher(text);
        List<Integer> intList = new LinkedList<Integer>();
        int temp = 0;
        while (matcher.find()) {
            String numStr = matcher.group();
            temp = Integer.parseInt(numStr);
            if (temp > to || temp < from) {
                result.setStatus(false);
                result.setMessage("input message contains number " + numStr 
                        + " which is incorrect. the integers must vary from " + from + " to " + to);
                return result;
            }
            intList.add(temp);
        }
        final int intArray[] = new int[intList.size()];
        temp = 0;
        for (Integer num : intList) {
            intArray[temp++] = num.intValue();
        }
        result.setData(intArray);
        return result;
    }
    
}
