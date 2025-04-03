<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-promise/4.1.1/es6-promise.auto.js"></script>
<script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.min.js"></script>
<script type="text/javascript" src="./js/htp.js"></script>
</head>
<style type="text/css">
@page {
	size: A4 portrait;
	margin: 15mm;
}

/* 전체 래퍼 */
#divCustomWrapper {
	font-family: 'Malgun Gothic', 'Dotum', Arial, Tahoma;
	font-size: 10pt;
	width: 100%;
	max-width: 210mm;
	margin: 0 auto;
}

/* 테이블 스타일 */
#divCustomWrapper table {
	width: 100%;
	table-layout: fixed; /* 고정 레이아웃 */
	border-collapse: collapse; /* 테이블 내부 간격 제거 */
	border: 1px solid black;
	margin-bottom: 15px; /* 테이블 간 간격 */
}

/* 셀 스타일 */
#divCustomWrapper th, #divCustomWrapper td {
	border: 1px solid black;
	font-size: 10pt;
	padding: 10px;
	text-align: center;
	vertical-align: middle;
	word-break: keep-all; /* 단어 단위 줄바꿈 방지 */
}

/* 좌측 제목 컬럼 스타일 */
#divCustomWrapper td.subjectColumn {
	background: rgb(221, 221, 221);
	font-weight: bold;
	text-align: center;
	width: 25%;
	white-space: nowrap; /* 줄 바꿈 방지 */
}

/* 우측 상세 내용 스타일 */
#divCustomWrapper td.detailColumn {
	width: 75%;
	text-align: left;
}

/* 세로 정렬 방지 */
#divCustomWrapper td.verticalText {
	writing-mode: horizontal-tb !important; /* 가로 정렬 강제 */
	text-align: center;
}

/* 문서 제목 */
#divCustomWrapper #titleSection {
	text-align: center;
	font-size: 24px;
	font-weight: bold;
	margin: 10px 0 20px;
}

/* 프린트 스타일 */
@media print {
	.viewModeHiddenPart {
		display: none;
	}
	h1, h2, h3, h4, h5, p, table, tr, td, div, body {
		-webkit-print-color-adjust: exact;
	}
}

/* 테이블 간 여백 조정 */
#topTable {
	margin-bottom: 20px !important;
}

#bottomTable {
	margin-top: 20px !important;
}

:root {
	--bs-primary: #1b84ff;
	--bs-primary-light: #e9f3ff;
	--bs-primary-white: #fff;
}

.btn.btn-light-primary {
	color: var(--bs-primary);
	border-color: var(--bs-primary-light);
	background-color: var(--bs-primary-light);
}

.btn.btn-light-primary:hover {
	color: var(--bs-primary-white);
	border-color: var(--bs-primary);
	background-color: var(--bs-primary);
}

