package com.cg.framework.bcun.jnlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class JarUpdate {
	
	JarUpdate(){
		try{
		updateJar();
		}catch(Exception e){
			 System.out.println("########ERROR at JarUpdate #######Please contact IT department ##############");
		}
	}
	/**
	 * main()
	 */
	public  void updateJar()throws IOException  {
		// Get the jar name and entry name from the command-line.

		
		String jarName = FileLocator.getWarFileLocation()+"udaan-web.war";
		String jdbcPropsFile = FileLocator.getWarFileLocation()+"jdbc.properties";
		String fileName = "jdbc.properties";

		// Create file descriptors for the jar and a temp jar.

		File jarFile = new File(jarName);
		File tempJarFile = new File(jarName + ".tmp"+System.currentTimeMillis());

		// Open the jar file.

		JarFile jar = new JarFile(jarFile);
		System.out.println(jarName + " opened.");

		// Initialize a flag that will indicate that the jar was updated.

		boolean jarUpdated = false;

		try {
			// Create a temp jar file with no manifest. (The manifest will
			// be copied when the entries are copied.)

			Manifest jarManifest = jar.getManifest();
			JarOutputStream tempJar =
					new JarOutputStream(new FileOutputStream(tempJarFile));

			// Allocate a buffer for reading entry data.

			byte[] buffer = new byte[10024];
			int bytesRead;

			try {
				/*// Open the given file.

            FileInputStream file = new FileInputStream(fileName);

            try {
               // Create a jar entry and add it to the temp jar.

               JarEntry entry = new JarEntry(fileName);
               tempJar.putNextEntry(entry);

               // Read the file and write it to the jar.

               while ((bytesRead = file.read(buffer)) != -1) {
                  tempJar.write(buffer, 0, bytesRead);
               }

               System.out.println(entry.getName() + " added.");
            }
            finally {
               file.close();
            }*/

				// Loop through the jar entries and add them to the temp jar,
				// skipping the entry that was added to the temp jar already.
				for (Enumeration entries = jar.entries(); entries.hasMoreElements(); ) {
					// Get the next entry.

					JarEntry entry = (JarEntry) entries.nextElement();

					// If the entry has not been added already, add it.

					if (! entry.getName().contains(fileName)) {
						// Get an input stream for the entry.

						InputStream entryStream = jar.getInputStream(entry);

						// Read the entry and write it to the temp jar.

						tempJar.putNextEntry(entry);

						while ((bytesRead = entryStream.read(buffer)) != -1) {
							tempJar.write(buffer, 0, bytesRead);
						}
					}else{
						//update file 
						System.out.println("Matching");
						//update file 
						String entryPath=entry.getName();
						System.out.println(entryPath);
						File f= new File(jdbcPropsFile);
						/* Properties props = new Properties();
            	   props.load(entryStream);

            	    ClassLoader cLoader = ClassLoader.getSystemClassLoader();  

            	   props.setProperty("branch.office.code", "sami");
            	   File f= new File("D:\\properties\\"+entryPath);
            	   File directory= new File(f.getParent());
            	   if(!directory.exists()){
            		   directory.mkdirs();
            		   }
            	 Writer fw= new FileWriter(f);

            	   props.store(fw,"");
            	   fw.close();*/


						// Read the entry and write it to the temp jar.
						FileInputStream fis = new FileInputStream(f);

						//Zip stream for create a zip file
						ZipEntry entry1 = new ZipEntry(entryPath);
						tempJar.putNextEntry(entry1);
						while ((bytesRead = fis.read(buffer)) != -1) {
							tempJar.write(buffer, 0, bytesRead);
						}

						fis.close();
						System.out.println(entryPath +"Close");
					}
				}

				jarUpdated = true;
			}
			catch (Exception ex) {
				System.out.println(ex);

				// Add a stub entry here, so that the jar will close without an
				// exception.
				System.out.println("Please contact IT Depatment");
				
			}
			finally {
				tempJar.close();
			}
		}
		finally {
			jar.close();
			System.out.println(jarName + " closed.");

			// If the jar was not updated, delete the temp jar file.

			if (! jarUpdated) {
				tempJarFile.delete();
			}
		}

		// If the jar was updated, delete the original jar file and rename the
		// temp jar file to the original name.

		if (jarUpdated) {
			jarFile.delete();
			tempJarFile.renameTo(jarFile);
			System.out.println(jarName + " updated.");
		}
	}
}