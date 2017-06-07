package com.jx372.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
		
	@Before( "execution(public ProductVo com.jx372.aoptest.ProductService.find(..))" )  //pointcut - 메소드 지정. 형식이 있다. 파라미터 생략은 ..으로 하며 오버로드가 되어있다면 정확히 파라미터를 적어 줘야 한다. 
	public void beforAdvice(){
		System.out.println( "beforeAdvice() called" );
	}
	
	@After( "execution(ProductVo com.jx372.aoptest.ProductService.find(..))" ) //public은 생략 가능. 접근지시자 생략가능, throw 생략가능
	public void afterAdvice(){
		System.out.println( "afterAdvice() called " );
	}
	
	@AfterReturning( "execution(* com.jx372.*.ProductService.find(..))" )  //와일드 카드도 사용 가능하다. 모든 리턴타입, 모든 패키지, 모든 메소드 다 가능하게 할 수 있다.
	public void afterReturningAdvice(){
		System.out.println( "afterReturning() called " );   //핵심이 실행되고 리턴 한 다음 실행된다.
	}
	
	@AfterThrowing(value = "execution(* *..*.ProductService.*(..))", throwing = "ex") // 와일드 카드를 이용해 프로젝트 내의 모든 패키지,ProductService클래스, 모든 메소드를 범위로 지정해 주었다. 리턴 타입 또한 마찬가지
	public void afterThrowingAdvice( Throwable ex){
		System.out.println( "afterThrowingAdvice("+ ex +") called ");   // exception을 모두 모을수 있다. 아니면 우리가 배운 글로벌exception을 사용해도 무방하다 내부적으로는 동일한 구조 일거 같다. 
	}
	
	@Around("execution(* *..*.ProductService.*(..))")	// 제일 많이 쓰는 방법이다.
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable{  //Around는  ProceedingJoinPoint라는 파라미터가 필요하다. 
		//before advice  해당 부분을 주석처리 하는 것만으로 before와 after를  구현가능하다. 둘의 기능을 유연하게 사용가능하다.
		System.out.println("[before] aroundAdvice() called ");
		
		//Object[] parameters = { "camera" };  // 대부분 잘 사용하지 않는 방법이다. 핵심 코드의 코드를 수정하는 건 드문일이기 때문이다. 
		Object result = pjp.proceed(); /* 핵심 코드  수행 */          // aroundAdvice는 return 값이 있어야 핵심 코드에 파라미터를 전달 해 줄 수 있다.   //  핵심코드에 파라미터를 지정해 주면 변경이 가능하다.
		
		//after advice
		System.out.println("[after] aroundAdvice() called ");		
		
		return result;
	}
}
