<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>에러 페이지</title>
    <meta http-equiv="refresh" content="3;url=/"> <!-- 5초 후 자동 이동 -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #dfefff; /* 연한 하늘색 */
            color: #0b3d91; /* 짙은 파랑 */
            text-align: center;
            padding: 50px;
        }
        .error-container {
            background: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
            display: inline-block;
            max-width: 400px;
        }
        .error-container img {
            width: 100px;
            margin-bottom: 20px;
        }
        h2 {
            font-size: 24px;
            margin-bottom: 10px;
        }
        p {
            font-size: 16px;
            margin-bottom: 20px;
        }
        .countdown {
            font-weight: bold;
            color: #dc3545;
        }
    </style>
    <script>
        let seconds = 3;
        function countdown() {
            document.getElementById("timer").innerText = seconds;
            seconds--;
            if (seconds >= 0) {
                setTimeout(countdown, 1000);
            }
        }
        window.onload = countdown;
    </script>
</head>
<body>
    <div class="error-container">
        <img src="https://cdn-icons-png.flaticon.com/512/564/564619.png" alt="Error Icon">
        <h2>세션이 만료되었습니다</h2>
        <p>${errorMessage}</p>
        <p><span class="countdown" id="timer">3</span>초 후 로그인 페이지로 이동합니다.</p>
    </div>
</body>
</html>
