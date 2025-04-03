document.addEventListener("DOMContentLoaded", function () {
	
	var userRole = document.getElementById("role").value;
	if (userRole === "U") {
        return;
    }
    
    fetch('/homeList.do/donutChart')
        .then(response => response.json())
        .then(data => {
			
            const departmentLabels = data.departmentLabels;  // 부서 이름
            const departmentData = data.departmentData;  // 시간(숫자 형식)
            const formattedData = data.formattedData;  // 시간:분 형식

            const ctx = document.getElementById("departmentWorkChart").getContext("2d");
            new Chart(ctx, {
                type: "doughnut",
                data: {
                    labels: departmentLabels,
                    datasets: [{
                        data: departmentData,  // 차트에서 사용될 숫자 데이터
                        backgroundColor: [ "#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF", "#F06292" ],
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
						title: {
	                        display: true,
	                        text: '부서별 근무 시간 비교',  // 제목 텍스트
	                        font: {
	                            size: 20,  // 제목 크기
	                            weight: 'bold',  // 제목 두께
	                        },
	                        padding: {
	                            top: 40,  // 제목 상단 여백
	                            bottom: 20  // 제목 하단 여백
	                        }
                    	},
                        legend: { 
							position: "right",
							labels: {
	                            padding: 10,
	                            font: {
									size: 13,
									weight: 'bold'
								}
	                        }
						},
                        tooltip: {
							titleFont: {
						        weight: 'bold',  // 툴팁 제목 폰트 두껍게
						        size: 14         // 툴팁 제목 폰트 크기
						    },
						    bodyFont: {
						        weight: 'bold',  // 툴팁 본문 폰트 두껍게
						        size: 12         // 툴팁 본문 폰트 크기
						    },
                            callbacks: {
                                // O시간 O분 형식으로 표시
                                label: function(tooltipItem) {
                                    return formattedData[tooltipItem.dataIndex];
                                }
                            }
                        }
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching donut chart data:', error));
        
        
    fetch('/homeList.do/lateEmpChart')
    	.then(response => response.json())
        .then(data => {

            const name = data.name;   // 사원 이름 리스트
            const count = data.count; // 지각 횟수 리스트

            const latectx = document.getElementById("lateEmpChart").getContext("2d");

            new Chart(latectx, {
                type: "bar",
                data: {
                    labels: name,
                    datasets: [{
                        label: "지각 횟수",
                        data: count,
                        backgroundColor: "#FF6384",
                        borderColor: "#FF6384",
                        borderWidth: 1
                    }]
                },
                options: {
                    indexAxis: 'y', // 가로 막대 그래프 설정
                    responsive: true,
                    scales: {
                        x: {
                            beginAtZero: true,
                            ticks : { stepSize: 1 },
                    	},
                    	y: {
							ticks: {
	                            font: {
	                                size: 14, // 크기 키우기
	                                weight: 'bold', // 두껍게
	                            }
                        	}
						}
                    },
                    plugins: {
                        legend: { display: false },
                        tooltip: {
                            callbacks: {
                                label: function(tooltipItem) {
                                    return `${tooltipItem.raw}회`;
                                }
                            }
                        },
                        title: {
	                        display: true,
	                        text: '이번 달 지각 많은 사원',
	                        font: {
	                            size: 20, // 제목 크기
	                            weight: 'bold', // 제목 두께
	                        },
	                        padding: {
	                            top: 10, // 제목 상단 여백
	                            bottom: 30 // 제목 하단 여백
	                        }
	                    },
	                    legend: { display: false },
	                    tooltip: {
							titleFont: {
						        weight: 'bold',  // 툴팁 제목 폰트 두껍게
						        size: 14         // 툴팁 제목 폰트 크기
						    },
						    bodyFont: {
						        weight: 'bold',  // 툴팁 본문 폰트 두껍게
						        size: 12         // 툴팁 본문 폰트 크기
						    },
	                        callbacks: {
	                            label: function(tooltipItem) {
	                                return `${tooltipItem.raw}회`;
	                            }
	                        }
	                    }
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching late employee chart data:', error));
    
});