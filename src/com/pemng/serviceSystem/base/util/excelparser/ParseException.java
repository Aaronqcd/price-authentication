package com.pemng.serviceSystem.base.util.excelparser;

import java.util.ArrayList;
import java.util.List;
   
public class ParseException extends Exception {

	private static final long serialVersionUID = 6526746026994126591L;
	
	private List errorList = new ArrayList();
	
    public ParseException(Throwable cause) {
        super(cause);
    }
    
    public ParseException(){}
    
    public void addParseError(ParseError err){
    	errorList.add(err);
    }
    
    public void addParseException(ParseException exception){
    	for (int i = 0; i < exception.getErrorList().size(); i++) {
    		errorList.add(exception.getErrorList().get(i));
		}
    }
    
    public List getErrorList(){
    	return errorList;
    }
}
   