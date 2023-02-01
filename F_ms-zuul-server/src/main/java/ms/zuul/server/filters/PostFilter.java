package ms.zuul.server.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info("Entering post filter ...");
		Long startTime = (Long) request.getAttribute("startTime");
		Long finalTime = System.currentTimeMillis();
		Long timeElapsed = finalTime- startTime;
		log.info(String.format("Elapsed time in seconds: %s seconds.", timeElapsed.doubleValue()/1000.00));
		log.info(String.format("Elapsed time in milliseconds: %s milliseconds.", timeElapsed.doubleValue()));
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static Logger log = LoggerFactory.getLogger(PostFilter.class); 

}
