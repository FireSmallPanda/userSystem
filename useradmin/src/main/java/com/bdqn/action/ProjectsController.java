package com.bdqn.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.bdqn.entity.Projects;
import com.bdqn.entity.User;
import com.bdqn.service.IProjectService;
import com.bdqn.util.Page;
import com.bdqn.util.ProjectsArexcelUtil;

@Controller
public class ProjectsController {

	@Value("#{configProperties['pageMax']}")
	private int pageSize;

	/**
	 * 打开project页面
	 * 
	 * @param pagePath
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("projects/{pagePath}.htm")
	public ModelAndView gotoFtlPage(@PathVariable String pagePath) throws Exception {
		// 载入时跳到登录页面(需要向页面取值)
		System.out.println("打开project页面！");
		ModelAndView mav = new ModelAndView("view/projects/" + pagePath);
		return mav;
	}

	/**
	 * 显示所有项目
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("adminerDo/doShowAllProject.htm")
	public ModelAndView doShowAllProject(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView("view/adminer/showProjectListPage");// 成功页面
		ModelAndView errMav = new ModelAndView("view/user/loginError");// 失败页面
		Page page = new Page();
		page.setPageSize(pageSize);
		page.setReccordCount(projectServiceImpl.searchProjectCount(null));
		int totalPageCount = page.getTotalPageCount();
		if (this.comfirmLogin(session)) {
			List<Projects> projectList = projectServiceImpl.serachProject(null, 1, pageSize);
			mav.addObject("projectList", projectList);
			mav.addObject("totalPageCount", totalPageCount);
			mav.addObject("pageNo", 1);
			session.setAttribute("searchProject", new Projects());
			session.setAttribute("projectsTimeSearch", "0");
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

	@RequestMapping("adminerDo/doAddProject.htm")
	@ResponseBody
	public String doAddProject(HttpSession session, Projects projects, String start, String end) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		projects.setProjectsStart(sdf.parse(start));
		projects.setProjectsEnd(sdf.parse(end));
		if (projects != null && projects.getProjectsEnd().before(projects.getProjectsStart())) {
			return "04"; // 结束时间大于开始时间
		}
		// 判断是否为管理员
		if (!this.comfirmLogin(session)) {
			return "01";// 未登录
		}
		boolean addFlag = projectServiceImpl.addProject(projects);
		if (addFlag) {
			return "02"; // 成功
		} else {
			return "03"; // 失败
		}

	}

	@RequestMapping("adminerDo/doDelProject.htm")
	@ResponseBody
	public String doDelProject(HttpSession session, int projectsId) throws Exception {

		// 判断是否为管理员
		if (!this.comfirmLogin(session)) {
			return "01";// 未登录
		}
		boolean delFlag = projectServiceImpl.deleteProject(projectsId);
		if (delFlag) {
			return "02"; // 成功
		} else {
			return "03"; // 失败
		}

	}

	@RequestMapping("adminerDo/doUpdateProject.htm")
	@ResponseBody
	public String doUpdateProject(HttpSession session, Projects projects, String start, String end) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		projects.setProjectsStart(sdf.parse(start));
		projects.setProjectsEnd(sdf.parse(end));
		if (projects != null && projects.getProjectsEnd().before(projects.getProjectsStart())) {
			return "04"; // 结束时间大于开始时间
		}
		// 判断是否为管理员
		if (!this.comfirmLogin(session)) {
			return "01";// 未登录
		}
		boolean updateFlag = projectServiceImpl.updateProject(projects);
		if (updateFlag) {
			return "02"; // 成功
		} else {
			return "03"; // 失败
		}
	}

	/**
	 * 分页查询项目
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("adminerDo/doSearchProjectByPage.htm")
	public ModelAndView doSearchProjectByPage(HttpSession session, int pageNo) throws Exception {
		ModelAndView mav = new ModelAndView("view/adminer/showProjectListPage");// 成功页面
		ModelAndView errMav = new ModelAndView("view/user/loginError");// 失败页面
		Projects searchProject = (Projects) session.getAttribute("searchProject");
		String projectsTimeSearch = (String) session.getAttribute("projectsTimeSearch");
		// 根据选择赋值
		if (projectsTimeSearch == null || "".equals(projectsTimeSearch) || "0".equals(projectsTimeSearch)) {

		} else if ("1".equals(projectsTimeSearch)) {
			searchProject.setProjectsEnd(new Date());
		} else if ("2".equals(projectsTimeSearch)) {
			searchProject.setProjectsStart(new Date());
			searchProject.setProjectsEnd(new Date());
		} else if ("3".equals(projectsTimeSearch)) {
			searchProject.setProjectsStart(new Date());
		}

		Page page = new Page();
		page.setPageSize(pageSize);
		page.setCurrPageNo(pageNo);
		page.setReccordCount(projectServiceImpl.searchProjectCount(searchProject));
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int totalPageCount = page.getTotalPageCount();
		if (this.comfirmLogin(session)) {
			List<Projects> projectList = projectServiceImpl.serachProject(searchProject,startRow, endRow);
			mav.addObject("projectList", projectList);
			mav.addObject("totalPageCount", totalPageCount);
			mav.addObject("pageNo", pageNo);
			session.setAttribute("searchProject", searchProject);
			session.setAttribute("projectsTimeSearch", projectsTimeSearch);
			return mav;
		} else {
			return errMav;
		}

	}

	/**
	 * 查询项目
	 * 
	 * @param session
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("adminerDo/doSearchProject.htm")
	public ModelAndView doSearchProject(HttpSession session, Integer projectsIdSearch, String projectsNameSearch,
			Integer projectsStatusSearch, String projectsTimeSearch) throws Exception {
		ModelAndView mav = new ModelAndView("view/adminer/showProjectListPage");// 成功页面
		ModelAndView errMav = new ModelAndView("view/user/loginError");// 失败页面
		Projects searchProjects = new Projects();
		searchProjects.setProjectsId(projectsIdSearch);
		searchProjects.setProjectsName(projectsNameSearch);
		searchProjects.setProjectsStatus(projectsStatusSearch);
		// 根据选择赋值
		if (projectsTimeSearch == null || "".equals(projectsTimeSearch) || "0".equals(projectsTimeSearch)) {

		} else if ("1".equals(projectsTimeSearch)) {
			searchProjects.setProjectsEnd(new Date());
		} else if ("2".equals(projectsTimeSearch)) {
			searchProjects.setProjectsStart(new Date());
			searchProjects.setProjectsEnd(new Date());
		} else if ("3".equals(projectsTimeSearch)) {
			searchProjects.setProjectsStart(new Date());
		}

		Page page = new Page();
		page.setPageSize(pageSize);
		page.setReccordCount(projectServiceImpl.searchProjectCount(searchProjects));
		int totalPageCount = page.getTotalPageCount();
		if (this.comfirmLogin(session)) {
			List<Projects> projectList = projectServiceImpl.serachProject(searchProjects, 1, pageSize);
			mav.addObject("projectList", projectList);
			mav.addObject("totalPageCount", totalPageCount);
			mav.addObject("pageNo", 1);
			session.setAttribute("searchProject", searchProjects);
			session.setAttribute("projectsTimeSearch", projectsTimeSearch);
			return mav;
		} else {
			return errMav;
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
	public String createProjectsList(HttpSession session, Integer pageNo, HttpServletRequest request) {
		if (!this.comfirmLogin(session)) {// 管理员是否登录
			return null;
		}
		Projects searchProject = (Projects) session.getAttribute("searchProject");
		String projectsTimeSearch = (String) session.getAttribute("projectsTimeSearch");
		// 根据选择赋值
		if (projectsTimeSearch == null || "".equals(projectsTimeSearch) || "0".equals(projectsTimeSearch)) {

		} else if ("1".equals(projectsTimeSearch)) {
			searchProject.setProjectsEnd(new Date());
		} else if ("2".equals(projectsTimeSearch)) {
			searchProject.setProjectsStart(new Date());
			searchProject.setProjectsEnd(new Date());
		} else if ("3".equals(projectsTimeSearch)) {
			searchProject.setProjectsStart(new Date());
		}
		
		if (searchProject != null && pageNo != null) {
			Page page = new Page();
			page.setPageSize(pageSize);
			page.setCurrPageNo(pageNo);
			page.setReccordCount(projectServiceImpl.searchProjectCount(searchProject));
			int startRow = page.getStartRow();
			int endRow = page.getEndRow();
			List<Projects> projectList = projectServiceImpl.serachProject(searchProject,startRow, endRow);
			if (projectList != null) {
				@SuppressWarnings("deprecation")
				String realPath = request.getRealPath("/");
				String downFileDirPath = realPath + "downloadFile";// 下载的路径
				ProjectsArexcelUtil arexcelUtil = new ProjectsArexcelUtil(downFileDirPath);// 创建工具类传入下载路径
				String savePath = "";
				try {
					savePath = arexcelUtil.genarateExcel(projectList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return savePath;
			}
		}

		return null;
	}

	@RequestMapping("adminerDo/doDownloadProjectsList.htm")
	public void doDownloadProjectsList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
			Integer pageNo) throws Exception {
		String path = this.createProjectsList(session, pageNo, request);

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
	
	@Resource
	private IProjectService projectServiceImpl;
}
