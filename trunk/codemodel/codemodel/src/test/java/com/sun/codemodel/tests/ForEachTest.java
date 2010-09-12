package com.sun.codemodel.tests;
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

import java.util.ArrayList;
import com.sun.codemodel.*;
import com.sun.codemodel.writer.SingleStreamCodeWriter;

/**
 * 
 * Simple program to test the generation of the enhanced for loop in jdk 1.5
 * 
 * @author Bhakti Mehta Bhakti.Mehta@sun.com
 * 
 */

public class ForEachTest {

	public static void main(String[] args) throws Exception {

		JCodeModel cm = new JCodeModel();
		JDefinedClass cls = cm._class("Test");

		JMethod m = cls.method(JMod.PUBLIC, cm.VOID, "foo");
		m.body().decl(cm.INT, "getCount");

		// This is not exactly right because we need to
		// support generics
		JClass arrayListclass = cm.ref(ArrayList.class);
		JVar $list = m.body().decl(arrayListclass, "alist",
				JExpr._new(arrayListclass));

		JClass $integerclass = cm.ref(Integer.class);
		JForEach foreach = m.body().forEach($integerclass, "count", $list);
		JVar $count1 = foreach.var();
		foreach.body().assign(JExpr.ref("getCount"), JExpr.lit(10));

		// printing out the variable
		JFieldRef out1 = cm.ref(System.class).staticRef("out");
		// JInvocation invocation =
		foreach.body().invoke(out1, "println").arg($count1);

		cm.build(new SingleStreamCodeWriter(System.out));
	}
}