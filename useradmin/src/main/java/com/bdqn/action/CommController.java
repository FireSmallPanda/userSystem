package com.bdqn.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommController {

	@RequestMapping("{pagePath}.htm")
	public ModelAndView gotoPage(@PathVariable String pagePath) throws Exception {
		ModelAndView mav = new ModelAndView(pagePath);
		System.out.println("进入项目");
		return mav;
	}

}
