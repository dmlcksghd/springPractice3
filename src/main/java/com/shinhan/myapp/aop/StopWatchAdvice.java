package com.shinhan.myapp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Component
@Aspect //@PointCut + Advice
public class StopWatchAdvice   {
 
	//@Pointcut("execution(* select*(..))")
	@Pointcut("within(com.shinhan.myapp.controller.EmpController)")
	public void deptTimer() {}
    	
	@Around("deptTimer()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("******" + jp.getSignature().getName() );
		StopWatch watch = new StopWatch("시작");
		watch.start();
		System.out.println("---------------before--------------");
		Object obj = jp.proceed();
		System.out.println("---------------after--------------");
		System.out.println("*****" + jp.getSignature().getName() );
		watch.stop();
		System.out.println("걸린시간:" + watch.getTotalTimeMillis());
		System.out.println(watch.prettyPrint());
		return obj;
	}
	
	
	 
}