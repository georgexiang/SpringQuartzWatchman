package com.websystique.spring;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {
    
        public static  Set setAlertId=new HashSet(100);   
        
	public static void main(String args[]){
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("quartz-context.xml");
	}

}
