package com.bdqn.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bdqn.entity.User;
import com.bdqn.entity.UsersImage;
import com.bdqn.service.IUserService;
import com.bdqn.service.IUsersImageService;
import com.bdqn.util.EmailSendUtil;
import com.bdqn.util.Page;
import com.bdqn.util.UserArexcelUtil;

@Controller
public class UserController {

	@Value("#{configProperties['pageMax']}")
	private int pageSize;
	@Value("#{configProperties['validateCode']}")
	private String validateCode;
	@Value("#{configProperties['codeNum']}")
	private int codeNum;
	@Value("#{configProperties['serverHost']}")
	private String serverHost;
	@Value("#{configProperties['email']}")
	private String email;
	@Value("#{configProperties['password']}")
	private String password;
	@Value("#{configProperties['emailTitle']}")
	private String emailTitle;
	@Value("#{configProperties['emailType']}")
	private String emailType;
	@Value("#{configProperties['defalutImage']}")
	private String defalutImage;

	/**
	 * user页面
	 * 
	 * @param pagePath
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("user/{pagePath}.htm")
	public ModelAndView gotoFtlPage(@PathVariable String pagePath) throws Exception {
		// 载入时跳到登录页面(需要向页面取值)
		System.out.println("打开user页面！");
		ModelAndView mav = new ModelAndView("view/user/" + pagePath);
		return mav;
	}

	/**
	 * 登录方法
	 * 
	 * @param session
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userDo/doLogin.htm")
	@ResponseBody
	public String doLogin(HttpSession session, User user, String verificationCode) throws Exception {

		String sessionCode = ((String) session.getAttribute("code")).toLowerCase();
		String myCode = verificationCode.toLowerCase();
		if (!myCode.equals(sessionCode)) {
			return "01"; // 验证码错误
		}

		User retn = userServiceImpl.getUserInfoByPassword(user);
		if (retn == null) {
			return "02"; // 帐户密码错误
		}
		UsersImage inImage = new UsersImage();
		inImage.setUserId(retn.getUserId());
		inImage.setUserImageType("1");
		UsersImage image = usersImageService.getUsersImage(inImage);
		// 判断是否为管理员
		if (retn.getUserId() == 1) {
			session.setAttribute("admininfo", retn);
			if (image != null) {
				session.setAttribute("adminHead", image.getUserImagePath());
			} else {
				session.setAttribute("adminHead", "..\\resources\\userImage\\" + defalutImage); // 默认图片路径
			}
			return "admin"; // 为管理员
		} else {
			session.setAttribute("userinfo", retn);
			if (image != null) {
				session.setAttribute("userHead", image.getUserImagePath());
			} else {
				session.setAttribute("userHead", "..\\resources\\userImage\\" + defalutImage); // 默认图片路径
			}
			return "user"; // 为用户
		}

	}

	/**
	 * 用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userDo/doRegister.htm")
	@ResponseBody
	public String doRegister(User user) throws Exception {
		// 查看用户名是否已经存在
		User testUser = new User();
		testUser.setUserAccount(user.getUserAccount());
		Integer haveFlag = userServiceImpl.searchUserCount(testUser);
		if (haveFlag != null && haveFlag > 0) {
			return "02"; // 该用户已经存在
		}
		// 进行添加用户
		boolean userRegisterFlag = userServiceImpl.addUser(user);
		if (!userRegisterFlag) {
			return "01"; // 注册失败
		}
		return "";
	}

	/**
	 * 显示所有用户操作
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("adminerDo/doShowAllUser.htm")
	public ModelAndView doShowAllUser(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView("view/adminer/showUserListPage");// 成功页面
		ModelAndView errMav = new ModelAndView("view/user/loginError");// 失败页面
		Page page = new Page();
		page.setPageSize(pageSize);
		page.setReccordCount(userServiceImpl.searchUserCount(null));// 总记录数
		int totalPageCount = page.getTotalPageCount();
		if (this.comfirmLogin(session)) {// 验证是否为管理员
			List<User> userList = userServiceImpl.getAllUser();
			mav.addObject("userList", userList);
			mav.addObject("totalPageCount", totalPageCount);
			mav.addObject("pageNo", 1);
			session.setAttribute("searchUser", new User());
			return mav;
		} else {
			return errMav;
		}
	}

	@RequestMapping("adminerDo/doAddUser.htm")
	@ResponseBody
	public String doAddUser(HttpSession session, User user) throws Exception {
		// 判断是否为管理员
		if (!this.comfirmLogin(session)) {
			return "nologin";
		}
		boolean addFlag = userServiceImpl.addUser(user);
		if (addFlag) {
			return "success";
		} else {
			return "error";
		}

	}

	@RequestMapping("adminerDo/doDelUser.htm")
	@ResponseBody
	public String doDelUser(HttpSession session, int userId) throws Exception {
		// 判断是否为管理员
		if (!this.comfirmLogin(session)) {
			return "nologin";
		}
		boolean delFlag = userServiceImpl.deleteUser(userId);
		if (delFlag) {
			return "success";
		} else {
			return "error";

		}

	}

	/**
	 * 修改用户信息
	 * 
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping("adminerDo/doUpdateUser.htm")
	@ResponseBody
	public String doUpdateUser(HttpSession session, User user, String oldPassword) {

		// 判断是否为管理员
		if (!this.comfirmLogin(session)) {
			return "nologin";
		}
		// 若修改密码则判断原始密码是否正确
		if (oldPassword != null && !"".equals(oldPassword)) {
			User validateUser = new User();
			validateUser.setUserStatus(user.getUserStatus());
			validateUser.setUserAccount(user.getUserAccount());
			validateUser.setUserPassword(oldPassword);
			User retn = userServiceImpl.getUserInfoByPassword(validateUser);
			if (retn == null) {
				return "passwordError";
			}
		}
		// 判断是否更改成功
		boolean updateFlag = userServiceImpl.updateUser(user);
		if (updateFlag) {
			return "success";
		} else {
			return "error";

		}
	}

	/**
	 * 搜索员工信息
	 * 
	 * @param session
	 * @param user
	 * @param flag
	 * @return
	 */
	@RequestMapping("adminerDo/doSearchUser.htm")
	public ModelAndView doSearchUser(HttpSession session, Integer userIdSearch, String userNameSearch,
			String userEmailSearch, Integer userStatusSearch) {
		User user = new User();
		user.setUserEmail(userEmailSearch);
		user.setUserId(userIdSearch);
		user.setUserName(userNameSearch);
		user.setUserStatus(userStatusSearch);
		ModelAndView mav = new ModelAndView("view/adminer/showUserListPage");// 成功页面
		ModelAndView errMav = new ModelAndView("view/user/loginError");// 失败页面
		// 得到开始记录和结束记录
		Page page = new Page();
		page.setPageSize(pageSize);
		page.setCurrPageNo(1);
		page.setReccordCount(userServiceImpl.searchUserCount(user));// 总记录数
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int totalPageCount = page.getTotalPageCount();
		if (this.comfirmLogin(session)) {// 验证是否为管理员
			List<User> userList = userServiceImpl.serachUser(user, startRow, endRow);
			mav.addObject("userList", userList);
			mav.addObject("pageNo", 1);
			mav.addObject("totalPageCount", totalPageCount);
			session.setAttribute("searchUser", user);
			return mav;
		} else {
			return errMav;
		}
	}