.btn.btn-light-primary:focus {
	outline: none;
	box-shadow: 0 0 0 0.25rem rgba(var(--bs-primary), 0.5);
}
</style>
<body>
	<div id="saveZone">
		<div id="divCustomWrapper"
			style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
			<div id="titleSection"
				style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 19pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">경력증명서</div>
			<div class="partition" id="draftSection"
				style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
				<div class="left"
					style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
					<table style="width: 250px; height: 120px;">
						<colgroup>
							<col width="80">
							<col width="170">
						</colgroup>
						<tbody>
							<tr>
								<td
									class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">

									신 청 자</td>
								<td
									class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
									<span class="comp_wrap" contenteditable="false" data-value=""
									unselectable="on" data-wrapper="" data-autotype=""
									data-dsl="{{label:draftUser}}" data-cid="0"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"><span
										class="comp_item"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
										${loginVo.name}
									</span>
									<span class="comp_hover" contenteditable="false"
										data-content-protect-cover="true" data-origin="0"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
											<a class="ic_prototype ic_prototype_trash"
											contenteditable="false" data-content-protect-cover="true"
											data-component-delete-button="true"></a>
									</span></span>
								</td>
							</tr>
							<tr>
								<td
									class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">

									소 속</td>
								<td
									class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
									<span class="comp_wrap" contenteditable="false" data-value=""
									unselectable="on" data-wrapper="" data-autotype=""
									data-dsl="{{label:draftDept}}" data-cid="1"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"><span
										class="comp_item"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
										${loginVo.dept_name}
									</span><span
										class="comp_hover" contenteditable="false"
										data-content-protect-cover="true" data-origin="1"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
											<a class="ic_prototype ic_prototype_trash"
											contenteditable="false" data-content-protect-cover="true"
											data-component-delete-button="true"></a>
									</span></span>
								</td>
							</tr>
							<tr>
								<td
									class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">

									신 청 일</td>
								<td
									class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
									<span class="comp_wrap" contenteditable="false" data-value=""
									unselectable="on" data-wrapper="" data-autotype=""
									data-dsl="{{label:draftDate}}" data-cid="2"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"><span
										class="comp_item"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
										<span id="requestDate"></span>
									</span><span
										class="comp_hover" contenteditable="false"
										data-content-protect-cover="true" data-origin="2"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
											<a class="ic_prototype ic_prototype_trash"
											contenteditable="false" data-content-protect-cover="true"
											data-component-delete-button="true"></a>
									</span></span>
								</td>
							</tr>
							<tr>
								<td
									class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">

									문서번호</td>
								<td
									class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
									<span class="comp_wrap" contenteditable="false" data-value=""
									unselectable="on" data-wrapper="" data-autotype=""
									data-dsl="{{label:docNo}}" data-cid="3"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"><span
										class="comp_item"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
										${param.cert_num}
									</span><span
										class="comp_hover" contenteditable="false"
										data-content-protect-cover="true" data-origin="3"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"><a
											class="ic_prototype ic_prototype_trash"
											contenteditable="false" data-content-protect-cover="true"
											data-component-delete-button="true"></a></span></span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<script>
			    document.addEventListener("DOMContentLoaded", function () {
			        let today = new Date();
			        let formattedDate = today.getFullYear() + '-' +
			                            ('0' + (today.getMonth() + 1)).slice(-2) + '-' +
			                            ('0' + today.getDate()).slice(-2);
			
			        document.getElementById("requestDate").innerText = formattedDate;
			    });
			</script>
			<table class="detailSection">
				<colgroup>
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="100">
				</colgroup>

				<tbody>
					<tr>
						<td rowspan="2"
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">

							인적사항</td>
						<td
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">

							성 명</td>
						<td colspan="2"
							class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							<span unselectable="on" contenteditable="false" class="comp_wrap"
							data-cid="4" data-dsl="{{text}}" data-wrapper=""
							style="width: 100%; font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"
							data-value="" data-autotype="">
							${loginVo.name}
							<span contenteditable="false"
								class="comp_active"
								style="display: none; font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
									<span class="Active_dot1"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"></span><span
									class="Active_dot2"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"></span>
									<span class="Active_dot3"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"></span><span
									class="Active_dot4"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;"></span>
							</span> <span contenteditable="false" class="comp_hover"
								data-content-protect-cover="true" data-origin="4"
								style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: normal; margin-top: 0px; margin-bottom: 0px;">
									<a contenteditable="false"
									class="ic_prototype ic_prototype_trash"
									data-content-protect-cover="true"
									data-component-delete-button="true"></a>
							</span> </span>
						</td>
						<td
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">

							주민등록번호</td>
						<td id="residentNumber" colspan="3"
							class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							<p
								style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;">
								<span unselectable="on" contenteditable="false"
									class="comp_wrap" data-cid="6" data-dsl="{{text}}"
									data-wrapper=""
									style="width: 100%; font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"
									data-value="" data-autotype="">
									${loginVo.birth}
								<span contenteditable="false"
									class="comp_active"
									style="display: none; font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;">
										<span class="Active_dot1"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"></span><span
										class="Active_dot2"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"></span>
										<span class="Active_dot3"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"></span><span
										class="Active_dot4"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"></span>
								</span> <span contenteditable="false" class="comp_hover"
									data-content-protect-cover="true" data-origin="6"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;">
										<a contenteditable="false"
										class="ic_prototype ic_prototype_trash"
										data-content-protect-cover="true"
										data-component-delete-button="true"></a>
								</span> </span><br>
							</p>
						</td>
					</tr>
					<tr>
						<td
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">

							이 메 일</td>
						<td colspan="6"
							class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							<p
								style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;">
								<span unselectable="on" contenteditable="false"
									class="comp_wrap" data-cid="5" data-dsl="{{text}}"
									data-wrapper=""
									style="width: 100%; font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"
									data-value="" data-autotype="">
									${loginVo.email}
								<span contenteditable="false"
									class="comp_active"
									style="display: none; font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;">
										<span class="Active_dot1"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"></span><span
										class="Active_dot2"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"></span>
										<span class="Active_dot3"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"></span><span
										class="Active_dot4"
										style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;"></span>
								</span> <span contenteditable="false" class="comp_hover"
									data-content-protect-cover="true" data-origin="5"
									style="font-family: &amp; quot; malgun gothic&amp;quot; , dotum , arial, tahoma; font-size: 9pt; line-height: 18px; margin-top: 0px; margin-bottom: 0px;">
										<a contenteditable="false"
										class="ic_prototype ic_prototype_trash"
										data-content-protect-cover="true"
										data-component-delete-button="true"></a>
								</span> </span><br>
							</p>
						</td>
					</tr>
					<tr>
						<td rowspan="5"
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							증명사항</td>
						<td colspan="3"
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							재 직 기 간</td>
						<td
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							소 속</td>
						<td
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							직 위</td>
						<td colspan="3"
							class="subjectColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							담당업무내용</td>
					</tr>
					<tr>
						<td colspan="3"
							class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l"
							style="">
							<p style="text-align: center;">
								<span unselectable="on" contenteditable="false"
									class="comp_wrap" data-cid="7" data-dsl="{{period}}"
									data-wrapper="" style="" data-value="" data-autotype="">
									${loginVo.hire_date}
									 ~ 
									 <span id="requestDate2"></span>
									<span contenteditable="false" class="comp_active" style="display: none;"> 
										<span class="Active_dot1"></span>
										<span class="Active_dot2"></span> 
										<span class="Active_dot3"></span>
										<span class="Active_dot4"></span>
									</span> 
									<span contenteditable="false" class="comp_hover"
											data-content-protect-cover="true" data-origin="7"> 
										<a contenteditable="false"
											class="ic_prototype ic_prototype_trash"
											data-content-protect-cover="true"
											data-component-delete-button="true"></a>
									</span> 
								</span>
							</p>
						</td>
						<td
							class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l"
							style="">
							<p style="text-align: center;">
								<span unselectable="on" contenteditable="false"
									class="comp_wrap" data-cid="19" data-dsl="{{textarea}}"
									data-wrapper="" style="width: 100%;" data-value=""
									data-autotype="">
									${loginVo.dept_name}
								<span
									contenteditable="false" class="comp_active"
									style="display: none;"> <span class="Active_dot1"></span><span
										class="Active_dot2"></span> <span class="Active_dot3"></span><span
										class="Active_dot4"></span>
								</span> <span contenteditable="false" class="comp_hover"
									data-content-protect-cover="true" data-origin="19"> <a
										contenteditable="false"
										class="ic_prototype ic_prototype_trash"
										data-content-protect-cover="true"
										data-component-delete-button="true"></a>
								</span> </span><br>
							</p>
						</td>
						<td
							class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l"
							style="">
							<p style="text-align: center;">
								<span unselectable="on" contenteditable="false"
									class="comp_wrap" data-cid="19" data-dsl="{{textarea}}"
									data-wrapper="" style="width: 100%;" data-value=""
									data-autotype="">
									${loginVo.position}
								<span
									contenteditable="false" class="comp_active"
									style="display: none;"> <span class="Active_dot1"></span><span
										class="Active_dot2"></span> <span class="Active_dot3"></span><span
										class="Active_dot4"></span>
								</span> <span contenteditable="false" class="comp_hover"
									data-content-protect-cover="true" data-origin="19"> <a
										contenteditable="false"
										class="ic_prototype ic_prototype_trash"
										data-content-protect-cover="true"
										data-component-delete-button="true"></a>
								</span> </span><br>
							</p>
						</td>
						<td colspan="3"
							class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							<p>
								<span unselectable="on" contenteditable="false"
									class="comp_wrap" data-cid="15" data-dsl="{{textarea}}"
									data-wrapper="" style="width: 100%;" data-value=""
									data-autotype="">
									<textarea class="txta_editor"></textarea>
									<span
									contenteditable="false" class="comp_active"
									style="display: none;"> <span class="Active_dot1"></span><span
										class="Active_dot2"></span> <span class="Active_dot3"></span><span
										class="Active_dot4"></span>
								</span> <span contenteditable="false" class="comp_hover"
									data-content-protect-cover="true" data-origin="15"> <a
										contenteditable="false"
										class="ic_prototype ic_prototype_trash"
										data-content-protect-cover="true"
										data-component-delete-button="true"></a>
								</span> </span><br>
							</p>
						</td>
					</tr>
					<tr>
						<td colspan="8"
							class="detailColumn dext_table_border_t dext_table_border_r dext_table_border_b dext_table_border_l">
							위와 같이 재직하고 있음을 증명합니다.<br>
						<br>
						<br> <span id="todayDate"
							style="text-align: center; display: block;"></span> <br>
						<br>
						<br>
						</td>
					</tr>
				</tbody>
			</table>
			<script>
			    window.onload = function() {
			        let birth = "${loginVo.birth}".trim(); // 혹시 모를 공백 제거
			        console.log("Birth value:", birth); // 콘솔에서 확인

			        // 생년월일이 8자리(YYYYMMDD)인 경우 앞 6자리 + '-●●●●●●●' 적용
			        let maskedBirth = maskResidentNumber(birth);
			        document.getElementById("residentNumber").textContent = maskedBirth; // 변경된 값 적용

			        function maskResidentNumber(birth) {
			            if (!birth || birth.length !== 8) return birth; // 8자리(YYYYMMDD)인지 확인
			            return birth.substring(2, 8) + "-●●●●●●●"; // 앞 6자리 + '-●●●●●●●' 표시
			        }
			    };
			</script>
			<script>
			    document.addEventListener("DOMContentLoaded", function () {
			        let today = new Date();
			        let formattedDate = today.getFullYear() + '-' +
			                            ('0' + (today.getMonth() + 1)).slice(-2) + '-' +
			                            ('0' + today.getDate()).slice(-2);
			
			        document.getElementById("requestDate2").innerText = formattedDate;
			    });
			</script>
			<script>
			    const today = new Date(); // 현재 날짜 정보 가져오기
			    const year = today.getFullYear(); // 연도 (예: 2024)
			    const month = today.getMonth() + 1; // 월 (0부터 시작하므로 +1)
			    const day = today.getDate(); // 일
			
			    const formattedDate = year + "년 " + month + "월 " + day + "일";
			
			    document.getElementById('todayDate').textContent = formattedDate;
			</script>
		</div>
		<script>
				document.addEventListener("DOMContentLoaded", function() {
				    var saveBtn = document.querySelector("#savePdf");
		
				    if (!saveBtn) {
				        console.error("❌ 버튼을 찾을 수 없습니다.");
				        return;
				    }
		
				    saveBtn.addEventListener("click", function() {
				        console.log("✅ 버튼 클릭됨");
				        pdfPrint();
				    });
				});
		
				function pdfPrint() {
				    console.log("📄 PDF 저장 시작");
		
				    var element = document.getElementById("saveZone");
				    if (!element) {
				        console.error("❌ 저장할 영역이 없습니다.");
				        return;
				    }
		
				    html2canvas(element).then(canvas => {
				        var imgData = canvas.toDataURL("image/png");
				        var imgWidth = 210; // A4 가로 (mm)
				        var pageHeight = imgWidth * 1.414; // A4 세로 (mm)
				        var imgHeight = (canvas.height * imgWidth) / canvas.width;
				        var heightLeft = imgHeight;
		
				        var doc = new jsPDF("p", "mm", "a4");
				        var position = 0;
		
				        doc.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
				        heightLeft -= pageHeight;
		
				        while (heightLeft > 0) {
				            position = heightLeft - imgHeight;
				            doc.addPage();
				            doc.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
				            heightLeft -= pageHeight;
				        }
		
				        doc.save("sample.pdf");
				        console.log("📄 PDF 저장 완료");
		
				        // PDF 다운로드 후 팝업창 닫기
				        window.close();  // 팝업 창을 닫음
				    }).catch(error => {
				        console.error("❌ PDF 생성 중 오류 발생:", error);
				    });
				}
		    </script>
	</div>
	<div style="text-align: center;">
		<button id="savePdf" class="btn btn-light-primary ms-2">PDF 저장</button>	 
	</div> 
</body>
</html>