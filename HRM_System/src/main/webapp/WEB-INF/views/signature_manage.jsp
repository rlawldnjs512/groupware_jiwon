<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/signature_pad/1.5.3/signature_pad.min.js"></script>
<title>서명관리</title>
<style>
:root {
	--bs-white: #fff;
	--bs-primary: #1b84ff;
	--bs-primary-light: #e9f3ff;
	--bs-success: #17c653;
	--bs-success-light: #dfffea;
	--bs-secondary: #252f4a;
	--bs-secondary-light: #f9f9f9;
	--bs-warning: #f6c000;
	--bs-warning-light: #fff8dd;
}

.btn.btn-light-success {
	color: var(--bs-success);
	border-color: var(--bs-success-light);
	background-color: var(--bs-success-light);
}

.btn.btn-light-success:hover {
	color: var(--bs-white);
	border-color: var(--bs-success);
	background-color: var(--bs-success);
}

.btn.btn-light-success:focus {
	outline: none;
	box-shadow: 0 0 0 0.25rem rgba(var(--bs-success), 0.5);
}

.btn.btn-light-secondary {
	color: var(--bs-secondary);
	border-color: var(--bs-secondary-light);
	background-color: var(--bs-secondary-light);
}

.btn.btn-light-secondary:hover {
	color: var(--bs-white);
	border-color: var(--bs-secondary);
	background-color: var(--bs-secondary);
}

