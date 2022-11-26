package kr.or.kosa.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebFilter(
		    description = "어노테이션 활용 필터 적용하기",
		    urlPatterns = {"/*"},
		    initParams = {@WebInitParam(name="encoding" , value="UTF-8")}
		  )
public class Encoding extends HttpFilter implements Filter {
       
	//member field 생성
	private String encoding;
	
    public Encoding() {
        super();
       
    }
    
    public void init(FilterConfig fConfig) throws ServletException {
		//최초 요청시 컴파일되고 한번만 실행
    	//web.xml 설정되어 있는 초기값을 read 해서 사용 FilterConfig 통해서
    	this.encoding = fConfig.getInitParameter("encoding"); //@WebInitParam 에서 read
    }

	public void destroy() {
		
	}

	// 함수를 통해서 요청 / 응답 필터링 제어
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		//HttpSession session = ((HttpServletRequest)request).getSession();
		//String userid = request.getParameter("userid");
		/* System.out.println("세션처리:" + userid); */

		
		//request  요청에 대한 필터 실행 코드 영역
		if(request.getCharacterEncoding() == null) {
			//한줄 코드 (공통관심 , 보조관심 AOP)
			request.setCharacterEncoding(this.encoding);
			response.setCharacterEncoding(this.encoding);
		}
		chain.doFilter(request, response);
		
		//response 응답에 대한  필터 실행 코드 영역
	}
}
