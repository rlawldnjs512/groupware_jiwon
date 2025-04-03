function windowOpen(){
    // window.open(url, title, 속성) => 새로운 팝업창을 열어줌
    var url = "./tree.do"
    var title = "결재선"
    var prop = "top=100px, left=300px, width=600px, height=600px"

    window.open(url,title,prop)

}

window.onload = function() {
	var reports = document.querySelectorAll(".report");
	var frm = document.forms[0];
	console.log(reports.length);
	for(let i=0; i<reports.length; i++) {
		reports[i].onclick = function(evt) {
  			evt.preventDefault();
			var reportName = this.name;
			console.log(reportName);
			if(reportName == "reportTemp") {
				frm.action = "./TempReport.do";
				frm.submit();
			} else if(reportName == "reportApproval"){
				frm.action = "./ApprovalReport.do";
				frm.submit();
			} else if (reportName == "leaveApproval"){
				frm.action = "./leaveReport.do";
				frm.submit();
			} else {
				frm.action = "./tripReport.do";
				frm.submit();
			}
			console.log("이동주소 : " , frm.action);
		}
	}
}