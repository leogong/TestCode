package com.leo.test.htmlparser;

import org.htmlparser.tags.CompositeTag;

public class EmTag extends CompositeTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8678174509641137991L;
	private static final String mIds[] = { "em" };
	private static final String mEndTagEnders[] = { "em" };

	public EmTag() {
	}

	public String[] getIds() {
		return mIds;
	}

	public String[] getEndTagEnders() {
		return mEndTagEnders;
	}
}
