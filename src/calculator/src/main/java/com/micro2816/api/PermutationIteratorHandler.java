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

package com.micro2816.api;

import com.micro2816.action.PermutationIteratorBean;

/**
 * @see the handler interface will be call while permutation iterates
 * @author Micro
 * @since 2019年10月18日 下午4:17:41
 * @version V1.0
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 *
 */
public interface PermutationIteratorHandler {
    
    void handle(PermutationIteratorBean bean, char []letters);
}