	/**
	 * 根据页面搜索记录
	 * 
	 * @param session
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("adminerDo/doSearchUserByPage.htm")
	public ModelAndView doSearchUserByPage(HttpSession session, int pageNo) {
		ModelAndView mav = new ModelAndView("view/adminer/showUserListPage");// 成功页面
		ModelAndView errMav = new ModelAndView("view/user/loginError");// 失败页面
		User user = (User) session.getAttribute("searchUser");
		// 得到开始记录和结束记录
		Page page = new Page();
		page.setPageSize(pageSize);
		page.setCurrPageNo(pageNo);
		page.setReccordCount(userServiceImpl.searchUserCount(user));// 总记录数
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int totalPageCount = page.getTotalPageCount();
		if (this.comfirmLogin(session)) {// 验证是否为管理员
			List<User> userList = userServiceImpl.serachUser(user, startRow, endRow);
			mav.addObject("userList", userList);
			mav.addObject("pageNo", pageNo);
			mav.addObject("totalPageCount", totalPageCount);
			return mav;
		} else {

			return errMav;
		}
	}

	/**
	 * 检查是否登录
	 * 
	 * @param session
	 * @return
	 */
	private boolean comfirmLogin(HttpSession session) {
		User adminer = (User) session.getAttribute("admininfo");
		if (!(adminer == null || "".equals(adminer.getUserName()))) {// 验证是否为管理员
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建excel文件
	 * 
	 * @param session
	 * @param pageNo
	 * @param request
	 * @return
	 */
	public String createUserList(HttpSession session, Integer pageNo, HttpServletRequest request) {
		if (!this.comfirmLogin(session)) {// 管理员是否登录
			return null;
		}
		User user = (User) session.getAttribute("searchUser");
		if (user != null && pageNo != null) {
			// 得到开始记录和结束记录
			Page page = new Page();
			page.setPageSize(pageSize);
			page.setCurrPageNo(pageNo);
			page.setReccordCount(userServiceImpl.searchUserCount(user));// 总记录数
			int startRow = page.getStartRow();
			int endRow = page.getEndRow();
			List<User> userList = userServiceImpl.serachUser(user, startRow, endRow);
			if (userList != null) {
				@SuppressWarnings("deprecation")
				String realPath = request.getRealPath("/");
				String downFileDirPath = realPath + "downloadFile";// 下载的路径
				UserArexcelUtil arexcelUtil = new UserArexcelUtil(downFileDirPath);// 创建工具类传入下载路径
				String savePath = "";
				try {
					savePath = arexcelUtil.genarateExcel(userList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return savePath;
			}
		}

		return null;
	}

	@RequestMapping("adminerDo/doDownloadUserList.htm")
	public void doDownloadUserList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
			Integer pageNo) throws Exception {
		String path = this.createUserList(session, pageNo, request);

		File file = new File(path);
		String fileName = file.getName();
		if ((new File(path)).exists()) {
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setHeader("Content-type", "application/vnd.ms-excel");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			// 删除生成的文件
			delExcel(path);
		}

	}

	/**
	 * 文件夹及目录下文件删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public void delExcel(String filepath) throws IOException {
		File f = new File(filepath); // 输入要删除的文件位置
		if (f.exists()) {
			f.delete();
		}

	}

	/**
	 * 修改用户信息
	 * 
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping("adminerDo/checkUserEmail.htm")
	@ResponseBody
	public String checkUserEmail(User user) {
		String email = userServiceImpl.getUserEmail(user);
		if (email != null && !("".equals(email))) {
			return "01";// 邮件校验成功
		} else {
			return "02";// 帐户信息错误
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping("adminerDo/sentEmail.htm")
	@ResponseBody
	public String sentEmail(HttpSession session, User user) {
		EmailSendUtil emailSendUtil = new EmailSendUtil();
		String sentEmail = userServiceImpl.getUserEmail(user);
		// 随机数组
		String[] codeSequence = validateCode.split(",");
		if (sentEmail == null || "".equals(sentEmail)) {
			return "03";// 邮件不存在
		}
		String context = "";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		StringBuffer code = new StringBuffer();
		// 创建一个随机数生成器类
		Random random = new Random();
		for (int i = 0; i < codeNum; i++) {
			code.append(String.valueOf(codeSequence[random.nextInt(58)]));
		}
		// 隐藏帐号最后两位
		String account = user.getUserAccount();
		String hideAccount = account.substring(0, account.length() - 2) + "**";
		// 邮件参数
		Map<String, String> map = new HashMap<>();
		map.put("userAccount", hideAccount);
		map.put("code", code.toString());
		map.put("date", dateString);
		context = emailSendUtil.getEmailContent(map);
		session.setAttribute("emailCode", code.toString());
		session.setAttribute("changePasswordAccount", account);
		try {
			emailSendUtil.sendMail(serverHost, email, password, sentEmail, emailTitle, context, emailType);
		} catch (Exception e) {
			e.printStackTrace();
			return "01";// 发送失败
		}
		return "02"; // 发送成功

	}

	/**
	 * 修改用户信息
	 * 
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping("adminerDo/setNewPassword.htm")
	@ResponseBody
	public String setNewPassword(HttpSession session, User user, String emailCode) {
		String sessionEmailCode = (String) session.getAttribute("emailCode");
		String changePasswordAccount = (String) session.getAttribute("changePasswordAccount");
		
		
		if (sessionEmailCode != null && !emailCode.equals(sessionEmailCode)) {
			return "01";// 验证码错误
		} else if (sessionEmailCode == null) {
			return "04";// 您未发送验证码

		}
		if (changePasswordAccount != null) {
			if(!changePasswordAccount.equals(user.getUserAccount())){
				return "05";// 激活账号未存在
			}
			user.setUserAccount(changePasswordAccount);
			userServiceImpl.updateUser(user);
			// 清空验证码和账号
			session.setAttribute("emailCode", null);
			session.setAttribute("changePasswordAccount", null);
			return "02";// 修改密码成功
		} else {
			return "03"; // 激活账号未存在
		}
	}
	/**
	 * 用户注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("userDo/logOff.htm")
	public ModelAndView doSearchUserByPage(HttpSession session) {
		ModelAndView mav = new ModelAndView("view/user/login");
		session.setAttribute("userinfo", null);
		session.setAttribute("userHead", "");
		return mav;
	}
	@Resource
	private IUserService userServiceImpl;
	@Resource
	private IUsersImageService usersImageService;

}
