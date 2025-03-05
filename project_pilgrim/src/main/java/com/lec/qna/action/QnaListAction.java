package com.lec.qna.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.common.PageInfo;
import com.lec.qna.service.QnaDetailService;
import com.lec.qna.service.QnaListService;
import com.lec.qna.vo.QnaFilesVO;
import com.lec.qna.vo.QnaVO;

public class QnaListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		// System.out.println("===> 게시글조회하기");
		// board/board_list.do?p=1&f=writer&q=홍길

		int page = 1;
		int limit = 10;

		int p = 1;
		String f = "";
		String q = "";

		if (req.getParameter("p") != null)
			p = Integer.parseInt(req.getParameter("p"));
		if (req.getParameter("f") != null)
			f = req.getParameter("f");
		else
			f = "title";
		if (req.getParameter("q") != null)
			q = req.getParameter("q");

		List<QnaVO> qnaList = new ArrayList<>();
		PageInfo pageInfo = new PageInfo();

		QnaListService qnaListService = QnaListService.getInstance();
		int listCount = qnaListService.getListCount(f, q);
		qnaList = qnaListService.getQnaList(p, limit, f, q);

		QnaDetailService qnaDetailService = QnaDetailService.getInstance();
		List<QnaFilesVO> fileList = new ArrayList<>();

		// ✅ 각 게시글(bno)에 해당하는 파일 리스트 가져오기
		for (QnaVO qna : qnaList) {
			List<QnaFilesVO> files = qnaDetailService.getFiles(qna.getBno());
			fileList.addAll(files); // 모든 파일을 리스트에 추가
		}

		// 페이징처리
		// 1. 총페이지수 = (총레코드수 / limit) + 1
		int totalPage = (int) ((double) listCount / limit + 0.95);

		// 2. 현재페이지(1~10, 11~20 ... 100~101)
		int startPage = (p - 1) / 10 * 10 + 1;

		// 3. 마지막페이지
		int endPage = startPage + 9;
		endPage = endPage > totalPage ? (totalPage > 0 ? totalPage : 1) : endPage;

		pageInfo.setListCount(listCount);
		pageInfo.setPage(p);
		pageInfo.setTotalPage(totalPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);

		req.setAttribute("qnaList", qnaList);
		req.setAttribute("pageInfo", pageInfo);
		req.setAttribute("fileList", fileList);

		ActionForward forward = new ActionForward();
		forward.setPath(String.format("/qna_list.jsp?p=%s&f=%s&q=%s", p, f, q));
		return forward;
	}
}
