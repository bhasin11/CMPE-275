package edu.sjsu.cmpe275.aop.aspect;

import edu.sjsu.cmpe275.aop.Profile;
import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;
import org.springframework.core.annotation.Order;
import java.util.*;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class AuthorizationAspect{
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advises as needed.
     */
	private static Map<String, Set<String>> hashMap;
	
	public AuthorizationAspect(){
		this.hashMap = new HashMap();
	}
	
	@Around("execution(public * edu.sjsu.cmpe275.aop.ProfileService.readProfile(..))")
	public Profile readAuthorization(ProceedingJoinPoint list) throws Throwable{
		
		Object[] arguments = list.getArgs();
		String self = arguments[0].toString(), requestedProfile = arguments[1].toString();

		if(self != requestedProfile && 
				(!hashMap.containsKey(self) || !hashMap.get(self).contains(requestedProfile))){
			throw new AccessDeniedExeption("Access Denied");
		}
		return (Profile) list.proceed();
	}
	
	@Around("execution(public void edu.sjsu.cmpe275.aop.ProfileService.shareProfile(..))")
	public void shareAuthorization(ProceedingJoinPoint list) throws Throwable{
		
		Object[] arguments = list.getArgs();
		String self = arguments[0].toString(), 
				sharedProfile = arguments[1].toString(), sharingWith = arguments[2].toString();
		
		if(self != sharedProfile &&
				(!hashMap.containsKey(self) || !hashMap.get(self).contains(sharedProfile))){
			throw new AccessDeniedExeption("Access Denied");
		}
		list.proceed();
		if(self.equals(sharedProfile)){ 
			if(!hashMap.containsKey(sharingWith))	hashMap.put(sharingWith, new HashSet<String>());
		}		
		else{
			if(!(hashMap.containsKey(sharingWith) && !hashMap.get(sharingWith).contains(sharingWith)))
				hashMap.put(sharingWith, new HashSet<String>());
		}	
		hashMap.get(sharingWith).add(sharedProfile);			
	}
	
	@Around("execution(public void edu.sjsu.cmpe275.aop.ProfileService.unshareProfile(..))")
	public void unshareAuthorization(ProceedingJoinPoint list) throws Throwable{

		Object[] args = list.getArgs();
		String self = args[0].toString(), deniedUser = args[1].toString();
		
		if(!self.equals(deniedUser) &&
				(!hashMap.containsKey(deniedUser) || !hashMap.get(deniedUser).contains(self))){
			throw new AccessDeniedExeption("Access Denied");
		}
		list.proceed();
		hashMap.get(deniedUser).remove(self);
	}
}