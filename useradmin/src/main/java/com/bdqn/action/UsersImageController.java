package com.bdqn.action;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bdqn.entity.User;
import com.bdqn.entity.UsersImage;
import com.bdqn.service.IUsersImageService;

@Controller
public class UsersImageController {

	/**
	 * 
	 * @param session
	 * @param file
	 * @param request
	 * @param uploadType
	 *            adminer "管理员上传" users "用户上传"
	 * @param imageType
	 * @return
	 */
	@RequestMapping("upload/usersImage.htm")
	public ModelAndView upload(HttpSession session,
			@RequestParam(value = "usersImageFile", required = false) MultipartFile file, HttpServletRequest request,
			String uploadType, String imageType) {
		ModelAndView successUser = new ModelAndView("view/user/loginSuccess");
		ModelAndView successAdmin = new ModelAndView("view/adminer/adminLoginSuccess");
		ModelAndView logginErrorPage = new ModelAndView("view/adminer/loginError");
		ModelAndView errorPage = new ModelAndView("view/user/error");
		Integer userId = null;
		// 判断是否登录
		if ("adminer".equals(uploadType)) {
			User user = (User) session.getAttribute("admininfo");
			if (user != null) {
				userId = user.getUserId();
			} else {
				return logginErrorPage;
			}

		} else {
			User user = (User) session.getAttribute("userinfo");
			if (user != null) {
				userId = user.getUserId();
			} else {
				return logginErrorPage;
			}

		}
		if (imageType == null || "".equals(imageType)) {

			errorPage.addObject("msg", "请设定图片类型");
			return errorPage;
		}

		// 判断是否上传文件
		if (file == null) {
			errorPage.addObject("msg", "请上传图片");
			return errorPage;

		}
		// 判断原有图片是否存在，若存在则删除
		UsersImage oldImageIn = new UsersImage();
		oldImageIn.setUserId(userId);
		oldImageIn.setUserImageType(imageType);
		UsersImage oldImageOut = usersImageService.getUsersImage(oldImageIn);

		if (oldImageOut != null) {// 删除原图片
			// 得到删除图片路径
			String delPath = request.getServletContext().getRealPath("/")
					+ oldImageOut.getUserImagePath().replace("..\\", "");
			File delFile = new File(delPath); // 输入要删除的文件位置
			if (delFile.exists()) {
				delFile.delete();
			}
			// 删除数据库中数据
			usersImageService.delUsersImage(oldImageOut);
		}
		// 保存路径
		String path = request.getServletContext().getRealPath("/") + "resources" + File.separator + "userImage"
				+ File.separator + userId;
		String fileName = new Date().getTime() + userId + file.getOriginalFilename();// 时间+用户编号+文件名
		System.out.println(path);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String oppositePath = ".." + File.separator + "resources" + File.separator + "userImage" + File.separator
				+ userId + File.separator + fileName;// 相对路径：时间+用户编号+文件名
		UsersImage newImagein = new UsersImage();
		newImagein.setUserId(userId);
		newImagein.setUserImageName("用户图片");
		newImagein.setUserImagePath(oppositePath);
		newImagein.setUserImageType(imageType);
		// 保存图片信息到数据库
		boolean saveFlag = usersImageService.addUsersImage(newImagein);
		if (!saveFlag) {
			errorPage.addObject("msg", "图片信息保存过程发生错误");
			return errorPage;
		}

		// 根据用户选择跳到相应页面
		if ("adminer".equals(uploadType)) {
			// 给予上传路径于session
			session.setAttribute("adminHead", oppositePath);
			return successAdmin;
		} else {
			// 给予上传路径于session
			session.setAttribute("userHead", oppositePath);
			return successUser;

		}
	}

	@Resource
	private IUsersImageService usersImageService;

}
