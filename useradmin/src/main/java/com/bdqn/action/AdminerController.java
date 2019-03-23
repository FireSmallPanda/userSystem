package com.bdqn.action;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class AdminerController {

	/**
	 * 打开adminer页面
	 * 
	 * @param pagePath
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("admin/{pagePath}.htm")
	public ModelAndView gotoFtlPage(@PathVariable String pagePath) throws Exception {
		// 载入时跳到登录页面(需要向页面取值)
		System.out.println("打开adminer页面！");
		ModelAndView mav = new ModelAndView("view/adminer/" + pagePath);
		return mav;
	}

	/**
	 * 管理员注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("adminerDo/logOff.htm")
	public ModelAndView doSearchUserByPage(HttpSession session) {
		ModelAndView mav = new ModelAndView("view/user/login");
		session.setAttribute("admininfo", null);
		session.setAttribute("adminHead", "");
		return mav;
	}

	/**
	 * 发送邮件
	 * 
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping("adminerDo/setWaitEmail.htm")
	@ResponseBody
	public void setWaitEmail(HttpSession session, String waitEmailFlag) {
		session.setAttribute("waitEmailFlag", waitEmailFlag);
	}

}
