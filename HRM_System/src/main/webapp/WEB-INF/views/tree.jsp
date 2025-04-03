<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>조직도 (jsTree)</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.12/themes/default/style.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.12/jstree.min.js"></script>
<style type="text/css">
.approval-item {
	display:inline-block;
	width:200px;
	padding: 5px;
	border-bottom: 1px solid #ddd;
}

.remove-btn {
	color: red;
	cursor: pointer;
	margin-left: 10px;
}
</style>
</head>
<body>
	<h2>조직도</h2>
	<input type="text" id="searchInput" placeholder="검색">
	<br>
	<div id="organizationTree"></div>
	<hr>
	<h3>결재순서</h3>
	<input type="hidden" id="userId" value="${sessionScope.loginVo.emp_id}" />

	<div id="approvalList"></div>
	<button onclick="ok()">완료</button>
	<button onclick="window.close()">취소</button>
	<script>
     	var approvalLine = [];
        $(document).ready(function () {
        	 var userId = $('#userId').val();  
            $('#organizationTree').jstree({
            	'plugins' : ["search"],
            	"search":{
                    "show_only_matches": true // 검색 결과만 표시
            	},
                'core': {
                    'data': function (node, cb) {
                        $.ajax({
                            url: "./getTree.do",
                            type: "GET",
                            dataType: "json",
                            success: function (data) {
                            	var filteredData = data.filter(function (node) {
                                    return node.id !== userId;
                                })
                            	console.log(data);
                            	console.log(filteredData);
                                cb(filteredData);
                            }
                        });
                    }
                }
            });
            
            $("#searchInput").keyup(function () {
                let searchText = $(this).val();
                $("#organizationTree").jstree(true).search(searchText);
            }); // search end

            
            
            // 사원 선택 이벤트 (결재선 추가)
            $('#organizationTree').on("select_node.jstree", function (e, data) {
                let selectedNode = data.node;
                var userId = $('#userId').val(); //로그인한 사용자
                console.log("선택된 노드:", selectedNode);
                
                let empNo = selectedNode.id;
                let empName = selectedNode.text;
                console.log("empNo:", empNo);
                console.log("empName:", empName);
				
               
                if (selectedNode.parents.length === 3 && userId != empNo) {
                    addToApprovalLine(empNo, empName);
                }else {
                    alert("회사나 부서는 선택할 수 없습니다.");  
                }

            }); // organizationTree end
     
        
        });
        
     	// 결재선 추가 함수
        function addToApprovalLine(empNo, empName) {
            // 중복 방지
            if (approvalLine.some(emp => emp.id === empNo)) {
                alert("이미 추가된 사원입니다.");
                return;
            }
            // 3명 체크
            if(approvalLine.length == 3) {
            	alert("결재선은 3명까지 지정 가능합니다.");
            	return;
            }

            // 결재선 목록에 추가
            approvalLine.push({ id: empNo, name: empName});
            updateApprovalList();
        }

        // 결재선 리스트 업데이트
        function updateApprovalList() {
            $("#approvalList").empty();
			console.log(approvalLine);
			let html = "";
			approvalLine.forEach( (emp , i) => { 
					console.log(emp.name,emp.id);
					html += "<div class='approval-item'>";
					html += "<span id='"+emp.id+"'>"+(i+1)+"."+emp.name+" ("+emp.id+")</span>";
					html += "<span class='remove-btn' onclick='removeFromApprovalLine(\""+emp.id+"\")'>✖</span>"
					html += "</div>";
					console.log(html);
			})
			$("#approvalList").html(html);
        }

        // 결재선에서 삭제
        function removeFromApprovalLine(empNo) {
            approvalLine = approvalLine.filter(emp => emp.id !== empNo);
            updateApprovalList();
        }

       
        
        function ok() {
        	if (approvalLine.length === 0) {
                alert("결재선을 선택하세요.");
                return;
            }
        	console.log(approvalLine)
        	window.opener.line(approvalLine);
        	window.close();
        }
    </script>
</body>
</html>