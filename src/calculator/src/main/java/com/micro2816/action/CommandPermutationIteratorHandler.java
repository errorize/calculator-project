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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.util.IOUtils;
import com.micro2816.api.PermutationIteratorHandler;

/**
 * @see This self-defined handler used in handling data while permutation iterating
 * @author Micro
 * @since 2019年10月18日 下午4:19:49
 * @version V1.0
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 *
 */
public class CommandPermutationIteratorHandler implements PermutationIteratorHandler {
    private static volatile CommandPermutationIteratorHandler instance;
    private BufferedWriter writer;
    private int writtenCount = 0;
    
    private CommandPermutationIteratorHandler() {
        
    }
    
    /**
     * @see initialize an instance of BufferedWriter
     * @author Micro
     * @since 2019年10月19日 上午7:24:19
     * @param filePath
     * @throws Exception
     * @return void
     */
    public void begin(String filePath) throws Exception {
        File outFile = new File(filePath);
        writer = new BufferedWriter(new FileWriter(outFile));
        writtenCount = 0;
    }
    
    /**
     * @see close BufferedWriter instance and collect all garbage
     * @author Micro
     * @since 2019年10月19日 上午7:22:54
     * @return void
     */
    public void dispose() {
        if (writer != null) {
            try {
                writer.flush();
            } catch (IOException e) {
            }
        }
        IOUtils.close(writer);
        writer = null;
        writtenCount = 0;
    }
    
    /**
     * @see Get the handler instance
     * @author Micro
     * @since 2019年10月19日 上午7:25:25
     * @return
     * @return CommandPermutationIteratorHandler
     */
    public static CommandPermutationIteratorHandler getInstance() {
        if (instance == null) {
            synchronized (CommandPermutationIteratorHandler.class) {
                instance = new CommandPermutationIteratorHandler();
            }
        }
        return instance;
    }
    
    @Override
    public void handle(PermutationIteratorBean bean, char[] letters) {
        try {
            writer.write(new String(letters));
            ++writtenCount;
            if (writtenCount % 20 == 0) {  // output newline character per 20 writing
                writer.write("\r\n");
            }
            else {
                writer.write(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
