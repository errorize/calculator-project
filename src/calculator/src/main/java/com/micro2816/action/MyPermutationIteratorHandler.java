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

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.micro2816.api.PermutationIteratorHandler;
import com.micro2816.frame.MainFrame;

/**
 * @see This self-defined handler used in handling data while permutation iterating
 * @author Micro
 * @since 2019年10月18日 下午4:19:49
 * @version V1.0
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 *
 */
public class MyPermutationIteratorHandler implements PermutationIteratorHandler {
    private StringBuilder result;
    private static volatile MyPermutationIteratorHandler instance;
    private String fileDirectory;
    private boolean hasWrittenToDisk = false;
    
    /**
     * constructor
     */
    private MyPermutationIteratorHandler() {
        result = new StringBuilder();
    }
    
    /**
     * @see Get the handler instance
     * @author Micro
     * @since 2019年10月19日 上午7:32:18
     * @return
     * @return MyPermutationIteratorHandler
     */
    public static MyPermutationIteratorHandler getInstance() {
        if (instance == null) {
            synchronized (MyPermutationIteratorHandler.class) {
                instance = new MyPermutationIteratorHandler();
            }
        }
        return instance;
    }
    
    /**
     * @see reset the value of some global variables.
     * @author Micro
     * @since 2019年10月19日 上午7:32:40
     * @param newLength
     * @return void
     */
    public void setLength(int newLength) {
        result.setLength(newLength);
        fileDirectory = null;
        hasWrittenToDisk = false;
    }
    
    @Override
    public void handle(PermutationIteratorBean bean, char[] letters) {
        
        // if the number of characters in StringBuilder beyond the limit number,
        // it will persist data into disk.
        if (result.length() + letters.length + 1 > bean.limitNumber) {
            if (StringUtils.isBlank(fileDirectory) || (!new File(fileDirectory).exists())) {
                JOptionPane.showMessageDialog(bean.mainFrame, "system automatically finds that too many items will be output.\r\nso system will persist data into disk!\r\nAfter tip dialog disappears, choose preferred directory please.", "warning tip!", 0);
                bean.mainFrame.getJFileChooser().showDialog(bean.mainFrame, "Choose preferred directory"); 
                File fileDir = bean.mainFrame.getJFileChooser().getSelectedFile();
                if (fileDir == null || (!fileDir.exists())) {
                    String msg = "no directory is chosen.";
                    bean.mainFrame.appendMessage(msg);
                    throw new IllegalArgumentException(msg);
                }
                fileDirectory = fileDir.getAbsolutePath();
            }
            
            String filePath = fileDirectory + File.separator + "calc-" + System.currentTimeMillis() + ".txt";  
            try {
                FileUtils.write(new File(filePath), result.toString());
                hasWrittenToDisk = true;
            } catch (IOException e1) {
                String msg = "failed write log message to disk!";
                MainFrame.logger.error(msg, e1);
                JOptionPane.showMessageDialog(bean.mainFrame, msg, "error tip!", 0);
            } finally {
                result.setLength(0);
            }
        }
        result.append(new String(letters)).append(' ');
    }

    /**
     * @see get the result content after permutation iterates
     * @author Micro
     * @since 2019年10月18日 下午4:40:33
     * @return String
     */
    public String getContent() {
        if (hasWrittenToDisk) {
            String filePath = fileDirectory + File.separator + "calc-" + System.currentTimeMillis() + ".txt";  
            try {
                FileUtils.write(new File(filePath), result.toString());
            } catch (IOException e1) {
                String msg = "failed write log message to disk!";
                MainFrame.logger.error(msg, e1);
                JOptionPane.showMessageDialog(null, msg, "error tip!", 0);
            } finally {
                result.setLength(0);
            }
            result.append("system has persisted data into disk whose path is ").append(fileDirectory);
        }
        
        // format output result, that helps people to check the result.
        int factor = 48;
        int idx = 2, t;
        if (result.length() <= factor) {
            return result.toString();
        }
        char ch = result.charAt(factor);
        if (ch == ' ') {
            result.setCharAt(factor, '\n');
        } else {
            factor = result.lastIndexOf(" ", factor);
            result.setCharAt(factor, '\n');
        }
        ++factor;  // skip newline character
        t = factor * idx - 1;
        while (t < result.length()) {
            result.setCharAt(t, '\n');
            t += factor;
        }
        return result.toString();
    }
}
