import java.util.*;
import java.lang.*;
import java.io.*;

/** This function extracts the controller from a directive file with an embedded controller.
	In order to properly function:
	- The controller function's parameters must be listed in one line in the angular array notation
	- The controller function should start at the same line as the directive.controller attribute is declared 
		(This is how the function decides where to start parsing for the controller)
	- The end of the controller needs to be marked with "END OF CONTROLLER FUNCTION".
	The controller function is added to the app_controllers module as a controller.

	The function takes 3 arguments as parameters: 
	1st argument is the source file, 
	2nd argument it the destination file, 
	3rd argument is the name of the controller.
	
	The function will throw a FileNotFound Exception if a valid source was not provided.
	The function will overwrite the existing distination a file.
	The function will use "NO_CONTROLLER_NAME_GIVEN" as controller name is no name is given.
*/
class ControllerExtractor {

	public static void main(String [] args) {

		String controllerName;

		// Ensure That a controller file name is given
		if (args.length == 3){
			controllerName = args[2];

		} else {
			controllerName = "NO_CONTROLLER_NAME_GIVEN";
		}

		try {
			String resultPath = parseFile(args[0], args[1], controllerName);

		} catch (Exception e) {
	        
    		System.out.println("FileNotFound Exception\n");
	    }

	}

	private static String parseFile(String inputPath, String outputPath, String controllerName) throws Exception {

		try {

	 		File file = new File(inputPath);
			Scanner input = new Scanner(file);
			PrintWriter writer = new PrintWriter(outputPath, "UTF-8");

			boolean start = false;

			// Loop through file till end of controller is found
			while(input.hasNext()) {

			    String nextLine = input.nextLine();

			    // If the controller function parsing has started, write to output
			    if (start){

					writer.println(nextLine);

			    	// Check for end of controller function
			    	if (nextLine.indexOf("END OF CONTROLLER FUNCTION") > -1){
			    		start = false;
						writer.println(");");
			    		break;
			    	}
			    
			    } else if ((nextLine.indexOf("controller:") > -1) || nextLine.indexOf("controller :") > -1) {
			    	// Check for the start of the controller function
			    	if (nextLine.indexOf("function") > -1){
						start = true;

						// Find the start of the array to start parsing the controller function
						int startOfFunctionIndex = nextLine.indexOf("[");
						nextLine = nextLine.substring(startOfFunctionIndex);

						// Pad the start of the controller function to assign it to the proper module
						nextLine = "app_controllers.controller(\"" + controllerName + "\"," + nextLine;
						
						writer.println(nextLine);

			    	} else {
			    		// The controller assignment line has strict requirements
			    		throw new Exception("Make sure controller function args and args array are in the same line as the directie controller attribute assignment.");
			    	}
				}


			}

			// Close files after work is done
			input.close();
			writer.close();

			return outputPath;

		} catch (Exception e) {
			// File Not Found Exception
	        return null;
	    }
	}

}