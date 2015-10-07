package com.leo.test.gc;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import junit.framework.TestCase;

import org.junit.Test;

public class TestReference extends TestCase {
	@Test
	public void testStrongReference() {
		Object referent = new Object();
		Object strongReference = referent;
		referent = null;
		System.gc();
		
		assertNotNull(strongReference);
	}

	@Test
	public void testSoftReference() {
		String str = "test";
		SoftReference<String> softreference = new SoftReference<String>(str);
		str = null;
		System.gc();
		assertNotNull(softreference.get());
	}

	@Test
	public void testWeakReference() {
		String str = "test";
		WeakReference<String> weakReference = new WeakReference<String>(str);
		str = null;
		System.gc();
		assertNull(weakReference.get());
	}
}
