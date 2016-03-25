package com.capgemini.lbs.framework.utils;

import java.io.File;
import java.io.FilenameFilter;
/**
 * @author anwar
 * 
 */
public class FileFilter implements FilenameFilter{
	private String name;
	private String extension;

	/**
	 * @param filter
	 * @param extn
	 */
	public FileFilter(String filter, String extn) {
		this.name = filter;
		this.extension = extn;
	}

	
	public boolean accept(File directory, String filename) {
		boolean exists = false;
		if (!StringUtil.isStringEmpty(name)){
			exists=filename.startsWith(name);
			if (exists && !StringUtil.isStringEmpty(extension)){
				exists=filename.endsWith(extension);
			}
		}
		return exists;
	}

}