.btn.btn-light-secondary:focus {
	outline: none;
	box-shadow: 0 0 0 0.25rem rgba(var(--bs-secondary), 0.5);
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

.btn.btn-light-warning {
	color: var(--bs-warning);
	border-color: var(--bs-warning-light);
	background-color: var(--bs-warning-light);
}

.btn.btn-light-warning:hover {
	color: var(--bs-white);
	border-color: var(--bs-warning);
	background-color: var(--bs-warning);
}

.btn.btn-light-warning:focus {
	outline: none;
	box-shadow: 0 0 0 0.25rem rgba(var(--bs-warning), 0.5);
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: left;
}

th {
	background-color: #f0f0f0;
}

.gray {
	background-color: #ddd;
}

.main-content {
	position: relative;
	width: 100%;
	padding-top: 20px; /* 위쪽 여백 추가 */
}

.button-container {
	display: flex;
	gap: 20px; /* 버튼 간격 */
	margin-bottom: 20px; /* 아래쪽 여백 */
}

.button-container button {
	flex: 1; /* 버튼 크기를 동일하게 확장 */
	max-width: 300px; /* 버튼 최대 너비 설정 */
	height: 60px; /* 버튼 높이 */
	font-size: 18px; /* 글자 크기 */
	font-weight: bold; /* 글자 굵게 */
}
</style>
</head>
<%@ include file="sidebar.jsp"%>
<body>
	<div class="content" id="content">
		<%@ include file="header.jsp"%>
		<div class="main-content">
			<div class="container">
				<h1
					class="page-heading d-flex text-gray-900 fw-bold fs-3 flex-column justify-content-center my-0">
					내 서명 관리</h1>
				<form id="my_signature" action="./select_signature.do" method="get">
					<table
						class="table table-hover table-rounded table-striped border gy-7 gs-7">
						<tr>
							<td><label for="signatureName">이름</label></td>
							<td><span>${loginVo.name}</span></td>
						</tr>
						<tr>
							<td><label for="signatureName">사원번호</label></td>
							<td><span>${loginVo.emp_id}</span></td>
						</tr>
						<tr>
							<td><label for="signatureDescription">
									내 서명
								</label></td>
							<td>
								<div id="signature-pad" class="m-signature-pad">
									<div class="m-signature-pad--body">
										<c:if test="${loginVo.signSaved ne null}">
			                                <img id="signatureImage" src="${loginVo.signSaved}" width="600" height="400"
			                                     style="border: 1px solid black;" />
			                                <canvas id="signature-pad-canvas" width="541" height="361"
			                                        style="border: 1px solid black;"></canvas>
			                            </c:if>
			                            <c:if test="${loginVo.signSaved eq null}">
			                                <img id="signatureImage" src="${loginVo.signSaved}" width="600" height="400"
			                                     style="border: 1px solid black;" />
			                                <canvas id="signature-pad-canvas" width="541" height="361"
			                                        style="border: 1px solid black; display: block;"></canvas>
			                            </c:if>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<div class="m-signature-pad--footer">
						<c:if test="${loginVo.signSaved eq null}">
							<button type="button" class="btn btn-danger clear"
								 data-action="clear">지우기</button>
							<button type="button" class="btn btn-primary save"
								 data-action="save">저장</button>
							<button type="button" class="btn btn-success save"
								 data-action="delete" style="display: none;">다시 만들기</button>
						</c:if>
						<c:if test="${loginVo.signSaved ne null}">
							<button type="button" class="btn btn-danger clear"
								 data-action="clear" style="display: none;">지우기</button>
							<button type="button" class="btn btn-primary save"
								 data-action="save" style="display: none;">저장</button>
							<button type="button" class="btn btn-success save"
								 data-action="delete">다시 만들기</button>
						</c:if>
					</div>
					<div id="messageContainer" style="display: none; color: 
						green; font-weight: bold; margin-top: 10px;"></div>
				</form>
			</div>
		</div>
	</div>
	<script>
		var canvas = $("#signature-pad canvas")[0];
		var sign = new SignaturePad(canvas, {
			minWidth : 5,
			maxWidth : 10,
			penColor : "rgb(66, 133, 244)"
		});

		// 서명 저장 버튼 클릭 시
		$(document).ready(function () {
	        $("[data-action]").on("click", async function () {
	            const action = $(this).data("action");
	
	            if (action === "clear") {
	                sign.clear();
	                $("#signatureImage").hide();
	                $("#signature-pad canvas").show();
	            } else if (action === "save") {
	                if (sign.isEmpty()) {
	                    $("#messageContainer").text("서명을 해주세요.").show().css("color", "blue");
	                } else {
	                    try {
	                        const response = await $.ajax({
	                            url: "/mySignature.do",
	                            method: "POST",
	                            contentType: "application/json",
	                            data: JSON.stringify({ sign: sign.toDataURL() }),
	                            dataType: "json"
	                        });
	
	                        console.log(response);
	                        $("#signatureImage").attr("src", sign.toDataURL()).show();
	                        $("#signature-pad canvas").hide();
	                        sign.clear();
	
	                        $("#messageContainer").text("서명이 성공적으로 저장되었습니다.").show().css("color", "green");
	
	                        // 메시지 유지 후 서서히 사라지게 설정 (3초 후 페이드 아웃)
	                        setTimeout(() => $("#messageContainer").fadeOut(500), 3000);
	
	                        // 페이지 이동을 3초 후로 지연
	                        if (response.redirect) {
	                            setTimeout(() => {
	                                window.location.href = response.redirect;
	                            }, 1000);
	                        }
	
	                    } catch (error) {
	                        console.error(error);
	                        $("#messageContainer").text("서명 저장 실패. 다시 시도해주세요.").show().css("color", "red");
	
	                        setTimeout(() => $("#messageContainer").fadeOut(500), 3000);
	                    }
	                }
	            } else if (action === "delete") {
	                try {
	                    const response = await $.ajax({
	                        url: "/deleteSignature.do",
	                        method: "POST",
	                        contentType: "application/json",
	                        data: JSON.stringify({}),
	                        dataType: "json"
	                    });

	                    console.log(response);

	                    $("#signatureImage").attr("src", "").hide();  

	                    $("#signature-pad canvas").show();
	                    sign.clear();

	                    $("button[data-action='clear']").show();
	                    $("button[data-action='save']").show(); 
	                    $("button[data-action='delete']").hide();

	                    $("#messageContainer").text("서명이 삭제되었습니다.").show().css("color", "green");
	                    setTimeout(() => $("#messageContainer").fadeOut(500), 3000);

	                    setTimeout(() => {
	                    	location.reload();
	                    }, 1000);
	                    
// 	                    if (response.redirect) {
// 	                        setTimeout(() => {
// 	                            window.location.href = response.redirect;
// 	                        }, 1000);
// 	                    }

	                } catch (error) {
	                    console.error(error);
	                    $("#messageContainer").text("서명 삭제 실패. 다시 시도해주세요.").show().css("color", "red");
	                    setTimeout(() => $("#messageContainer").fadeOut(500), 3000);
	                }
	            }
	        });
	    });

		function resizeCanvas() {
			var canvas = $("#signature-pad canvas")[0];

			var ratio = Math.max(window.devicePixelRatio || 1, 1);
			canvas.width = canvas.offsetWidth * ratio;
			canvas.height = canvas.offsetHeight * ratio;
			canvas.getContext("2d").scale(ratio, ratio);
		}

		$(window).on("resize", function() {
			resizeCanvas();
		});

		resizeCanvas();

		// 페이지 로드 시 서명이 있는 경우 img로 표시하고, 캔버스를 숨김
		window.onload = function() {
			var signSaved = '${loginVo.signSaved}'; // 서버에서 전달된 base64 서명 이미지 데이터

			if (signSaved) {
				// 서명이 있을 경우 이미지 표시
				$("#signatureImage").attr("src", signSaved).show();
				$("#signature-pad canvas").hide();
			} else {
				// 서명이 없을 경우 캔버스를 표시
				$("#signatureImage").hide();
				$("#signature-pad canvas").show();
			}
		};
	</script>

</body>
</html>
