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

import org.apache.log4j.Logger;

import com.micro2816.api.PermutationIteratorHandler;

/**
 * @see a default handler for permutation iteration
 * @author Micro
 * @since 2019年10月18日 上午10:00:30
 * @version V1.0
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 *
 */
public class DefaultPermutationIteratorHandler implements PermutationIteratorHandler {
    private static final Logger logger = Logger.getLogger(DefaultPermutationIteratorHandler.class);
    @Override
    public void handle(PermutationIteratorBean bean, char []letters) {
//        System.err.print(new String(letters) + " ");
        logger.info(new String(letters));
    }

}
