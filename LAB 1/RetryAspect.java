package edu.sjsu.cmpe275.aop.aspect;

import edu.sjsu.cmpe275.aop.Profile;
import edu.sjsu.cmpe275.aop.exceptions.*;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;

@Aspect
@Order(0)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */

	@Around("execution(public * edu.sjsu.cmpe275.aop.ProfileService.*shareProfile(..))")
	public void shareProfileHandler(ProceedingJoinPoint list) throws NetworkException, AccessDeniedExeption{
		int attempts = 3;
		while(attempts > 0){
			try{
				list.proceed();
				break;
			}
			catch(AccessDeniedExeption e){
				System.out.println("Exception: Access Denied");
				throw new AccessDeniedExeption(e.getMessage());
			}
			catch(NetworkException e){
				System.out.println("Exception: Network");
				if(attempts == 1) throw new NetworkException(e.getMessage());
			}
			catch(Throwable e){
				System.out.println("Exception: "+e.getMessage());
				return;
			}
			attempts--;
		}
	}
	
	@Around("execution(public * edu.sjsu.cmpe275.aop.ProfileService.readProfile(..))")
	public Profile readProfileHandler(ProceedingJoinPoint list) throws NetworkException, AccessDeniedExeption{
		int attempts = 3;
		while(attempts > 0){
			try{
				return (Profile) list.proceed();
			}
			catch(AccessDeniedExeption e){
				System.out.println("Exception: Access Denied");
				throw new AccessDeniedExeption(e.getMessage());
			}
			catch(NetworkException e){
				System.out.println("Exception: Network");
				if(attempts == 1) throw new NetworkException(e.getMessage());
			}
			catch(Throwable e){
				System.out.println("Exception: "+e.getMessage());
				return null;
			}
			attempts--;
		}
		return null;
	}
}