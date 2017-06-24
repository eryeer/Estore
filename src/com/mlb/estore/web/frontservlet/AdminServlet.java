package com.mlb.estore.web.frontservlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mlb.estore.domain.Goods;
import com.mlb.estore.domain.User;
import com.mlb.estore.service.AdminService;
import com.mlb.estore.service.GoodsService;
import com.mlb.estore.utils.DownLoadUtils;
import com.mlb.estore.utils.FactoryUtils;
import com.mlb.estore.utils.UUIDUtils;
import com.mlb.estore.utils.UploadUtils;

public class AdminServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String addGoods(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 验证用户登录
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "/login.jsp";
		}

		boolean multipartContent = ServletFileUpload
				.isMultipartContent(request);
		if (!multipartContent) {
			response.setContentType("text/html");
			response.getWriter().print(
					"<script>alert('不能添加商品,请联系管理员');history.go(-1);</script>");
			return null;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		try {
			Goods goods = new Goods();
			goods.setId(UUIDUtils.getUUID());
			List<FileItem> list = sfu.parseRequest(request);
			for (FileItem fileItem : list) {
				if (!fileItem.isFormField()) {
					String fileName = fileItem.getName();
					if (fileName != null && fileName.length() != 0) {
						fileName = UploadUtils.generateRandomFileName(fileName);
						String app = this.getServletContext().getRealPath("");
						String dir = "/upload";
						String subDir = UploadUtils.generateRandomDir(fileName);
						String path = app + dir + subDir;
						File file = new File(path);
						if (!file.exists()) {
							file.mkdirs();
						}
						goods.setImgurl(dir + subDir + "/" + fileName);
						fileItem.write(new File(file, fileName));
						fileItem.delete();
					}
				} else {
					String property = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8");
					BeanUtils.setProperty(goods, property, value);
				}
			}
			AdminService adminService = FactoryUtils
					.getInstance(AdminService.class);
			adminService.addGoods(goods);
			return "/srgoodsServlet?method=goodsList";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("上传失败!");
		}
	}

	public void ranking(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GoodsService goodsService = FactoryUtils
				.getInstance(GoodsService.class);
		List<Goods> ranking = goodsService.getRanking();
		if (ranking != null && ranking.size() > 0) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("销量排行榜");
			int rowIndex = 0;
			HSSFRow headRow = sheet.createRow(rowIndex++);
			HSSFCell head1 = headRow.createCell(0);
			HSSFCell head2 = headRow.createCell(1);
			HSSFCell head3 = headRow.createCell(2);
			HSSFCell head4 = headRow.createCell(3);
			HSSFCell head5 = headRow.createCell(4);
			HSSFCell head6 = headRow.createCell(5);
			head1.setCellValue("name");
			head2.setCellValue("marketprice");
			head3.setCellValue("estoreprice");
			head4.setCellValue("num");
			head5.setCellValue("category");
			head6.setCellValue("salesNum");
			for (Goods g : ranking) {
				HSSFRow bodyRow = sheet.createRow(rowIndex++);
				HSSFCell cell1 = bodyRow.createCell(0);
				HSSFCell cell2 = bodyRow.createCell(1);
				HSSFCell cell3 = bodyRow.createCell(2);
				HSSFCell cell4 = bodyRow.createCell(3);
				HSSFCell cell5 = bodyRow.createCell(4);
				HSSFCell cell6 = bodyRow.createCell(5);
				cell1.setCellValue(g.getName());
				cell2.setCellValue(g.getMarketprice());
				cell3.setCellValue(g.getEstoreprice());
				cell4.setCellValue(g.getNum());
				cell5.setCellValue(g.getCategory());
				cell6.setCellValue(g.getSalesnum());
			}
			String fileName = "销量排行榜.xls";
			response.setHeader(
					"content-disposition",
					"attachment;filename="
							+ DownLoadUtils.getAttachmentFileName(fileName,
									request.getHeader("user-agent")));
			response.setHeader("content-type", this.getServletContext()
					.getMimeType(fileName));
			workbook.write(response.getOutputStream());
		} else {
			response.setContentType("text/html");
			response.getWriter().write("<script>alert('下载失败!')</script>");
		}
	}

	public String rankingOnline(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		GoodsService goodsService = FactoryUtils
				.getInstance(GoodsService.class);
		List<Goods> ranking = goodsService.getRanking();
		request.setAttribute("ranking", ranking);
		return "/ranking_online.jsp";
	}
}
