<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.net.URLEncoder, java.sql.*" %>
<%
    request.setCharacterEncoding("UTF-8");

    String certNum = request.getParameter("cert_num");  // 증명서 발급번호
    String certType = request.getParameter("type");    // 증명서 종류 (재직/퇴직/경력)
    String reason = request.getParameter("reason");    // 증명서 신청 사유

    // reason 값을 URL 인코딩하여 안전하게 전달
    String encodedReason = URLEncoder.encode(reason, "UTF-8");

    String targetPage = "";
    if ("재직".equals(certType)) {
        targetPage = "pdf_emp.jsp";
    } else if ("경력".equals(certType)) {
        targetPage = "pdf_career.jsp";
    } else if ("퇴직".equals(certType)) {
        targetPage = "pdf_retire.jsp";
    } else {
        out.println("<script>alert('잘못된 요청입니다.'); history.back();</script>");
        return;
    }

    // 선택한 JSP로 이동하면서 증명서 번호 및 신청 사유 전달
    response.sendRedirect(targetPage + "?cert_num=" + certNum + "&reason=" + encodedReason);
%>
