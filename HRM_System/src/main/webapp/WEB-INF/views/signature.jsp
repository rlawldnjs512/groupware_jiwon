<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/signature_pad/1.5.3/signature_pad.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/signature.css">
<title>서명관리</title>
</head>
<body>
	<div id="signature-pad" class="m-signature-pad">
		<div class="m-signature-pad--body">
			<canvas id="signature" width="600" height="200"></canvas>
		</div>
		<div class="m-signature-pad--footer">
			<button type="button" class="button clear" data-action="clear">지우기</button>
			<button type="button" class="button save" data-action="save">저장</button>
		</div>
	</div>
    
    <script>
		var canvas = $("#signature-pad canvas")[0];
		var sign = new SignaturePad(canvas, {
			minWidth: 5,
			maxWidth: 10,
			penColor: "rgb(66, 133, 244)"
		});
		
		$("[data-action]").on("click", function(){
			if ( $(this).data("action")=="clear" ){
				sign.clear();
			}
			else if ( $(this).data("action")=="save" ){
				if (sign.isEmpty()) {
					alert("사인해 주세요!!");
				} else {
					$.ajax({
						url : "save.jsp",
						method : "post",
						dataType : "json",
						data : {
							sign : sign.toDataURL()
						},
						success : function(r){
							alert("저장완료 : " + r.filename);
							sign.clear();
						},
						error : function(res){
							console.log(res);
						}
					});
				}
			}
		});
		
		
		function resizeCanvas(){
			var canvas = $("#signature-pad canvas")[0];
	
			var ratio =  Math.max(window.devicePixelRatio || 1, 1);
			canvas.width = canvas.offsetWidth * ratio;
			canvas.height = canvas.offsetHeight * ratio;
			canvas.getContext("2d").scale(ratio, ratio);
		}
	    
	    $(window).on("resize", function(){
			resizeCanvas();
		});

		resizeCanvas();
    </script>
</body>
</html>