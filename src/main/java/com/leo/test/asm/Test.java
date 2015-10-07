package com.leo.test.asm;

import java.io.File;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * 为AccountAsm类的operation方法添加 安全检查方法
 * 读取原来的AccountAsm.class文件，并为operation方法前添加SecurityChecker.checkSecurity()，然后
 * 生产新的class文件
 * 
 * @author Leo
 * 
 */
public class Test {

	public static void main(String[] args) throws Exception {
		ClassReader cr = new ClassReader("com.leo.aop.AccountAsm");
		ClassWriter cw = new ClassWriter(1);
		ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
		cr.accept(classAdapter, 1);
		byte[] data = cw.toByteArray();
		String classPath = (AccountAsm.class.getResource("/").toString()
				+ AccountAsm.class.getCanonicalName().replace(".",
						File.separator) + ".class").substring(6);
		File file = new File(classPath);
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(data);
		fout.close();
	}
}
