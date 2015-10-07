package com.leo.test.asm;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 实现一个 methodAdapter 类，用于 修改方法
 * @author Leo
 *
 */
public class AddSecurityCheckMethodAdapter extends MethodAdapter {
	 
	 public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
	   super(mv);
	  }
	           //com/security/SecurityChecker 此点很总要，不能写成com.security.SecurityChecker
	  public void visitCode() {
	   visitMethodInsn(Opcodes.INVOKESTATIC, "com/leo/aop/SecurityChecker",
	   "checkSecurity", "()V");
	  }

	}
